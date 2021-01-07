package pl.edu.agh.oros;

import org.jcsp.lang.*;
import java.util.Arrays;

public class IndexBufferController implements CSProcess {
    private final ChannelInputInt[] channelsWaitForAsk;
    private final ChannelOutputInt[] channelsOutputIndex;
    private final Alternative alternativeWaitForAsk;
    private final int bufferSize;
    int indexBuff = 0;

    public IndexBufferController(ChannelInputInt[] channelsWaitForAsk, ChannelOutputInt[] channelOutputIndex, int bufferSize) {
        this.channelsWaitForAsk = channelsWaitForAsk;
        this.channelsOutputIndex = channelOutputIndex;
        this.bufferSize = bufferSize;

        final Guard[] guardsForAsks = (Guard[]) Arrays.stream(channelsWaitForAsk).toArray(Guard[]::new);
        alternativeWaitForAsk = new Alternative(guardsForAsks);
    }


    @Override
    public void run() {

        while(true){
            //fairly make choice between asking processes
            int indexPC = alternativeWaitForAsk.fairSelect();

            channelsWaitForAsk[indexPC].read();
            channelsOutputIndex[indexPC].write(indexBuff);

            indexBuff = (indexBuff + 1) % bufferSize;
        }
    }
}
