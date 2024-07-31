public class Assistant extends Thread{

    @Override
    public void run() {
        try {
            while (true) {

                Student student = Main.assistantStudentsBQ.take();

                long start = System.currentTimeMillis() - Main.start;
                student.start();
                student.join();

                Main.sumOfGrades.addAndGet(student.getGrade());
                Main.numberOfStudents.incrementAndGet();

                System.out.println("Thread: " + student +
                        " Arrival: " + student.getArrivalTime() +
                        " Prof: Assistant" + super.getName() +
                        " TTC: " + student.getPace() +
                        " : " + start +
                        " Score: " + student.getGrade());

                Main.semaphoreAssistant.release();
            }
        }
            catch (Exception e) {
                e.printStackTrace();
            }
   }
}
