import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {

    public static AtomicInteger sumOfGrades = new AtomicInteger();
    public static AtomicInteger numberOfStudents = new AtomicInteger();
    public static Semaphore semaphoreProfessor = new Semaphore(2);
    public static Semaphore semaphoreAssistant = new Semaphore(1);
    public static BlockingQueue<Student> professorStudentsBQ1 = new LinkedBlockingQueue<>();
    public static BlockingQueue<Student> professorStudentsBQ2 = new LinkedBlockingQueue<>();
    public static BlockingQueue<Student> assistantStudentsBQ = new LinkedBlockingQueue<>();
    public static ScheduledExecutorService executorService;

    public static long start;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of students: ");
        int totalStudents = sc.nextInt();
        sc.close();

        executorService = Executors.newScheduledThreadPool(totalStudents);

        Queue<Student> professorStudents = new ArrayDeque<>();

        Professor professor1 = new Professor(0);
        Professor professor2 = new Professor(1);
        Assistant assistant = new Assistant();
        professor1.start();
        professor2.start();
        assistant.start();
        start = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            try {
                semaphoreProfessor.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            professorStudentsBQ1.add(Objects.requireNonNull(professorStudents.poll()));
            professorStudentsBQ2.add(Objects.requireNonNull(professorStudents.poll()));
        });


        ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
        timer.schedule(() -> {
            System.out.println("Student count: " + numberOfStudents.get() + " Total grade average: " + (double) sumOfGrades.get() / numberOfStudents.get());
            System.exit(0);
        }, 5, TimeUnit.SECONDS);

        List<Student> students = IntStream.range(0, totalStudents)
                .mapToObj(Student::new)
                .sorted(Comparator.comparingLong(Student::getArrivalTime))
                .toList();

        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            if (new Random().nextBoolean()) {
                if (professorStudents.size() < 2) {
                    Student student = iterator.next();
                    professorStudents.add(student);
                    executorService.schedule(() -> {
                        try {
                            cyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }, student.getArrivalTime(), TimeUnit.MILLISECONDS);
                }
            } else {
                if (semaphoreAssistant.tryAcquire()) {
                    Student student = iterator.next();

                    executorService.schedule(() -> assistantStudentsBQ.add(student), student.getArrivalTime(), TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
