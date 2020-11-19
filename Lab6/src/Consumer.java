class Consumer implements Runnable
{

    Monitor monitor;
    Buffer buffer;

    public Consumer(Monitor m, Buffer buffer)
    {
        this.buffer = buffer;
        monitor = m;
    }
    @Override
    public void run() {

        try {

            while(true){
                int i = monitor.startGet();
                Thread.sleep((int) (Math.random() * 100));
                int value = buffer.get(i);
                monitor.finishGet(i);

            }

        }catch(Exception e)
        {

        }
    }
}
