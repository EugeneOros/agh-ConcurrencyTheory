package Lab8.PK_Monitor;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProducerM extends Thread {
    private final BufferM bufer;
    private final int M;
    private int countWork;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private int countMRequestsDone = 0;


    public ProducerM(BufferM buf, int M, int countWork)  {
        bufer = buf;
        this.M = M;
        this.countWork = countWork;
    }

    private void doSomething(){
        for(int i=0; i<1000; i++)
        {
            Math.sin(23);
            Math.exp(34);
            Math.abs(-12);
        }
    }

    public void run() {
        int a[] = new int[(int) (Math.random()*(M-1)+1)];
        for(int i=0; i<a.length; i++) {
            a[i] = (int)(Math.random() * 10) + 1;
        }

        while(running.get()) {
            try {
                bufer.put(a);
                countMRequestsDone++;
            } catch (InterruptedException e) {
                running.set(false);
                System.out.println("P "+countMRequestsDone);
            }
            for(int i=0; i<countWork; i++){
                doSomething();
            }
            if(Thread.interrupted()){
                running.set(false);
                System.out.println("P "+countMRequestsDone);
            }
        }
    }
}
