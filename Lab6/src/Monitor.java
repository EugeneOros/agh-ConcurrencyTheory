import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    LinkedList<Integer> emptyElementsQueue = new LinkedList<>();
    LinkedList<Integer> fillElementsQueue = new LinkedList<>();
    Lock lock = new ReentrantLock();
    Condition CZEK_PROD = lock.newCondition();
    Condition CZEK_KONS = lock.newCondition();
    State[] state;
    int bufferSize;
    public Monitor(int bufferSize)
    {
        this.bufferSize=bufferSize;
        state = new State[bufferSize];
        for(int i=0; i<bufferSize; i++)
        {
            emptyElementsQueue.add(i);
        }
        for(int i=0; i<bufferSize; i++)
        {
            state[i]= State.EMPTY;
        }
    }

    private void showStateListCount()
    {
        int empty, inProgressPut, filled, inProgressGet;
        empty=inProgressPut=filled=inProgressGet=0;
        for(int i=0; i<bufferSize; i++)
        {
            if(state[i]== State.EMPTY)empty++;
            else if(state[i]== State.FILLED)filled++;
            else if(state[i]== State.IN_PROGRESS_PUT)inProgressPut++;
            else if(state[i]== State.IN_PROGRESS_GET)inProgressGet++;
        }
        System.out.println("________________________");
        System.out.println("|      EMPTY      | "+empty+"  |\n|      FILLED     | "+filled+"  |\n| IN_PROGRESS_PUT | "+inProgressPut+" |\n| IN_PROGRESS_GET | "+inProgressGet+" |");
        System.out.println("________________________");
    }

    public int startPut() throws InterruptedException {
        lock.lock();
        showStateListCount();
        while(emptyElementsQueue.isEmpty())CZEK_PROD.await();
        int i = emptyElementsQueue.pop();
        state[i]= State.IN_PROGRESS_PUT;
        CZEK_KONS.signal();
        System.out.println("Producer id: "+Thread.currentThread().getId()+"start producing index: "+i);
        lock.unlock();
        return i;

    }

    public void finishPut(int i)
    {
        lock.lock();
        showStateListCount();
        fillElementsQueue.add(i);
        state[i]= State.FILLED;
        CZEK_KONS.signal();
        System.out.println("Producer id: "+Thread.currentThread().getId()+" finished produce index: "+i);
        lock.unlock();
    }
    public int startGet() throws InterruptedException {
        lock.lock();
        showStateListCount();
        while(fillElementsQueue.isEmpty())CZEK_KONS.await();
        int i = fillElementsQueue.pop();
        state[i]= State.IN_PROGRESS_GET;
        System.out.println("Consumer id: "+Thread.currentThread().getId()+"start consuming index: "+i);
        lock.unlock();
        return i;
    }

    public void finishGet(int i)
    {
        lock.lock();
        showStateListCount();
        System.out.println("Consumer id: "+Thread.currentThread().getId()+"finished consume index: "+i);
        state[i]= State.EMPTY;
        emptyElementsQueue.add(i);
        CZEK_PROD.signal();
        lock.unlock();
    }

}