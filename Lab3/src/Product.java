
public class Product {
    public final static int MAX_SIZE = 10;
    private final Integer[] buffer = new Integer[MAX_SIZE];

    public Product() {
        for(int i=0; i<MAX_SIZE; i++) {
            buffer[i]=0;
        }
    }

    private int countFreePlaces() {
        int counter = 0;
        for(int i=0; i<MAX_SIZE; i++) {
            if(buffer[i]==0)counter++;
        }

        return counter;
    }

    public synchronized void produce(int value) throws InterruptedException {
        int placesToProduce = countFreePlaces();

        while(placesToProduce==0){
            wait();
            placesToProduce = countFreePlaces();
        }

        for(int i=0; i<MAX_SIZE; i++){
            if(buffer[i]==0){
                buffer[i]=value;
                continue;
            }
        }

        notify();
    }

    public synchronized int consume() throws InterruptedException {
        int result = 0;
        int placesToConsume = MAX_SIZE-countFreePlaces();

        while(placesToConsume==0){
            wait();
            placesToConsume = MAX_SIZE-countFreePlaces();
        }

        for(int i=0; i<MAX_SIZE; i++){
            if(buffer[i]!=0){
                result = buffer[i];
                buffer[i]=0;
                continue;
            }
        }

        notify();
        return result;
    }

}

