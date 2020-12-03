package PK_Compare;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import PK_Compare.PK_ActiveObject.*;
import PK_Compare.PK_ActiveObject.ActiveObject.Proxy.Proxy;
import PK_Compare.PK_Monitor.BufferM;
import PK_Compare.PK_Monitor.ConsumerM;
import PK_Compare.PK_Monitor.ProducerM;

public class Main {
//    public static final int consumerCount = 100;
//    public static final int producerCount = 100;
//    public static final int bufferSize = 100;

    private static List<String> generateListToPut(int M){
        int countToPut = 10; //ThreadLocalRandom.current().nextInt(1, (M / 2) + 1);
        List<String> listToPut = new LinkedList<>();
        for (int i = 0; i < countToPut; i++) {
            listToPut.add(String.valueOf((int)(Math.random() * (M-1)) + 1));
        }
        return listToPut;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        boolean generateNewFile = Boolean.parseBoolean(args[0]);
        int capacity = Integer.parseInt(args[1]);
        int countProducers = Integer.parseInt(args[2]);
        int countConsumers = Integer.parseInt(args[3]);
        int timeBuffer = Integer.parseInt(args[4]);
        int timeWorker = Integer.parseInt(args[5]);
        boolean isMonitor = Boolean.parseBoolean(args[6]);
        String resourcePath = args[7];

        ArrayList<Thread> producers = new ArrayList<>(countProducers);
        ArrayList<Thread> consumers = new ArrayList<>(countConsumers);

        if (generateNewFile){
            for (int i = 0; i < countConsumers; i++) {
                Files.write(Paths.get(resourcePath + "cons" + i), generateListToPut(capacity), StandardCharsets.UTF_8);
            }

            for (int i = 0; i < countProducers; i++) {
                Files.write(Paths.get(resourcePath + "prod" + i), generateListToPut(capacity), StandardCharsets.UTF_8);
            }
        }

        if(!isMonitor) {
            Proxy proxy = new Proxy(capacity, timeBuffer);

            for (int i = 0; i < countProducers; i++) {
                GeneratedData generatedData = new GeneratedData(resourcePath + "prod" + i);
                producers.add(new ProducerAO(proxy, i, timeWorker, generatedData));
            }

            for (int i = 0; i < countConsumers; i++) {
                GeneratedData generatedData = new GeneratedData(resourcePath + "cons" + i);
                consumers.add(new ConsumerAO(proxy, i, timeWorker,  generatedData));
            }
        }else{
            BufferM buff = new BufferM(capacity);

            for (int i = 0; i < countProducers; i++) {
                GeneratedData generatedData = new GeneratedData(resourcePath + "prod" + i);
                producers.add(new ProducerM(buff, timeWorker, generatedData));
            }

            for (int i = 0; i < countConsumers; i++) {
                GeneratedData generatedData = new GeneratedData(resourcePath + "cons" + i);
                consumers.add(new ConsumerM(buff, timeWorker, generatedData));
            }
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



