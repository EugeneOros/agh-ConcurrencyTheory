package ActiveObject.Servant;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Buffer {
    private final Queue<Integer> buffer;
    public final int M;

    public Buffer(int M){
        this.M = M;
        this.buffer = new LinkedList<>();
    }

    public void put(List<Integer> listToPut){
        if (buffer.size() + listToPut.size() > M) {
            throw new IllegalStateException("Added to full buffer");
        } else {
            buffer.addAll(listToPut);
        }
    }

    public List<Integer> get(int countToGet){
        List<Integer> result = new LinkedList<>();

        for (int i = 0; i < countToGet; i++) {
            result.add(buffer.remove());
        }
        return result;
    }

    public boolean isFull() {
        return M == buffer.size();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean canPut(int count) {
        return buffer.size() + count <= M;
    }

    public int size() {return buffer.size();}
}
