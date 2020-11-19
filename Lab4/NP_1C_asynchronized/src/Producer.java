import java.util.Random;

class Producer implements  Runnable
{
    private static int MAX_SIZE_TO_INSERT = 5;
    Product product;
    Random rand = new Random();
    public Producer(Product p)
    {
        product = p;
    }
    @Override
    public void run() {

        try{

            while(true) {
                int i = rand.nextInt(MAX_SIZE_TO_INSERT-1)+1;
                product.putStart(i);
                Thread.sleep((int) (Math.random() * 100));
                product.putFinish(i);
            }
        }catch(Exception e)
        {

        }
    }
}

