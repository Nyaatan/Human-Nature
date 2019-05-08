package bin.system;

import bin.world.World;

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

//TODO COMMENTS
public class DataLoader {

    private static String pathCfg = System.getProperty("user.dir") + "\\cfg";
    private static String pathSrcCfg = System.getProperty("user.dir") + "\\src\\cfg";

    private static HashMap<String,ArrayList<String>> configs = new HashMap<>();

    public DataLoader() {
        ArrayList<File> filesInFolder = new ArrayList<>();
        try{ //does it for dev version
            filesInFolder = (ArrayList<File>) Files.walk(Paths.get(pathSrcCfg)) //collects paths to each file in cfg folder and iterates by files
                    .filter(Files::isRegularFile) //filters regular files to futher use
                    .map(Path::toFile) //maps every file by it's path
                    .collect(Collectors.toList()); //put's files into a list
        } catch (IOException e) { //if dev path is not found, do the same for release version. It just didn't work when I tried to do it regular way, so here will this heresy be
            try {
                filesInFolder = (ArrayList<File>) Files.walk(Paths.get(pathCfg)) //as above, but with different path
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        for(File file : filesInFolder) {
            try {
                loadConfig(file);
            } catch (Exception e) {
                loadConfig(file);
            }
        }
    }

    public static ArrayList<String> getConfig(String config) {
        for(String configLine : configs.get("config"))
        {
            if(configLine.contains(config)) { return parseConfig(configLine); }
        }
        return null;
    } //returns a config line from "config" file

    public static HashMap<String,ArrayList<String>> getSpeciesConfig(String Specimen)
    {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        boolean inParsing = false;

        for(String configLine : configs.get("species"))
        {
            if(configLine.contains(Specimen.toUpperCase())) { inParsing = true; }
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
    } ////returns a config block from "species" file

    private static void loadConfig(File file) //loads config lines from file
    {
        ArrayList<String> outputLines = new ArrayList<>();
        Scanner configScan = null;
        try {
            configScan = new Scanner(file);
        } catch (FileNotFoundException e) {
            World.WorldSPI.log(e.toString());
        }
        assert configScan != null;
        while(configScan.hasNextLine()) { outputLines.add(configScan.nextLine()); }
        configs.put(file.getName(), outputLines);
    }

    private static ArrayList<String> parseConfig(String configLine) //config format: config_name = <config>
    {
        return new ArrayList<>(Arrays.asList(configLine.split("<")[1].split(">")[0].split(",")));
    }
}
