import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int N = 2;
    public static void main(String[] args) {
        Product product = new Product();
        List<Thread> list = new ArrayList<Thread>();
        Runnable runnable;
        for(int i=0; i<N; i++) {
            if(i%2==0) {
                runnable = new Consumer(product);
            }
            else {
                runnable = new Producer(product);
            }

            Thread t = new Thread(runnable);
            list.add(t);
            t.start();
        }

        for(Thread t: list) {
            try{
                t.join();
            }
            catch (Exception e) {

            }
        }
    }
}
