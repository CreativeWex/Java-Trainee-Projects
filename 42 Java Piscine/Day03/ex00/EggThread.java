public class EggThread extends Thread {
    private Integer count;

    public EggThread() {

    }

    public EggThread(Integer count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Egg");
        }
    }
}
