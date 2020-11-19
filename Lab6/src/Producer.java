class Producer implements  Runnable
{

    private Monitor monitor;
    Buffer buffer;

    public Producer(Monitor m, Buffer buffer)
    {
        this.buffer = buffer;
        monitor = m;
    }
    @Override
    public void run() {

        try{

            while(true) {
                int i = monitor.startPut();
                Thread.sleep((int) (Math.random() * 100));
                buffer.put(i, 1);
                monitor.finishPut(i);
            }
        }catch(Exception e)
        {

        }
    }
}