package Lab8.PK_ActiveObject;

import Lab8.PK_ActiveObject.ActiveObject.Proxy.Proxy;
import Lab8.PK_ActiveObject.ActiveObject.Scheduler.Future;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerAO extends Thread {
    private AtomicBoolean running = new AtomicBoolean(true);
    private final Proxy proxy;
    private final int id;
    int countToGet;
    int countWork;
    private int countMRequestsDone = 0;

    public ConsumerAO(Proxy proxy, int id,  int countWork) {
        this.proxy = proxy;
        this.id = id;
        this.countWork = countWork;
    }

    private void doSomething(){
        for(int i=0; i<1000; i++)
        {
            Math.sin(23);
            Math.exp(34);
            Math.abs(-12);
        }
    }
    @Override
    public void run() {
        try{
            while (running.get()) {
                countToGet = ThreadLocalRandom.current().nextInt(1, (proxy.M / 2) + 1);
                Future futureToGet = proxy.get(countToGet, id);
                int i=0;
                while (!futureToGet.isAvailable()) {
                    if(Thread.interrupted()){
                        running.set(false);
                        System.out.println("C "+ countMRequestsDone);
                        break;
                    }
                    if(i<countWork){
                        i++;
                        doSomething();
                    }else{
                        break;
                    }

                }
                while(i<countWork){
                    i++;
                    doSomething();
                }
                countMRequestsDone++;
                if(Thread.interrupted()){
                    running.set(false);
                    System.out.println("C "+ countMRequestsDone);
                }
            }
        }catch(Exception e){
            running.set(false);
            System.out.println("C "+countMRequestsDone);
        }
    }
}
