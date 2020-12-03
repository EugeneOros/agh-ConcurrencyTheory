package PK_Compare.WorkThreads;

import PK_Compare.GeneratedData;

public abstract class WorkConsumer extends WorkThread {
    public WorkConsumer(int countWork, GeneratedData generatedData) {
        super(countWork, generatedData);
    }

    public int getToConsume(){
        return super.getRandom();
    }
}
