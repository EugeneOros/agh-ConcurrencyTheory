import ActiveObject.Proxy.Proxy;
import ActiveObject.Scheduler.Future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread{
    private final Proxy proxy;
    private final int id;
    Future futuresToPut;

    public Producer(Proxy proxy, int id){
        this.proxy = proxy;
        this.id = id;
    }

    private List<Integer> generateListToPut(){
        int countToPut = ThreadLocalRandom.current().nextInt(1, (proxy.M / 2) + 1);
        List<Integer> listToPut = new LinkedList<>();
        for (int i = 0; i < countToPut; i++) {
            listToPut.add((int)(Math.random() * 10) + 1);
        }
        return listToPut;
    }

    @Override
    public void run() {
        try{
            //najpierwszy raz kiedy jeszcze niema
            List<Integer> listToPut = generateListToPut();
            System.out.println("(Producer " + id + ") put: " + listToPut.toString());
            futuresToPut = proxy.put(listToPut, id);
            listToPut = generateListToPut();
            sleep(100); //coś tam robi

            while(true){
                if(futuresToPut.isAvailable()){
                    futuresToPut = proxy.put(listToPut, id);
                    listToPut = generateListToPut();
                    System.out.println("(Producer " + id + ") put: " + listToPut.toString());
                }
                sleep(100); //coś tam robi
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
