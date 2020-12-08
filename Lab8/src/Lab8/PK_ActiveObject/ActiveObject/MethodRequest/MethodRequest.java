package Lab8.PK_ActiveObject.ActiveObject.MethodRequest;


import Lab8.PK_ActiveObject.ActiveObject.Servant.Buffer;

public abstract class MethodRequest implements IMethodRequest {
    protected Buffer buffer;
    protected Integer id = null;

    public MethodRequest(Buffer buffer, Integer id){
        this.buffer = buffer;
        this.id = id;
    }
}
