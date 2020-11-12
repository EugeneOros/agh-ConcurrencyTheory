import java.util.Random;

class Producer implements  Runnable {
    Product product;
    Random rand = new Random();
    public Producer(Product p)
    {
        product = p;
    }
    @Override
    public void run() {

        try {
            while(true) {
                int value = rand.nextInt(10) +1;
                product.produce(value);
                System.out.println("Producent: "+Thread.currentThread().getId()+" value: "+value);

            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}