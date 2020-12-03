package ActiveObject.Scheduler;

import ActiveObject.MethodRequest.IMethodRequest;
import ActiveObject.Servant.Buffer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler extends Thread{
    private final Queue<IMethodRequest> mainQueue;
    private final Queue<IMethodRequest> secondQueue;
    Buffer buffer;
    ReentrantLock lock = new ReentrantLock();
    Condition cond = lock.newCondition();

    public Scheduler(Buffer buffer){
        mainQueue = new ConcurrentLinkedQueue<>();
        secondQueue = new ConcurrentLinkedQueue<>();
        this.buffer = buffer;
    }

    public void enqueue(IMethodRequest request){
        lock.lock();
        try{
            mainQueue.add(request);
            cond.signal();
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void run(){
        while(true){
            while(!secondQueue.isEmpty()){
                IMethodRequest methodRequest = secondQueue.peek();

                if(methodRequest.guard()){
                    methodRequest.call();
                    secondQueue.remove();
                    System.out.println("MethodRequest done; name: " + methodRequest.toString());
                    System.out.println("Buffer filled size: " + buffer.size() + "\n");
                } else break;
            }

            waitForGeneral();

            if(!mainQueue.isEmpty()){
                IMethodRequest methodRequest = mainQueue.remove();

                if(methodRequest.guard()){
                    methodRequest.call();
                    System.out.println("MethodRequest done; name: " + methodRequest.toString());
                    System.out.println("Buffer filled size: " + buffer.size() + "\n");
                } else {
                    secondQueue.add(methodRequest);
                }
            }

        }


    }

    private void waitForGeneral(){
        // waits on condition until general_queue is not empty
        if(mainQueue.isEmpty()){
            lock.lock();
            try{
                while(mainQueue.isEmpty()) {
                    try {
                        cond.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                lock.unlock();
            }
        }
    }

}

