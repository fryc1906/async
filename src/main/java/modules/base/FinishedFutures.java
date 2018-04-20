package modules.base;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FinishedFutures {
    private List<Future> finishedFuturesList;

    public FinishedFutures() {
        initList();
    }

    private void initList() {
        finishedFuturesList = new LinkedList<>();
    }

    public synchronized void addElement(Future<Integer> future){
        this.finishedFuturesList.add(future);
    }

    public synchronized void printListElements(){
        if(!finishedFuturesList.isEmpty()){
            for(Future future : finishedFuturesList){
                try {
                    System.out.println(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized int getSize() {
        return this.finishedFuturesList.size();
    }
}
