package PK_Compare.PK_ActiveObject.ActiveObject.Proxy;

import PK_Compare.PK_ActiveObject.ActiveObject.Scheduler.Future;
import PK_Compare.PK_ActiveObject.ActiveObject.MethodRequest.GetRequest;
import PK_Compare.PK_ActiveObject.ActiveObject.MethodRequest.PutRequest;
import PK_Compare.PK_ActiveObject.ActiveObject.Scheduler.Scheduler;
import PK_Compare.PK_ActiveObject.ActiveObject.Servant.Buffer;

import java.util.List;

public class Proxy {
    private final Buffer buffer;
    private final Scheduler scheduler;
    public final int M;

    public Proxy(int M, int countWait){
        this.M = M;
        this.buffer = new Buffer(M, countWait);
        this.scheduler = new Scheduler(buffer);
        scheduler.start();
    }

    public Future put(List<Integer> listToPut, Integer id){
        Future future = new Future();
        PutRequest putRequest = new PutRequest(id, buffer, listToPut, future);

        scheduler.enqueue(putRequest);

        return future;
    }

    public Future get(int countToGet, Integer id){
        Future future = new Future();
        GetRequest getRequest = new GetRequest(buffer, future, countToGet, id);

        scheduler.enqueue(getRequest);

        return future;
    }
}
