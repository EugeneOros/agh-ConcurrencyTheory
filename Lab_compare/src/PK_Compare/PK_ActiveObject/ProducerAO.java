package PK_Compare.PK_ActiveObject;

import PK_Compare.PK_ActiveObject.ActiveObject.Proxy.Proxy;
import PK_Compare.PK_ActiveObject.ActiveObject.Scheduler.Future;
import PK_Compare.GeneratedData;
import PK_Compare.WorkThreads.WorkProducer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerAO extends WorkProducer{
    private final Proxy proxy;
    private final int id;
    Future futuresToPut;

    public ProducerAO(Proxy proxy, int id, int countWork, GeneratedData generatedData){
        super(countWork, generatedData);
        this.proxy = proxy;
        this.id = id;
    }

//    private List<Integer> generateListToPut(){
//        int countToPut = ThreadLocalRandom.current().nextInt(1, (proxy.M / 2) + 1);
//        List<Integer> listToPut = new LinkedList<>();
//        for (int i = 0; i < countToPut; i++) {
//            listToPut.add((int)(Math.random() * 10) + 1);
//        }
//        return listToPut;
//    }

    @Override
    public void run() {
        try{
            List<Integer> listToPut = getToProduce();
            futuresToPut = proxy.put(listToPut, id);
            listToPut = getToProduce();
            while(true){
                doSomething();
                
                if(futuresToPut.isAvailable()){
                    futuresToPut = proxy.put(listToPut, id);
                    listToPut = getToProduce();
                    if(listToPut == null) {
                        doSomething();
                        return;
                    };
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
