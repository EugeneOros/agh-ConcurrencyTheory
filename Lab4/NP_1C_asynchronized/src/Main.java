import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static final int howManyProducers = 5;
    public static final int howManyConsumers = 4;
    public static final int bufferSize = 10;
//    private ConcurrentHashMap<Integer, Integer> buffer = new ConcurrentHashMap<>(50);
    public static void main(String[] args)
    {
        Product product =  new Product(bufferSize);
        List<Thread> list = new ArrayList<Thread>();
        Runnable runnable;
        for(int i=0; i<howManyProducers; i++)
        {
            runnable = new Producer(product, buffer);
            Thread t = new Thread(runnable);
            list.add(t);
            t.start();
        }

        for(int i=0; i<howManyConsumers; i++)
        {
            runnable = new Consumer(product, buffer);
            Thread t = new Thread(runnable);
            list.add(t);
            t.start();
        }


        for(Thread t: list)
        {
            try{
                t.join();
            }
            catch (Exception e)
            {

            }
        }


    }

}
