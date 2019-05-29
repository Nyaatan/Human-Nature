/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin.ui;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
//import static javafx.scene.paint.Color.*;

/**
 *
 * @author admin
 */
public class Hexagon {
    private Polygon shape;
    public static double size=40;
    public Hexagon(double centerX, double centerY)
    {
        
        this.shape = new Polygon();
        shape.getPoints().addAll(new Double[]{
            centerX, centerY+size,
            centerX+size*cos(Math.toRadians(30)), centerY+size*sin(Math.toRadians(30)),
            centerX+size*cos(Math.toRadians(30)), centerY-size*sin(Math.toRadians(30)),
            centerX, centerY-size,
            centerX-size*cos(Math.toRadians(30)), centerY-size*sin(Math.toRadians(30)),
            centerX-size*cos(Math.toRadians(30)), centerY+size*sin(Math.toRadians(30)),
        });
       // System.out.println(shape.getPoints());
    }
    
    public Polygon getShape() { return this.shape; }
    
    public Polygon setColor(Paint paint) {
        this.shape.setFill(paint);
        return this.shape;
    }
}
