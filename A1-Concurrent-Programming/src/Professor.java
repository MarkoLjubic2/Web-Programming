public class Professor extends Thread{

    private final int id;

    public Professor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {

                Student student;
                if(this.id == 0)student = Main.professorStudentsBQ1.take();
                else student = Main.professorStudentsBQ2.take();

                long defenseTime = System.currentTimeMillis() - Main.start;
                student.start();
                student.join();

                Main.numberOfStudents.incrementAndGet();
                Main.sumOfGrades.addAndGet(student.getGrade());

                System.out.println("Thread: " + student +
                        " Arrival: " + student.getArrivalTime() +
                        " Prof: Professor" + super.getName() +
                        " TTC: " + student.getPace() +
                        " : " + defenseTime +
                        " Score: " + student.getGrade());

                if(this.id == 1) Main.semaphoreProfessor.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
