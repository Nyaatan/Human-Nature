package bin.system;

import lib.Enums;
import lib.Enums.Commands.Craft;
import lib.Enums.Commands.Move;
import lib.Enums.Commands.Use;

public class Commander {
    private Enum move = null;
    private Enum use = null;
    private Enum craft = null;

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
    public void giveCommand(Enum command)
    {
        if(command.getClass()==Move.class) giveCommand((Move) command);
        
        else if(command.getClass()==Use.class) giveCommand((Use) command);
        
        else if(command.getClass()==Craft.class) giveCommand((Craft) command);
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
