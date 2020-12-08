package Lab8.PK_ActiveObject.ActiveObject.Scheduler;

import Lab8.PK_ActiveObject.ActiveObject.MethodRequest.IMethodRequest;
import Lab8.PK_ActiveObject.ActiveObject.Servant.Buffer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler extends Thread{
    private final Queue<IMethodRequest> mainQueue;
    private final Queue<IMethodRequest> secondQueue;
    Buffer buffer;
    ReentrantLock lock = new ReentrantLock();
    Condition cond = lock.newCondition();
    private AtomicBoolean running = new AtomicBoolean(true);

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
        while(running.get()){
            while(!secondQueue.isEmpty()){
                IMethodRequest methodRequest = secondQueue.peek();

                if(Thread.interrupted()){
                    running.set(false);
                    break;
                }
                if(methodRequest.guard()){
                    methodRequest.call();
                    secondQueue.remove();
                } else break;
            }
//            if(!Thread.interrupted()){
//                waitForGeneral();
//            }


            if(!mainQueue.isEmpty()){
                IMethodRequest methodRequest = mainQueue.remove();

                if(methodRequest.guard()){
                    methodRequest.call();
                } else {
                    secondQueue.add(methodRequest);
                }
            }
            if(Thread.interrupted()){
                running.set(false);
            }
        }


    }

    private void waitForGeneral(){
        if(mainQueue.isEmpty()){
            lock.lock();
            try{
                while(mainQueue.isEmpty()) {
                    try {
                        cond.await();
                    } catch (InterruptedException e) {
                        System.out.println("ops");
                        break;
                    }
                }
            }finally {
                lock.unlock();
            }
        }
    }

}

