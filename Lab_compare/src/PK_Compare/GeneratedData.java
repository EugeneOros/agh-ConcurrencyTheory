package PK_Compare;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class GeneratedData {
    List<Integer> data;

    public GeneratedData(String fileName){
        data = new LinkedList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach((String line) -> data.add(Integer.parseInt(line)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(data);
    }

    public int get(){
        if(data.isEmpty()) return -1;
        return data.remove(0);
    }
}
