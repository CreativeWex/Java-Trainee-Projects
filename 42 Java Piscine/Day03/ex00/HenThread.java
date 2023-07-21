public class HenThread extends Thread {
    private Integer count;

    public  HenThread() {

    }

    public HenThread(Integer count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Hen");
        }
    }
}
