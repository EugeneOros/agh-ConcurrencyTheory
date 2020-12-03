package PK_Compare.PK_Monitor;

import PK_Compare.GeneratedData;
import PK_Compare.WorkThreads.WorkConsumer;

public class ConsumerM extends WorkConsumer {
    private BufferM _buf;

    public ConsumerM(BufferM buf, int countWork, GeneratedData generatedData) {
        super(countWork, generatedData);
        this._buf = buf;
    }

    public void run() {
        while(true) {
            int countToGet = getToConsume();
            _buf.get(countToGet);
            if(countToGet == -1){
                doSomething();
                return;
            }
            doSomething();
        }
    }
}
