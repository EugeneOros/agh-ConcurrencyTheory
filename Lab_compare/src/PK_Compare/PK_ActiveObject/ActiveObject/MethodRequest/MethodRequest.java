package PK_Compare.PK_ActiveObject.ActiveObject.MethodRequest;


import  PK_Compare.PK_ActiveObject.ActiveObject.Servant.Buffer;

public abstract class MethodRequest implements IMethodRequest {
    protected Buffer buffer;
    protected Integer id = null;

    public MethodRequest(Buffer buffer, Integer id){
        this.buffer = buffer;
        this.id = id;
    }
}
