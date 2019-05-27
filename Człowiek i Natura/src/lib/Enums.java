package lib;

public class Enums {
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
        WOOD,WOOL,MEAT,FUR,BLOOD,CYBERWOOL,QUANTUMCIRCUIT,CYBERHEART,FLOWER,HOGWEEDLEAF,AXE
    }

    public enum ItemType {
        EQUIPMENT,CRAFTING,BUFF,RESOURCE
    }

    public static class Commands{
        public enum Move{
            UPRIGHT, DOWNLEFT,LEFT,RIGHT,UPLEFT,DOWNRIGHT
        }

        public enum Use{}

        public enum Craft{}
    }

    public enum EquipmentType{
        HEAD,BODY,LEGS,BOOTS,TOOL
    }

    public enum Buff{
        NONE, TIMBERMAN,HOGWEED_RESISTANT
    }
}
