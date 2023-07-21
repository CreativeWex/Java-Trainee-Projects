public class ThreadsCalculation implements Runnable  {
    private int index;
    private int from;
    private int to;
    private int sum;
    private short[] arr;

    public ThreadsCalculation(int index, int from, int to, short[] arr) {
        this.index = index;
        this.from = from;
        this.to = to;
        this.sum = 0;
        this.arr = arr;
    }

    @Override
    public void run() {
        for(int i = from; i <= to; i++) {
            sum += arr[i];
        }
        System.out.println("Thread " + (index + 1) + ": from " + from + " to " + to + " sum is " + sum);
        synchronized (this) {
            Program.threadSum += sum;
        }
    }
}
