package bin.system;

import bin.enums.item.ItemName;
import bin.world.WorldSPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataLoader {
    private static String pathDir = System.getProperty("user.dir");

    private static String pathCfg = pathDir  + "\\cfg";
    private static String pathSrcCfg = pathDir + "\\src\\cfg";

    private static String pathItemDescr = pathDir + "\\lang\\" + GlobalSettings.getLanguage().toString().toLowerCase() + "\\descriptions\\items";
    private static String pathSrcItemDescr = pathDir + "\\src\\lang\\" + GlobalSettings.getLanguage().toString().toLowerCase() + "\\descriptions\\items";

    private static HashMap<String,ArrayList<String>> configs = new HashMap<>(); //contains config lines equivalent to their file, which name is the key
    private static HashMap<String,ArrayList<String>> descriptionsItem = new HashMap<>();

    public DataLoader() {
        loadConfigs(configs, pathCfg, pathSrcCfg);
        loadConfigs(descriptionsItem, pathItemDescr, pathSrcItemDescr);
    }

    public static ArrayList<String> getConfig(String config, String fileName) {
        for(String configLine : configs.get(fileName))
        {
            if(configLine.contains(config)) { return parseConfig(configLine); }
        }
        return null;
    } //returns a config line from "config" file

    public static HashMap<String,ArrayList<String>> getBlockConfig(String blockName, String fileName)
    {
        return parseBlockConfig(configs.get(fileName.toLowerCase()), blockName);
    } ////returns a config block from "species" file

    public static Pair<String, String> getItemDescription(ItemName name) {
        Pair<String, String> fullDescription = new Pair<>("", "");
        fullDescription.setX(descriptionsItem.get(name.toString().toLowerCase()).get(0));
        StringBuilder description = new StringBuilder();
        for(String line : descriptionsItem.get(name.toString().toLowerCase()))
        {
            if(!line.equals(fullDescription.getX())) description.append(line);
        }
        fullDescription.setY(description.toString());
        return fullDescription;
    }

    private static void loadConfigs(HashMap<String,ArrayList<String>> configs, String path, String pathSrc)
    {
        ArrayList<File> filesInFolder = new ArrayList<>();
        try{ //does it for dev version
            filesInFolder = (ArrayList<File>) Files.walk(Paths.get(pathSrc)) //collects paths to each file in cfg folder and iterates by files
                    .filter(Files::isRegularFile) //filters regular files to futher use
                    .map(Path::toFile) //maps every file by it's path
                    .collect(Collectors.toList()); //put's files into a list
        } catch (IOException e) { //if dev path is not found, do the same for release version. It just didn't work when I tried to do it regular way, so here will this heresy be
            try {
                filesInFolder = (ArrayList<File>) Files.walk(Paths.get(path)) //as above, but with different path
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        for(File file : filesInFolder) {
            try {
                loadConfig(file, configs);
            } catch (Exception e) {
                loadConfig(file, configs);
            }
        }
    }

    private static void loadConfig(File file, HashMap<String, ArrayList<String>> configs) //loads config lines from file
    {
        ArrayList<String> outputLines = new ArrayList<>();
        Scanner configScan = null;
        try {
            configScan = new Scanner(file);
        } catch (FileNotFoundException e) {
            GameSystem.addLog(e.toString());
        }
        assert configScan != null;
        while(configScan.hasNextLine()) { outputLines.add(configScan.nextLine()); }
        configs.put(file.getName(), outputLines);
    }

    private static HashMap<String,ArrayList<String>> parseBlockConfig(ArrayList<String> configLines, String blockName)
    {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        boolean inParsing = false;

        for(String configLine : configLines)
        {
            if(configLine.contains(blockName.toUpperCase())) { inParsing = true; }
            if(inParsing)
            {
                if(configLine.contains("="))
                {
                    result.put(configLine.trim().split("=")[0].trim(), parseConfig(configLine.trim().split("=")[1]));
                }
                if(configLine.contains("}"))
                {
                    return result;
                }
            }
        }
        return null;
    }

    private static ArrayList<String> parseConfig(String configLine) //config format: config_name = <config>
    {
        return new ArrayList<>(Arrays.asList(configLine.split("<")[1].split(">")[0].split(",")));
    }
}
