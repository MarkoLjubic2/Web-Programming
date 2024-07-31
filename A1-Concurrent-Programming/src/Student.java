import java.util.Random;

public class Student extends Thread{

    private final long id;
    private final long arrivalTime;
    private final long pace;
    private final int grade;

    public Student(long id) {
        this.id = id;
        this.arrivalTime = new Random().nextLong(1000);
        this.grade = 5 + new Random().nextInt(6);
        this.pace = 500 + new Random().nextLong(500);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(pace);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Student " + id;
    }

    public int getGrade() {
        return grade;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getPace() {
        return pace;
    }

}
