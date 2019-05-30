package bin.ui;


import bin.system.Commander;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Enums.ButtonName;
import lib.Pair;

import java.util.ArrayList;

import static java.lang.Math.*;
import static javafx.scene.paint.Color.*;
import static lib.Enums.ButtonName.CRAFT;

//import lib.CommandRefusedException;
//import javafx.scene.Group;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.StackPane;
//import static java.lang.Math.sin;
//import lib.Enums.Commands.Move.*;
//import lib.Enums;
public class UI extends Application {
    private Stage stage;

    void create() {
        start(stage = new Stage());
    }
    private final int btnSize= 80, r=16,btnX=900,btnY=450;
    private final double sizeX=60,sizeY=60,sizeHex=27,legendX=1000,legendY=50;
    private final Pair<Integer,Integer> windowSize = new Pair<>(1280,720);
    Enums.Species.AllSpecies nameLegend[]=Enums.Species.AllSpecies.values();
    @Override
    public void start(Stage primaryStage) {

        
        primaryStage.setTitle("Human&Nature");
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        Pane root = new Pane();
        
        Scene scene = new Scene(root, windowSize.getX(),windowSize.getY());
        scene.setRoot(root);
        
        /*Label myLabel = new Label("Wilk");
        root.getChildren().add(myLabel);
        myLabel.relocate(1000,50);*/
        
        mapUpdate(r,root);
        for(ButtonName name : ButtonName.values()){
            if(name!=CRAFT) root.getChildren().add(makeButton(btnSize,btnX,btnY,name,root,primaryStage));
        }
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Label wordLegend(double legendX,double legendY, Enums.Species.AllSpecies nameLegend )
    {
        Label myLabel= new Label(API.dataLoaderAPI.getSpecimenName(nameLegend));
        myLabel.relocate(legendX,legendY);
        return myLabel;
    }
    private Polygon hexLegend(double sizeHex, double legendX, double legendY, Enums.Species.AllSpecies nameLegend)
    {
        Polygon hexLeg=new Polygon();
        switch(nameLegend)
        {
            case HUMAN:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(RED);
                break;
            case WOLF:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(BLACK);
                break;
            case SHEEP:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(BEIGE);
                break;
            case CYBERSHEEP:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(BLUE);
                break;
            case OAK:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(MAROON);
                break;
            case FLOWER:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(YELLOW);
                break;
            case HOGWEED:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(DARKGREEN);
                break;
            default:
                hexLeg=new Hexagon(legendX,legendY,sizeHex).setColor(GREY);
                break;
        }
        
        return hexLeg;
    }
    private Polygon makeHex(int i,int j,double size,double sizeX,double sizeY)
    {
        Polygon hex = null;
        if (API.worldAPI.getCenterChunk().get(j,15-i)!=null){
                    switch(API.worldAPI.getCenterChunk().get(j,15-i).getSpecies()){
                        case WOLF:
                            hex = getHex(i, j, size, sizeX, sizeY, BLACK);
                            break;
                        case SHEEP:
                            hex = getHex(i, j, size, sizeX, sizeY, BEIGE);
                            break;
                        case HUMAN:
                            hex = getHex(i, j, size, sizeX, sizeY, RED);
                            break;
                        case CYBERSHEEP:
                            hex = getHex(i, j, size, sizeX, sizeY, BLUE);
                            break;
                        case OAK :
                            hex = getHex(i, j, size, sizeX, sizeY, MAROON);
                            break;
                        case FLOWER:
                            hex = getHex(i, j, size, sizeX, sizeY, YELLOW);
                            break;
                        case HOGWEED:
                            hex = getHex(i, j, size, sizeX, sizeY, DARKKHAKI);
                            break;
                }
        }else{
            hex = getHex(i, j, size, sizeX, sizeY, GREY);
        }
        return hex;    
    }

    private Polygon getHex(int i, int j, double size, double sizeX, double sizeY, Color color) {
        Polygon hex;
        if(i%2==0){
            hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2,sizeHex).setColor(color);
        }else{
            hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2,sizeHex).setColor(color);
        }
        return hex;
    }

    private void mapUpdate(int r,Pane root)
    {
        root.getChildren().clear();
        for(int i=0;i<nameLegend.length;i++)
        {
            root.getChildren().add(hexLegend(10,legendX-10,legendY+10+i*legendY/2,nameLegend[i]));
            root.getChildren().add(wordLegend(legendX,legendY+i*legendY/2,nameLegend[i]));
        }
        for(ButtonName name : ButtonName.values()){
            if(name != CRAFT) root.getChildren().add(makeButton(btnSize,btnX,btnY,name,root,stage));
        }
        Pair<ComboBox,Button> craftingBox = makeCraftingBox(root);
        root.getChildren().addAll(craftingBox.getX(), craftingBox.getY());

         for(int i=0;i<r;i++){
            for(int j=0;j<r;j++){
                root.getChildren().add(makeHex(i,j,sizeHex,sizeX,sizeY));
            }  
        }
    }

    private Pair<ComboBox, Button> makeCraftingBox(Pane root)
    {
        ArrayList<Enums.Commands.Craft> optionList = new ArrayList<>();
        for(Enums.Commands.Craft option : Enums.Commands.Craft.values())
        {
            if(API.worldSPI.getHuman().canCraft(option))
                optionList.add(option);
        }

        ObservableList<Enums.Commands.Craft> options =
                FXCollections.observableArrayList(optionList);

        ComboBox comboBox = new ComboBox(options);
        comboBox.relocate(btnX+Math.E*btnSize, btnY);
        comboBox.setMinWidth(btnSize*2);
        comboBox.setPromptText("Select item");

        Button button = new Button("Craft");
        button.setMinWidth (btnSize*2);
        button.setMinHeight (btnSize);
        button.setText(CRAFT.toString());
        button.setOnAction(event -> {
            Commander com = new Commander();
            if(comboBox.getValue()!=null) {
                com.giveCommand(Enums.Commands.Craft.valueOf(comboBox.getValue().toString()));
                doAction(com);
                mapUpdate(r, root);
            }
        });
        button.relocate(btnX+E*btnSize, btnY+btnSize/2);

        return new Pair<>(comboBox,button);
    }


    private Button makeButton(int btnSize,int btnX,int btnY, ButtonName btnName,Pane root,Stage stage)
    {
        Button btn = new Button();
        btn.setMinWidth (btnSize);        
        btn.setMinHeight (btnSize);
        btn.setText(btnName.toString());
        //Przydzielenie funkcji przyciskom
        btn.setOnAction(event -> {
            Commander com = new Commander();
            com.giveCommand(Enums.Commands.valueOf(btnName.toString()));
            doAction(com);
            mapUpdate(r,root);
        });
        switch(btnName)
        {
            case UPLEFT:
                btn.relocate(btnX,btnY); 
                break;
            case UPRIGHT:
                 btn.relocate(btnX+btnSize,btnY);
                 break;
            case LEFT:
                btn.relocate(btnX-btnSize/2,btnY+btnSize);
                break;
            case RIGHT:
                 btn.relocate(btnX+3*btnSize/2,btnY+btnSize);
                 break;
            case DOWNLEFT:
                btn.relocate(btnX,btnY+2*btnSize);
                break;
            case DOWNRIGHT:
                btn.relocate(btnX+btnSize,btnY+2*btnSize);
                break;
            case WAIT:
                btn.relocate(btnX+btnSize/2,btnY+btnSize);
            case EXIT:
                btn.relocate(btnX+E*btnSize, btnY+btnSize*PI/2);
                btn.setMinWidth(2*btnSize);
                btn.setOnAction(event -> {
                    API.systemAPI.getWorld().save();
                    stage.close();
                    API.systemAPI.getMenu().show();
                });
        }
        return btn;
    }

    private void doAction(Commander com)
    {
        try {
            API.systemAPI.getWorld().makeTurn(com);
        } catch (CommandRefusedException ex) {
            Alert alert = new Alert(AlertType.WARNING, ex.getMessage() , ButtonType.OK);
            alert.showAndWait();
        }
        System.out.println(API.worldAPI.getPopulation());
    }
    
}
