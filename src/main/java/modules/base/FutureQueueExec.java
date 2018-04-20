package modules.base;
import java.util.Random;
import java.util.concurrent.*;

public class FutureQueueExec {
    FinishedFutures finishedFutures = new FinishedFutures();
    private ExecutorService executorService;
    private BlockingQueue blockingQueue;
    private long timer;

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public FutureQueueExec(int threads, FinishedFutures finishedFutures) {
        this.executorService = Executors.newFixedThreadPool(threads);
        this.finishedFutures = finishedFutures;
        this.blockingQueue = new LinkedBlockingQueue(10);
    }


    public Future<Integer> calculate(int value){
        return executorService.submit(() -> {
            Thread.sleep((new Random().nextInt(2000)));
            return value * value;
        });
    }


//    public boolean isFinished(){
//        for(Future future : futureList){
//            if(!future.isDone()){
//                return false;
//            }
//        }
//        return true;
//    }

//    public void printResult(){
//        if(isFinished()){
//            for(Future future : futureList){
//                try {
//                    System.out.println(future.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            System.out.println("list not calculated yet");
//        }
//    }

    public void shutdownExecutor(){
        executorService.shutdown();
    }

    public void begin() {
        try {
            new Thread(() -> {
                    while(finishedFutures.getSize()<Configs.ITERATIONS || !blockingQueue.isEmpty()){
                        if(!blockingQueue.isEmpty() ) {
                            try {
                                Future<Integer> future = (Future) blockingQueue.peek();
                                finishedFutures.addElement(future);
                                blockingQueue.remove();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            }).start();

            timer = System.currentTimeMillis();

            for(int i=0; i<Configs.ITERATIONS; i++){
                try {

                    blockingQueue.put(calculate(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            shutdownExecutor();
            while(true) {
                if (executorService.isTerminated()) {
                    finishedFutures.printListElements();
                    System.out.println("Execution time: " + (System.currentTimeMillis() - timer) + "ms");
                    break;
                }
            }
        }
    }

//    public void waitForFinish() {
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
////                    try {
//                        if(isFinished()){
//                            printResult();
//                            shutdownExecutor();
//                            break;
//                        }
//                        //Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                }
//            }
//        });
//    }
}
