package Lab8.PK_ActiveObject;

import Lab8.PK_ActiveObject.ActiveObject.Proxy.Proxy;
import Lab8.PK_ActiveObject.ActiveObject.Scheduler.Future;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProducerAO extends Thread{
    private final Proxy proxy;
    private final int id;
    Future futuresToPut;
    int countWork;
    private AtomicBoolean running = new AtomicBoolean(true);
    private int countMRequestsDone = 0;

    public ProducerAO(Proxy proxy, int id, int countWork){
        this.proxy = proxy;
        this.id = id;
        this.countWork = countWork;
    }

    private List<Integer> generateListToPut(){
        int countToPut = ThreadLocalRandom.current().nextInt(1, (proxy.M / 2) + 1);
        List<Integer> listToPut = new LinkedList<>();
        for (int i = 0; i < countToPut; i++) {
            listToPut.add((int)(Math.random() * 10) + 1);
        }
        return listToPut;
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
            List<Integer> listToPut = generateListToPut();
            futuresToPut = proxy.put(listToPut, id);
            listToPut = generateListToPut();
            int i=0;
            while(running.get()){
                if(futuresToPut.isAvailable()){
                    futuresToPut = proxy.put(listToPut, id);
                    listToPut = generateListToPut();
                    countMRequestsDone++;
                }
                if(i<countWork){
                    i++;
                    doSomething();
                }
                if(Thread.interrupted()){
                    running.set(false);
                    System.out.println("P "+ countMRequestsDone);
                }
            }
        }catch(Exception e){
            running.set(false);
            System.out.println("P "+ countMRequestsDone);
        }
    }
}
