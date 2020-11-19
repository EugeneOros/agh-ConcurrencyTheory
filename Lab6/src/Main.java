import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int consumerCount = 100;
    public static final int producerCount = 100;
    public static final int bufferSize = 100;
    public static Buffer buffer = new Buffer(bufferSize);
    public static void main(String[] args)
    {
        Monitor monitor = new Monitor(bufferSize);
        List<Thread> list = new ArrayList<Thread>();
        Runnable runnable;
        for(int i=0; i<producerCount; i++)
        {
            runnable = new Producer(monitor, buffer);
            Thread t = new Thread(runnable);
            list.add(t);
            t.start();
        }

        for(int i=0; i<consumerCount; i++)
        {
            runnable = new Consumer(monitor, buffer);
            Thread t = new Thread(runnable);
            list.add(t);
            t.start();
        }


        for(Thread t: list)
        {
            try{
                t.join();
            }
            catch (Exception e){}
        }


    }

}