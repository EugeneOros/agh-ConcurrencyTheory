import java.util.concurrent.ConcurrentHashMap;

public class MyConcurentHashMap {
    private final ConcurrentHashMap<Integer, Integer> buffer ;

    public MyConcurentHashMap(int m){
       buffer = new ConcurrentHashMap<>(m);
    }
    public ConcurrentHashMap<Integer, Integer> getMap(){
        return buffer ;
    }

    public int produce(){

    }

    public int consume(){

    }
}
