package bin.world;

import bin.enums.Directions;
import bin.enums.Species;
import bin.system.GlobalSettings;
import bin.system.Pair;
import bin.world.organism.Organism;

public class WorldSPI {
    public static Pair<Integer,Integer> graveyard = new Pair<>(0,0);

    public static void cleanCorpse(int worldID) {
        GlobalSettings.getWorld(worldID).cleanCorpse(); }
    public static void makeOrganism(int worldID, Species specimen, Pair<Integer,Integer> coords) {
        GlobalSettings.getWorld(worldID).makeOrganism(specimen,coords); }
    public static void setField(int worldID, Pair<Integer,Integer> coords, Organism organism) {
        GlobalSettings.getWorld(worldID).setField(coords, organism); }
    public static Organism getField(int worldID, Pair<Integer,Integer> coords) {
        return GlobalSettings.getWorld(worldID).getField(coords); }
    public static Pair<Integer,Integer> getCoordsInDirection(int worldID, Directions dir, Pair<Integer, Integer> fromCoords) {
        return GlobalSettings.getWorld(worldID).getCoordsInDirection(dir, fromCoords); }
    public static void log(int worldID, Organism organism, String message) {
        GlobalSettings.getWorld(worldID).log(organism, message); }
    public static void log(int worldID, String message) {
        GlobalSettings.getWorld(worldID).log(message); }
}
