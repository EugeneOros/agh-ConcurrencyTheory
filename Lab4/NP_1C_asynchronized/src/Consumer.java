import java.util.Random;

class Consumer implements Runnable
{
    private static int MAX_SIZE_TO_TAKE = 5;
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
                int i = rand.nextInt(MAX_SIZE_TO_TAKE-1)+1;
                product.getStart(i);
                Thread.sleep((int) (Math.random() * 100));
                product.getFinish(i);
            }

        }catch(Exception e)
        {

        }
    }
}