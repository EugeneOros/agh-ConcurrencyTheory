package pl.edu.agh.oros;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private One2OneChannelInt channel;

    // constructor
    public Producer(final One2OneChannelInt out) {
        channel = out;
    }
    // end constructor

    public void run() {
        int item;
        ChannelOutputInt channelOutput = channel.out();
        for (int k = 0; k < 100; k++) {
            item = k ;
            channelOutput.write(item);
        }
        channelOutput.write(-1);
        System.out.println("Producer ended.");
    } // run
} // class Producer2
