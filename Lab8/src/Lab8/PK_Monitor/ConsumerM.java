package Lab8.PK_Monitor;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerM extends Thread {
    private final BufferM bufer;
    private final int M;
    private int countWork;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private int countMRequestsDone = 0;

    public ConsumerM(BufferM buf, int M, int countWork) {
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
        while(running.get()) {
            try {
                bufer.get((int) (Math.random()*(M-1)+1));
                countMRequestsDone++;
            } catch (InterruptedException e) {
                running.set(false);
                System.out.println("C "+countMRequestsDone);
            }
            for(int i=0; i<countWork; i++){
                doSomething();
            }
            if(Thread.interrupted()){
                running.set(false);
                System.out.println("C "+countMRequestsDone);
            }
        }
    }
}
