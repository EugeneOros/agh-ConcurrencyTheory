package PK_Compare.WorkThreads;

import PK_Compare.GeneratedData;

import java.util.LinkedList;
import java.util.List;

public abstract class WorkProducer extends WorkThread{

    private int value = 0;

    public WorkProducer(int countWork, GeneratedData generatedData) {
        super(countWork, generatedData);
    }

    protected List<Integer> getToProduce(){
        int output = super.getRandom();
        if(output == -1) return null;

        List<Integer> products = new LinkedList<>();

        for (int i = 0; i < output; i++) {
            int output_value = value;
            products.add(output_value);
            value = value + 1;
        }

        return products;
    }
}
