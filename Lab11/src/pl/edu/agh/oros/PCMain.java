package pl.edu.agh.oros;

import org.jcsp.lang.*;
import java.util.Arrays;
import java.util.stream.Stream;


public final class PCMain {
    static final int countConsumers = 10;
    static final int countProducers = 10;
    static final int bufferSize = 100;

    public static void main(String[] args) {
        new PCMain();
    }

    public PCMain(){
        // channels between Producer and Buffer
        final Any2OneChannelInt[] channelProducerBuffer = new Any2OneChannelInt[bufferSize];
        Arrays.setAll(channelProducerBuffer, ignore -> Channel.any2oneInt());

        final ChannelInputInt[] inProducerBuffer = new ChannelInputInt[bufferSize];
        Arrays.setAll(inProducerBuffer, index -> channelProducerBuffer[index].in());
        final ChannelOutputInt[] outProducerBuffer =  new ChannelOutputInt[bufferSize];
        Arrays.setAll(outProducerBuffer, index -> channelProducerBuffer[index].out());

        // channels between Buffer and Consumer
        final One2AnyChannelInt[] channelBufferConsumer =  new One2AnyChannelInt[bufferSize];
        Arrays.setAll(channelBufferConsumer, ignore -> Channel.one2anyInt());

        final ChannelInputInt[] inBufferConsumer =  new ChannelInputInt[bufferSize];
        Arrays.setAll(inBufferConsumer, index -> channelBufferConsumer[index].in());
        final ChannelOutputInt[] outBufferConsumer = new ChannelOutputInt[bufferSize];
        Arrays.setAll(outBufferConsumer, index -> channelBufferConsumer[index].out());

        CSProcess[] procBuffer = new CSProcess[bufferSize];
        Arrays.setAll(procBuffer, index -> new Buffer(inProducerBuffer[index], outBufferConsumer[index]));

        // concatenate consumers' & producers' streams
        Stream<CSProcess> processesP = createProducers(outProducerBuffer);
        Stream<CSProcess> processesC = createConsumers(inBufferConsumer);
        Stream<CSProcess> processes = Stream.concat(processesP, processesC);
        // concatenate with buffer
        CSProcess[] procList = Stream.concat(processes,
                Arrays.stream(procBuffer)).toArray(CSProcess[]::new);

        Parallel parallel = new Parallel(procList);
        parallel.run();
    }

    private Stream<CSProcess> createProducers(ChannelOutputInt[] outputProducerBuffer){
        // channel Producer IndexController for get
        final One2OneChannelInt[] channelIndexGet = createOne2OneChannelInt(countProducers);
        final ChannelInputInt[] inputIndexGet = createChannelInputInt(channelIndexGet, countProducers);
        final ChannelOutputInt[] outputIndexGet = createChannelOutputInt(channelIndexGet, countProducers);
        //channel Producer IndexController for Ask
        final One2OneChannelInt[] channelIndexAsk = createOne2OneChannelInt(countProducers);
        final ChannelInputInt[] inputIndexAsk = createChannelInputInt(channelIndexAsk, countProducers);
        final ChannelOutputInt[] outputIndexAsk = createChannelOutputInt(channelIndexAsk, countProducers);
        // Producers
        CSProcess[] processes = new CSProcess[countProducers];
        Arrays.setAll(processes, index -> new Producer(outputIndexAsk[index], inputIndexGet[index], outputProducerBuffer));
        // Index Buffer Controller
        CSProcess indexBufferController = new IndexBufferController(inputIndexAsk, outputIndexGet, bufferSize);

        return Stream.concat(Stream.of(indexBufferController), Arrays.stream(processes));
    }


    private Stream<CSProcess> createConsumers(ChannelInputInt[] inBufCons){
        //channel Consumer IndexController for Get
        final One2OneChannelInt[] channelIndexGet = createOne2OneChannelInt(countConsumers);
        final ChannelInputInt[] inputIndexGet = createChannelInputInt(channelIndexGet, countConsumers);
        final ChannelOutputInt[] outputIndexGet = createChannelOutputInt(channelIndexGet, countConsumers);
        //channel Consumer IndexController for Ask
        final One2OneChannelInt[] channelIndexAsk = createOne2OneChannelInt(countConsumers);
        final ChannelInputInt[] inputIndexAsk = createChannelInputInt(channelIndexAsk, countConsumers);
        final ChannelOutputInt[] outputIndexAsk = createChannelOutputInt(channelIndexAsk, countConsumers);
        // Consumers
        CSProcess[] processes = new CSProcess[countConsumers];
        Arrays.setAll(processes, index -> new Consumer(outputIndexAsk[index], inputIndexGet[index], inBufCons));
        // Index Buffer Controller
        CSProcess indexBufferController = new IndexBufferController(inputIndexAsk, outputIndexGet, bufferSize);

        return Stream.concat(Stream.of(indexBufferController), Arrays.stream(processes));
    }

    private One2OneChannelInt[] createOne2OneChannelInt(int size){
        One2OneChannelInt[] channel = new One2OneChannelInt[size];
        Arrays.setAll(channel, ignore -> Channel.one2oneInt());
        return channel;
    }

    private ChannelInputInt[] createChannelInputInt(One2OneChannelInt[] channels, int size){
        final ChannelInputInt[] inputs = new ChannelInputInt[size];
        Arrays.setAll(inputs, index -> channels[index].in());
        return inputs;
    }

    private ChannelOutputInt[] createChannelOutputInt(One2OneChannelInt[] channels, int size){
        final ChannelOutputInt[] outputs = new ChannelOutputInt[size];
        Arrays.setAll(outputs, index -> channels[index].out());
        return outputs;
    }
} // class PCMain

