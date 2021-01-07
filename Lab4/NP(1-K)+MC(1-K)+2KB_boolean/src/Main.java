import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        int M = 100;
        int m = 100;
        int n = 100;

        Buffer buf = new Buffer(M);
        ExecutorService service = Executors.newFixedThreadPool(m + n);

        for(int i=1; i<=m; i++) {
            service.submit(new Producer(buf, M)) ;
        }

        for(int i=1; i<=n; i++) {
            service.submit(new Consumer(buf, M));
        }

        try {
            service.shutdown();
            if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
                service.shutdownNow();
            }
//            scheduler.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
