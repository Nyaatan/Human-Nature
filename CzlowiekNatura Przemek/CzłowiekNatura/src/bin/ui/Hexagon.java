/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin.ui;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Hexagon {
    private Polygon shape;
    static double size=27;
    Hexagon(double centerX, double centerY)
    {
        
        this.shape = new Polygon();
        shape.getPoints().addAll(centerX, centerY+size,
                centerX+size*cos(Math.toRadians(30)), centerY+size*sin(Math.toRadians(30)),
                centerX+size*cos(Math.toRadians(30)), centerY-size*sin(Math.toRadians(30)),
                centerX, centerY-size,
                centerX-size*cos(Math.toRadians(30)), centerY-size*sin(Math.toRadians(30)),
                centerX-size*cos(Math.toRadians(30)), centerY+size*sin(Math.toRadians(30)));
    }
    
    public Polygon getShape() { return this.shape; }
    
    Polygon setColor(Paint paint) {
        this.shape.setFill(paint);
        return this.shape;
    }
}
