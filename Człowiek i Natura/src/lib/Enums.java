package lib;

import java.io.Serializable;

public class Enums implements Serializable {
    public enum Values {
        STRENGTH,INITIATIVE,AGE
    }

    public static class Species {
        public enum Animals {
            WOLF,SHEEP, CYBERSHEEP, HUMAN
        }

        public enum Plants {
            OAK,HOGWEED,FLOWER
        }

        public enum AllSpecies {
            OAK,HOGWEED,FLOWER, WOLF,SHEEP, CYBERSHEEP, HUMAN
        }
    }

    public enum OrganismType {
        PREDATOR,PREY,PLANT, NONE
    }

    public enum Directions {
        UPRIGHT, DOWNLEFT,LEFT,RIGHT,UPLEFT,DOWNRIGHT
    }

    public enum Language {
        PL,EN
    }

    public enum ItemName {
        WOOD,WOOL,MEAT,FUR,BLOOD,CYBERWOOL,QUANTUMCIRCUIT,CYBERHEART,FLOWER,HOGWEEDLEAF,AXE, BRANCH,
        FURCOAT, WOOLENCOAT, WOOLENBOOTS, WOOLENPANTS, WOOLENCAP, WOODENSWORD, BLESSING, MAGICJUICE,
    }

    public enum ItemType {
        EQUIPMENT,CRAFTING,BUFF,RESOURCE
    }

    public static class Commands{

        public static Enum valueOf(String value) { 
            for(Enum command : Move.values())
            {
                if(command.toString().equals(value.toUpperCase())) return command;
            }
            for(Enum command : Use.values())
            {
                if(command.toString().equals(value.toUpperCase())) return command;
            }
            for(Enum command : Craft.values())
            {
                if(command.toString().equals(value.toUpperCase())) return command;
            }
            return null;
        }
        public enum Move{
            UPRIGHT, DOWNLEFT,LEFT,RIGHT,UPLEFT,DOWNRIGHT,WAIT
        }

        public enum Use{
            AXE, FURCOAT, WOOLENCOAT, WOOLENBOOTS, WOOLENPANTS, WOOLENCAP, WOODENSWORD, BLESSING, MAGICJUICE,
        }

        public enum Craft{
            AXE, FURCOAT, WOOLENCOAT, WOOLENBOOTS, WOOLENPANTS, WOOLENCAP, WOODENSWORD, BLESSING, MAGICJUICE;
        }
    }

    public enum EquipmentType{
        HEAD,BODY,LEGS,BOOTS,TOOL
    }

    public enum Buff{
        NONE(0), TIMBERMAN(2),HOGWEED_RESISTANT(0),MAGICJUICE(10), BLESSING(2);

        public final int strength;
        Buff(int i) {
            strength=i;
        }
    }

    public enum MenuOption{
        NEWGAME,LOAD,EXIT
    }
    public enum ButtonName{
        UPRIGHT, DOWNLEFT,LEFT,RIGHT,UPLEFT,DOWNRIGHT,WAIT, CRAFT, EXIT
    }
}
