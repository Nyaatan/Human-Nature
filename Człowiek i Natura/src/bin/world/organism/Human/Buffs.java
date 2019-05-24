package bin.world.organism.Human;

import lib.Enums;

import java.util.ArrayList;

class Buffs {
    private ArrayList<Enums.Buff> buffs;

    public Buffs()
    {
        this.buffs = new ArrayList<>();
    }

    void add(Enums.Buff buff)
    {
        this.buffs.remove(buff);
        this.buffs.add(buff);
    }

    void remove(Enums.Buff buff)
    {
        this.buffs.remove(buff);
    }
}
