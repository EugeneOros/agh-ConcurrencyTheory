package PK_Compare.PK_ActiveObject;

import PK_Compare.GeneratedData;
import PK_Compare.PK_ActiveObject.ActiveObject.Proxy.Proxy;
import PK_Compare.PK_ActiveObject.ActiveObject.Scheduler.Future;
import PK_Compare.WorkThreads.WorkConsumer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumerAO extends WorkConsumer {
    private final Proxy proxy;
    private final int id;
    int countToGet;

    public ConsumerAO(Proxy proxy, int id, int countWork, GeneratedData generatedData) {
        super(countWork, generatedData);
        this.proxy = proxy;
        this.id = id;
    }

    @Override
    public void run() {
        
        try{
            while (true) {
                countToGet = getToConsume();
                if(countToGet == -1){
                    doSomething();
                    return;
                }
                Future futureToGet = proxy.get(countToGet, id);

                while (!futureToGet.isAvailable()) {
                    doSomething();
                }
                futureToGet.get();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
