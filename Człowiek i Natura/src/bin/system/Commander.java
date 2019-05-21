package bin.system;

import lib.Enums;

public class Commander {
    Enum move = null;
    Enum use = null;
    Enum craft = null;

    public void giveCommand(Enums.Commands.Move move)
    {
        this.move = move;
        this.use = null;
        this.craft = null;
    }
    public void giveCommand(Enums.Commands.Use use)
    {
        this.use = use;
        this.move = null;
        this.craft = null;
    }
    public void giveCommand(Enums.Commands.Craft craft)
    {
        this.craft = craft;
        this.move = null;
        this.use = null;
    }

    public Enum hearCommand()
    {
        if(this.move!=null)
            return this.move;
        else if(this.use!=null)
            return this.use;
        else if(this.craft!=null)
            return this.craft;
        else return null;
    }
}
