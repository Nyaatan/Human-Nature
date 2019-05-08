package bin.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//TODO COMMENTS
public class DataLoader {

    private String pathCfg = System.getProperty("user.dir") + "\\src\\cfg\\config";
    private String pathSpecies = System.getProperty("user.dir") + "\\src\\cfg\\species";

    private ArrayList<String> configLines = new ArrayList<>();
    private ArrayList<String> speciesLines = new ArrayList<>();

    public DataLoader(){
        loadConfig(pathCfg, configLines);
        loadConfig(pathSpecies, speciesLines);
    }

    public ArrayList<String> getConfig(String config) {
        for(String configLine : configLines)
        {
            if(configLine.contains(config)) { return parseConfig(configLine); }
        }
        return null;
    }

    public HashMap<String,ArrayList<String>> getSpeciesConfig(String Specimen)
    {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        boolean inParsing = false;

        for(String configLine : speciesLines)
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
                    inParsing = false;
                    return result;
                }
            }
        }
        return null;
    }

    private void loadConfig(String inputPath, ArrayList<String> outputLines)
    {
        File configFile = new File(inputPath);
        Scanner configScan = null;
        try {
            configScan = new Scanner(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(configScan.hasNextLine()) { outputLines.add(configScan.nextLine()); }
    }

    private ArrayList<String> parseConfig(String configLine) //config format: config_name = <config>
    {
        return new ArrayList<>(Arrays.asList(configLine.split("<")[1].split(">")[0].split(",")));
    }
}
