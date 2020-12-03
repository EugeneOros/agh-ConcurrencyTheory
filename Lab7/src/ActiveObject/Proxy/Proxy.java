package ActiveObject.Proxy;

import ActiveObject.Scheduler.Future;
import ActiveObject.MethodRequest.GetRequest;
import ActiveObject.MethodRequest.PutRequest;
import ActiveObject.Scheduler.Scheduler;
import ActiveObject.Servant.Buffer;

import java.util.List;

public class Proxy {
    private final Buffer buffer;
    private final Scheduler scheduler;
    public final int M;

    public Proxy(int M){
        this.M = M;
        this.buffer = new Buffer(M);
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
