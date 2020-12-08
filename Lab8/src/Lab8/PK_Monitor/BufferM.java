package Lab8.PK_Monitor;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferM {
    private final LinkedList<Integer> buffer;
    private final int LIMIT;
    private final ReentrantLock lock;
    private final Condition producerCondition;
    private final Condition consumerCondition;
    private final Condition otherProducerCondition;
    private final Condition otherConsumerCondition;
    private boolean isProducerConditionalEmpty = true;
    private boolean isConsumerConditionalEmpty = true;
    private int countProducerConditional = 0;
    private int countConsumerConditional = 0;
    private int countProducerOthersConditional = 0;
    private int countConsumerOthersConditional = 0;
    private int countWait;

    public BufferM(int m, int countWait) {
        this.buffer = new LinkedList<Integer>();
        this.LIMIT = m * 2;
        this.lock = new ReentrantLock();
        this.producerCondition = lock.newCondition();
        this.consumerCondition = lock.newCondition();
        this.otherProducerCondition = lock.newCondition();
        this.otherConsumerCondition = lock.newCondition();
        this.countWait = countWait;

    }

    public  void put(int[] toProduce) throws InterruptedException {
        lock.lock();

        try {
//            System.out.println("Enter producer id :" + Thread.currentThread().getId());
//            printCounters();
            countProducerOthersConditional++;
            while(!isProducerConditionalEmpty){
//                System.out.println("PRODUCER id: " + Thread.currentThread().getId() + " is waiting on otherProducerCondition(size:" + countProducerOthersConditional + ") to put: " + toProduce.length);
                otherProducerCondition.await();
            }
            if(countProducerOthersConditional > 0){
                countProducerOthersConditional--;
            }
            countProducerConditional++;
            while( buffer.size() + toProduce.length  > LIMIT ) {
//                System.out.println("PRODUCER id: " + Thread.currentThread().getId() + " is waiting producerCondition(size:" + countProducerConditional + ") to put: " + toProduce.length);
                isProducerConditionalEmpty = false;
                producerCondition.await();

            }
            if(countProducerConditional > 0){
                countProducerConditional--;
            }


            isProducerConditionalEmpty = true;
            for( int j = 0; j<toProduce.length; j++){
                buffer.add(toProduce[j]);
                for (int t = 0; t < countWait; t++) {
                    Math.cos(30);
                }
            }

//            System.out.println("Producer id: " + Thread.currentThread().getId() + " put: " + toProduce.length );
//            System.out.println("Buffer size: " + buffer.size());



            otherProducerCondition.signal();
            consumerCondition.signal();
        } finally {
            lock.unlock();
        }

    }

    public void get(int toConsume) throws InterruptedException {

        lock.lock();

        try {
//            System.out.println("Enter Customer id :" + Thread.currentThread().getId());
//            printCounters();
            countConsumerOthersConditional++;
            while(!isConsumerConditionalEmpty){
//                System.out.println("CONSUMER id: " + Thread.currentThread().getId() + " is waiting on otherConsumerCondition(size:" + countConsumerOthersConditional + ") to get: " + toConsume );
                otherConsumerCondition.await();
            }
            if(countConsumerOthersConditional > 0){
                countConsumerOthersConditional--;
            }

            countConsumerConditional++;
            while( buffer.size() - toConsume < 0 ) {
//                System.out.println("CONSUMER id: " + Thread.currentThread().getId() + " is waiting on consumerCondition(size:" + countConsumerConditional + ") to get: " + toConsume);
                isConsumerConditionalEmpty = false;
                consumerCondition.await();
            }
            if(countConsumerConditional > 0){
                countConsumerConditional--;
            }
            isConsumerConditionalEmpty = true;
            for( int j = 0; j < toConsume; j++ ) {
                buffer.removeFirst();
                for (int t = 0; t < countWait; t++) {
                    Math.cos(30);
                }
            }
//            System.out.println("CONSUMER id: " + Thread.currentThread().getId() + " get: " + toConsume);
//            System.out.println("Buffer size:" + buffer.size());


            otherConsumerCondition.signal();
            producerCondition.signal();
        } finally {
            lock.unlock();
        }


    }

    private void printCounters(){
        System.out.println("______________________");
        System.out.println("|   | First | Others |");
        System.out.println("| C |   " + countConsumerConditional + "   |   " + countConsumerOthersConditional + "   |");
        System.out.println("| P |   " + countProducerConditional + "   |   " + countProducerOthersConditional + "   |");
        System.out.println("______________________");
    }
}


















































































