package modules.base;

public class App {
    public static void main(String[] args) {
        FinishedFutures finishedFutures = new FinishedFutures();
        FutureQueueExec futureQueueExec = new FutureQueueExec(Configs.MAX_AMOUNT_OF_THREADS, finishedFutures);
        futureQueueExec.begin();
    }
}
