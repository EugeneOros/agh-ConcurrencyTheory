package Lab8.PK_ActiveObject.ActiveObject.MethodRequest;

import Lab8.PK_ActiveObject.ActiveObject.Scheduler.Future;
import Lab8.PK_ActiveObject.ActiveObject.Servant.Buffer;

import java.util.List;

public class GetRequest extends MethodRequest {
    Future future;
    final int countToGet;

    public GetRequest(Buffer buffer, Future future, int countToGet, Integer id) {
        super(buffer, id);
        this.future = future;
        this.countToGet = countToGet;
    }

    @Override
    public void call() {
        List<Integer> result = buffer.get(countToGet);
        future.set(result);
    }

    @Override
    public boolean guard() {
        return countToGet <= buffer.size() ;
    }

    @Override
    public String toString() {
        return "{Cons" + id + "}";
    }
}
