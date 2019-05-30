package bin.system.dataLoader;

import lib.Enums;
import bin.system.GlobalSettings;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class DataLoaderAPI {
    public ArrayList<String> getConfig(String config, String fileName) { return GlobalSettings.dataLoader.getConfig(config,fileName); }
    public HashMap<String,ArrayList<String>> getBlockConfig(String blockName, String fileName) {
        return GlobalSettings.dataLoader.getBlockConfig(blockName,fileName);}
    public Pair<String, String> getItemDescription(Enums.ItemName name) { return GlobalSettings.dataLoader.getItemDescription(name); }
    public String getSpecimenName(Enums.Species.AllSpecies specimen) { return GlobalSettings.dataLoader.getSpecimenName(specimen); }
}
