import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Product {

    private final int LIMIT;

    public Product(int m) {
        this.LIMIT = m;
        this.buffer = new ConcurrentHashMap<Integer, Integer>(LIMIT);
    }


    public void putStart(int i){

    }

    public void putFinish(int i){

    }

    public void getStart(int i){

    }

    public void getFinish(int i){

    }


}






//
//
//
//
//    public  void put(int[] toProduce) {
//        lock.lock();
//
//        try {
//            System.out.println("Enter producer id :" + Thread.currentThread().getId());
//            printCounters();
//            countProducerOthersConditional++;
//            while(!isProducerConditionalEmpty){
//                System.out.println("PRODUCER id: " + Thread.currentThread().getId() + " is waiting on otherProducerCondition(size:" + countProducerOthersConditional + ") to put: " + toProduce.length);
//                otherProducerCondition.await();
//            }
//            if(countProducerOthersConditional > 0){
//                countProducerOthersConditional--;
//            }
//            countProducerConditional++;
//            while( buffer.size() + toProduce.length  > LIMIT ) {
//                System.out.println("PRODUCER id: " + Thread.currentThread().getId() + " is waiting producerCondition(size:" + countProducerConditional + ") to put: " + toProduce.length);
//                isProducerConditionalEmpty = false;
//                producerCondition.await();
//
//            }
//            if(countProducerConditional > 0){
//                countProducerConditional--;
//            }
//
//
//            isProducerConditionalEmpty = true;
//            for( int j = 0; j<toProduce.length; j++){
//                buffer.add(toProduce[j]);
//            }
//
//            System.out.println("Producer id: " + Thread.currentThread().getId() + " put: " + toProduce.length );
//            System.out.println("Buffer size: " + buffer.size());
//
//
//            otherProducerCondition.signal();
//            consumerCondition.signal();
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//
//    }
//
//    public void get(int toConsume) {
//
//        lock.lock();
//
//        try {
//            System.out.println("Enter Customer id :" + Thread.currentThread().getId());
//            printCounters();
//            countConsumerOthersConditional++;
//            while(!isConsumerConditionalEmpty){
//                System.out.println("CONSUMER id: " + Thread.currentThread().getId() + " is waiting on otherConsumerCondition(size:" + countConsumerOthersConditional + ") to get: " + toConsume );
//                otherConsumerCondition.await();
//            }
//            if(countConsumerOthersConditional > 0){
//                countConsumerOthersConditional--;
//            }
//
//            countConsumerConditional++;
//            while( buffer.size() - toConsume < 0 ) {
//                System.out.println("CONSUMER id: " + Thread.currentThread().getId() + " is waiting on consumerCondition(size:" + countConsumerConditional + ") to get: " + toConsume);
//                isConsumerConditionalEmpty = false;
//                consumerCondition.await();
//            }
//            if(countConsumerConditional > 0){
//                countConsumerConditional--;
//            }
//            isConsumerConditionalEmpty = true;
//            for( int j = 0; j < toConsume; j++ ) {
//                buffer.removeFirst();
//            }
//            System.out.println("CONSUMER id: " + Thread.currentThread().getId() + " get: " + toConsume);
//            System.out.println("Buffer size:" + buffer.size());
//
//
//            otherConsumerCondition.signal();
//            producerCondition.signal();
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//
//
//    }
//

















































