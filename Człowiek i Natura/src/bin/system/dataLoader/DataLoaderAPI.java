package bin.system.dataLoader;

import bin.enums.item.ItemName;
import bin.system.GlobalSettings;
import bin.system.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class DataLoaderAPI {
    public static ArrayList<String> getConfig(String config, String fileName) { return GlobalSettings.dataLoader.getConfig(config,fileName); }
    public static HashMap<String,ArrayList<String>> getBlockConfig(String blockName, String fileName) {
        return GlobalSettings.dataLoader.getBlockConfig(blockName,fileName);}
    public static Pair<String, String> getItemDescription(ItemName name) { return GlobalSettings.dataLoader.getItemDescription(name); }
}
