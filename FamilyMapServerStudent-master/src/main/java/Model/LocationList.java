package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationList {

    List<LocationHelp> data;

    public LocationList(List<LocationHelp> locations) {
        this.data = locations;
    }

    public LocationHelp getRandom() {
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }

}
