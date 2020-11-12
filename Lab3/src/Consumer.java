import java.util.Random;

class Consumer implements Runnable {
    Product product;
    Random rand = new Random();
    public Consumer(Product p)
    {
        product = p;
    }
    @Override
    public void run() {

        try {
            while(true){
                int x=product.consume();
                System.out.println("Consumer: "+Thread.currentThread().getId()+" value: "+Integer.toString(x));
            }

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
