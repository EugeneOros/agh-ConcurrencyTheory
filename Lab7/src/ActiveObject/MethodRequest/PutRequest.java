package ActiveObject.MethodRequest;

import ActiveObject.Servant.Buffer;
import ActiveObject.Scheduler.Future;

import java.util.List;

public class PutRequest extends MethodRequest {
    private final List<Integer> listToPut;
    Future future;

    public PutRequest(Integer id, Buffer buffer, List<Integer> listToPut, Future future){
        super(buffer, id);
        this.listToPut = listToPut;
        this.future = future;
    }

    @Override
    public void call() {
        buffer.put(listToPut);
        future.set(null);
    }

    @Override
    public boolean guard() { // guard
        return buffer.canPut(listToPut.size());
    }

    @Override
    public String toString() {
        return "{Prod" + id + "}";
    }
}
