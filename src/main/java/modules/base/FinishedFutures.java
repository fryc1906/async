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
        System.out.println(finishedFuturesList.size() + " element added");
        this.finishedFuturesList.add(future);
        if(future.isDone()){
            System.out.println("done");
        } else {
            System.out.println("not done");
        }
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
