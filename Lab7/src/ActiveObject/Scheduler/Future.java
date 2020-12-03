package ActiveObject.Scheduler;

import java.util.List;

public class Future {
    private volatile List<Integer> resultList = null;
    private volatile boolean isAvailable = false;

    public boolean isAvailable(){
        return isAvailable;
    }

    public void set(List<Integer> resultList){
        this.resultList = resultList;
        isAvailable = true;
    }

    public List<Integer> get(){
        return this.resultList;
    }
}

