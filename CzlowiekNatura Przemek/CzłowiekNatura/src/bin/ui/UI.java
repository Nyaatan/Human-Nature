package bin.ui;


import bin.system.Commander;
//import lib.CommandRefusedException;
import javafx.event.*;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
import static javafx.scene.paint.Color.*;
import javafx.stage.Stage;
import static java.lang.Math.cos;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Polygon;
import javafx.stage.StageStyle;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Enums.ButtonName;
import static lib.Enums.Commands.Move.*;
//import static java.lang.Math.sin;
//import lib.Enums.Commands.Move.*;
//import lib.Enums;
public class UI extends Application {
   
    public void create() {
        launch();
    }
    private final int btnSize= 80, r=16,btnX=900,btnY=450;
    private final double sizeX=60,sizeY=60;
    @Override
    public void start(Stage primaryStage) {

        
        primaryStage.setTitle("Człowiek i Natura");
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Pane root = new Pane();          //Korzeń
        
        Scene scene = new Scene(root, 1280,720);
        scene.setRoot(root);
        
        
        mapUpdate(r,root);
        for(ButtonName name : ButtonName.values()){
            root.getChildren().add(makeButton(btnSize,btnX,btnY,name,root));
        }
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Polygon makeHex(int i,int j,double size,double sizeX,double sizeY)
    {
        Polygon hex = null;
        if (API.worldAPI.getCenterChunk().get(j,15-i)!=null){
                    switch(API.worldAPI.getCenterChunk().get(j,15-i).getSpecies()){
                        case WOLF:
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(BLACK);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(BLACK);
                            }break;
                        case SHEEP:
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(BEIGE);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(BEIGE);
                            }break;
                        case HUMAN:
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(RED);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(RED);
                            }break;
                        case CYBERSHEEP:
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(BLUE);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(BLUE);
                            }break;
                        case OAK :
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(MAROON);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(MAROON);
                            }break;
                        case FLOWER:
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(YELLOW);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(YELLOW);
                            }break;
                        case HOGWEED:
                            if(i%2==0){
                                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(DARKGREEN);
                            }else{
                                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(DARKGREEN);
                            }break;
                }
        }else{
            if(i%2==0){
                hex=new Hexagon(sizeX+size*j*2*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(GREY);
            }else{
                hex=new Hexagon(sizeX+size*(j*2+1)*cos(Math.toRadians(30)),sizeY+3*i*size/2).setColor(GREY);
            }
        }
        return hex;    
    }
    
    private void mapUpdate(int r,Pane root)
    {
        root.getChildren().clear();
        for(ButtonName name : ButtonName.values()){
            root.getChildren().add(makeButton(btnSize,btnX,btnY,name,root));
        }
        
         for(int i=0;i<r;i++){
            for(int j=0;j<r;j++){
                root.getChildren().add(makeHex(i,j,Hexagon.size,sizeX,sizeY));
            }  
        }
    }
    private Button makeButton(int btnSize,int btnX,int btnY, ButtonName btnName,Pane root )
    {
        Button btn = new Button();
        btn.setMinWidth (btnSize);        
        btn.setMinHeight (btnSize);
        btn.setText(btnName.toString()); 
        btn.setOnAction(new EventHandler<ActionEvent>() {      //Przydzielenie funkcji przyciskom
            @Override
            public void handle(ActionEvent event) {
                //root.getChildren().clear();
                System.out.println(btnName);
                Commander com = new Commander();
                com.giveCommand(Enums.Commands.valueOf(btnName.toString()));
                doAction(com);
                mapUpdate(r,root);
                 
            }
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
