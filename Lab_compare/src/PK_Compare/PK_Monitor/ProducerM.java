package PK_Compare.PK_Monitor;

import PK_Compare.GeneratedData;
import PK_Compare.WorkThreads.WorkProducer;

import java.util.List;

public class ProducerM extends WorkProducer {
    private BufferM _buf;

    public ProducerM(BufferM buf, int countWork, GeneratedData generatedData)  {
        super(countWork, generatedData);
        this._buf = buf;
    }

    public void run() {
//        int a[] = new int[];
//        for(int i=0; i<a.length; i++) {
//            a[i] = (int)(Math.random() * 10) + 1;
//        }
        List<Integer> listToPut = getToProduce();

        while(true) {
            _buf.put(listToPut);
            listToPut = getToProduce();
            if(listToPut == null){
                doSomething();
                return;
            }
            doSomething();
        }
    }
}
