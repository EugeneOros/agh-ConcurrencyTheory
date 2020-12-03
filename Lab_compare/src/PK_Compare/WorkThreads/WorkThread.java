package PK_Compare.WorkThreads;

import PK_Compare.GeneratedData;

public abstract class WorkThread extends Thread {
    private int countWork;
    private GeneratedData generatedData;
    public WorkThread(int countWork, GeneratedData generatedData){
        this.countWork = countWork;
        this.generatedData = generatedData;
    }
    public void doSomething(){
        for(int i=0; i<countWork; i++){
            //coś tam robi
            Math.abs(-12);
            Math.exp(3);
            Math.cos(1.23);
        }
    }

    public int getRandom() {
        return generatedData.get();
    }

}
