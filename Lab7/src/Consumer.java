import ActiveObject.Proxy.Proxy;
import ActiveObject.Scheduler.Future;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {
    private final Proxy proxy;
    private final int id;
    int countToGet;

    public Consumer(Proxy proxy, int id) {
        this.proxy = proxy;
        this.id = id;
    }

    @Override
    public void run() {
        try{
            while (true) {
                countToGet = ThreadLocalRandom.current().nextInt(1, (proxy.M / 2) + 1);
                Future futureToGet = proxy.get(countToGet, id);
                sleep(300); //coś tam robi

                while (!futureToGet.isAvailable()) {
                    sleep(300); //coś tam robi i zawsze sprawdza future
                }
                List<Integer> result = futureToGet.get();
                System.out.println("(Consumer " + id + ") get: " + result.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
