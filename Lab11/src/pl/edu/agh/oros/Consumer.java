package pl.edu.agh.oros;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;

public class Consumer implements CSProcess {
    private final ChannelOutputInt channelAskForIndex;
    private final ChannelInputInt channelGetIndex;
    private final ChannelInputInt[] channelBuffer;

    public Consumer(ChannelOutputInt channelAskForIndex, ChannelInputInt channelGetIndex, ChannelInputInt[] channelBuffer){
        this.channelAskForIndex = channelAskForIndex;
        this.channelGetIndex = channelGetIndex;
        this.channelBuffer = channelBuffer;
    }

    public void run () {
        int value;
        try {
            while (true) {
                //signal that you want to get index of buffer for consuming!
                channelAskForIndex.write(0);
                //wait until get this index
                int index = channelGetIndex.read();
                //wait until get value from buffer of this index?
                value = channelBuffer[index].read();

                System.out.println("Getting... value: " + value + "; buffer index: " + index );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
