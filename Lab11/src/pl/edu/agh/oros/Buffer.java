package pl.edu.agh.oros;

import org.jcsp.lang.*;

public class Buffer implements CSProcess {
    private final ChannelInputInt channelInputProducer;
    private final ChannelOutputInt channelOutputConsumer;
    private int currentValue;

    public Buffer(final ChannelInputInt channelInProducer, final ChannelOutputInt channelOutConsumer) {
        this.channelInputProducer = channelInProducer;
        this.channelOutputConsumer = channelOutConsumer;
    }

    public void run ()
    {
        while(true){
            //waiting for produce action ?
            currentValue = channelInputProducer.read();
            //push val to consumer !
            channelOutputConsumer.write(currentValue);
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }
}
