package bin.world.organism.Human;

import lib.Enums;

import java.io.Serializable;
import java.util.ArrayList;

public class Buffs implements Serializable {
    private ArrayList<Enums.Buff> buffs;

    public Buffs()
    {
        this.buffs = new ArrayList<>();
    }

    public void add(Enums.Buff buff)
    {
        this.buffs.remove(buff);
        this.buffs.add(buff);
    }

    public void remove(Enums.Buff buff)
    {
        this.buffs.remove(buff);
    }

    public boolean contains(Enums.Buff buff) {
        return buffs.contains(buff);
    }
}
