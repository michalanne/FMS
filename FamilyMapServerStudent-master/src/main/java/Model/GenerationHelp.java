package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationHelp {

    private List<String> data = new ArrayList<>();

    public GenerationHelp(List mainList) {
        this.data = mainList;
    }

    public String getRandom() {
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }

}
