package pl.edu.agh.oros;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;

public class Producer implements CSProcess {
    private final ChannelOutputInt channelAskForIndex;
    private final ChannelInputInt channelGetIndex;
    private final ChannelOutputInt[] channelsBuffer;

    public Producer(ChannelOutputInt channelAskForIndex, ChannelInputInt channelGetIndex, ChannelOutputInt[] channelsBuffer) {
        this.channelAskForIndex = channelAskForIndex;
        this.channelGetIndex = channelGetIndex;
        this.channelsBuffer = channelsBuffer;
    }

    public void run (){
        int value;
        while (true) {
            //signal that you want to get index of buffer for producing!
            channelAskForIndex.write(1);
            //wait until get this index
            int index = channelGetIndex.read();
            //put value to buffer of this index!
            value = (int) (Math.random() * 100) + 1;
            channelsBuffer[index].write(value);

            System.out.println("Putting... value: " + value + "; buffer index: " + index );
        }
    }
} // class Producer
