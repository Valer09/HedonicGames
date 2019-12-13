package Structures;
import Interfaces.CoalitionStructure;

import java.util.ArrayList;

public class MFCoalitionStructure implements CoalitionStructure {
    private ArrayList <Coalition> coalitions;

    @Override
    public Coalition getCoalition(Integer i) {
        return coalitions.get(i);
    }
}
