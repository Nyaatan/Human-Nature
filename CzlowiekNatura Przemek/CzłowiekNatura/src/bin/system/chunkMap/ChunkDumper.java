package bin.system.chunkMap;

import bin.system.GlobalSettings;
import lib.Pair;

import java.io.*;

public class ChunkDumper implements Serializable{
    private String basePath = System.getProperty("user.dir") + "\\saves\\" + GlobalSettings.worldName;
    private String basePathSrc = System.getProperty("user.dir") + "\\src\\saves\\" + GlobalSettings.worldName;

    public void Dump(Chunk chunk)
    {
        try {
            save(chunk, basePath);

        } catch (Exception ex) {
            try{
                save(chunk, basePathSrc);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) { }
        }
    }

    private void save(Chunk chunk, String basePath) throws IOException {
        String path = basePath + "\\" + chunk.ID.getX().toString() + "," + chunk.ID.getY().toString();
        new File(path).createNewFile();
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(chunk);
        objectOut.close();
        fileOut.close();
    }

    public Chunk load(Pair<Integer,Integer> ID)
    {
        Chunk chunk;
        try {
            String path = basePath + "\\" + ID.getX().toString() + "," + ID.getY().toString();
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            chunk = (Chunk) objectIn.readObject();
            objectIn.close();
            fileIn.close();

        } catch (Exception ex) {
            return null;
        }
        return chunk;
    }
}
