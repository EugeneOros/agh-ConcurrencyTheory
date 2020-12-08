package Lab8;

import Lab8.PK_ActiveObject.ActiveObject.Proxy.Proxy;
import Lab8.PK_ActiveObject.ConsumerAO;
import Lab8.PK_ActiveObject.ProducerAO;
import Lab8.PK_Monitor.BufferM;
import Lab8.PK_Monitor.ConsumerM;
import Lab8.PK_Monitor.ProducerM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
//        boolean generateNewFile = Boolean.parseBoolean(args[0]);
        boolean isMonitor = Boolean.parseBoolean(args[0]);
        int capacity = Integer.parseInt(args[1]);
        int countProducers = Integer.parseInt(args[2]);
        int countConsumers = Integer.parseInt(args[3]);
        int countBufferWait = Integer.parseInt(args[4]);
        int countWork = Integer.parseInt(args[5]);
//        String resourcePath = args[7];
        Proxy proxy = null;

        ArrayList<Thread> producers = new ArrayList<>(countProducers);
        ArrayList<Thread> consumers = new ArrayList<>(countConsumers);

        if(!isMonitor) {
            proxy = new Proxy(capacity, countBufferWait);

            for (int i = 0; i < countProducers; i++) {
                producers.add(new ProducerAO(proxy, i, countWork));
            }

            for (int i = 0; i < countConsumers; i++) {
                consumers.add(new ConsumerAO(proxy, i, countWork));
            }
        }else{
            BufferM buff = new BufferM(capacity, countBufferWait);

            for (int i = 0; i < countProducers; i++) {
                producers.add(new ProducerM(buff, capacity, countWork));
            }

            for (int i = 0; i < countConsumers; i++) {
                consumers.add(new ConsumerM(buff, capacity, countWork));
            }
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        Thread.sleep(5000 * 1);


        for (Thread customer : consumers) {
            customer.interrupt();
        }
        for (Thread producer : producers) {
            producer.interrupt();
        }

        if(proxy != null){
            proxy.scheduler.interrupt();
        }

        for (Thread customer : consumers) {
            customer.join();
        }
        for (Thread producer : producers) {
            producer.join();
        }
    }
}
