package bin.world.gameObject;

import bin.enums.GameObjectType;
//TODO COMMENTS
public class GameObject {
    private String name;
    private String description;
    private GameObjectType type;

    public GameObject(String name, String description, GameObjectType type)
    {
        this.name = name; //TODO
        this.description = description;
        this.type = type;
    }

    public String getName() {return name;}
    public String getDescription() {return description;}
    public GameObjectType getType() {return type;}
}
