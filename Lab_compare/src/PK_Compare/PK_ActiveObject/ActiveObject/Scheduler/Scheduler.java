package PK_Compare.PK_ActiveObject.ActiveObject.Scheduler;

import PK_Compare.PK_ActiveObject.ActiveObject.MethodRequest.IMethodRequest;
import PK_Compare.PK_ActiveObject.ActiveObject.Servant.Buffer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread{
    private final Queue<IMethodRequest> mainQueue;
    private final Queue<IMethodRequest> secondQueue;
    Buffer buffer;

    public Scheduler(Buffer buffer){
        mainQueue = new ConcurrentLinkedQueue<>();
        secondQueue = new ConcurrentLinkedQueue<>();
        this.buffer = buffer;
    }

    public void enqueue(IMethodRequest request){
        mainQueue.add(request);
    }

    @Override
    public void run(){
        while(true){
            while(!secondQueue.isEmpty()){
                IMethodRequest methodRequest = secondQueue.peek();

                if(methodRequest.guard()){
                    methodRequest.call();
                    secondQueue.remove();
                    System.out.println("MethodRequest done; name: " + methodRequest.toString());
                    System.out.println("Buffer filled size: " + buffer.size() + "\n");
                } else break;
            }

            if(!mainQueue.isEmpty()){
                IMethodRequest methodRequest = mainQueue.remove();

                if(methodRequest.guard()){
                    methodRequest.call();
                    System.out.println("MethodRequest done; name: " + methodRequest.toString());
                    System.out.println("Buffer filled size: " + buffer.size() + "\n");
                } else {
                    secondQueue.add(methodRequest);
                }
            }

        }
    }

}

