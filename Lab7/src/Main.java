import ActiveObject.Proxy.Proxy;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int M = 10;
        int producersCount = 5;
        int consumersCount = 3;

        ArrayList<Thread> producers = new ArrayList<>(producersCount);
        ArrayList<Thread> consumers = new ArrayList<>(consumersCount);

        Proxy proxy = new Proxy(M);

        for(int i=0; i < producersCount; i++){
            producers.add(new Producer(proxy, i));
        }
        for(int i=0; i < consumersCount; i++){
            consumers.add(new Consumer(proxy, i));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        for (Thread customer : consumers) {
            customer.join();
        }
        for (Thread producer : producers) {
            producer.join();
        }

    }

}
