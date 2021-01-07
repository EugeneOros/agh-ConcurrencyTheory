package pl.edu.agh.oros;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private One2OneChannelInt in;

    public Consumer( final One2OneChannelInt in) {
        this.in = in;
    }

    public void run() {
        int item;
        while (true) {
            ChannelInputInt channelInputInt = in.in();
            item = channelInputInt.read();
            if (item < 0)
                break;
            System.out.println(item);
        } // for
        System.out.println("Consumer ended.");
    } // run
} // class Consumer2

//czy beda od razu zadania z czer

