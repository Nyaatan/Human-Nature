import bin.world.World;

public class Main {

    public static void main(String[] args) {
        World world = new World();
        System.out.println(World.getPoplation());
        World.getOrganisms().get(1).get(0).die();
        World.cleanCorpses();
        System.out.println(World.getPoplation());
    }
}
