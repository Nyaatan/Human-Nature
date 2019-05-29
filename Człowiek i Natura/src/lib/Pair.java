package lib;

import java.io.Serializable;

public class Pair<X, Y> implements Serializable {
    private X x;
    private Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public void setX(X x) {this.x = x;}

    public void setY(Y y) {this.y = y;}

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public String toString() {
        return "x: " + this.x.toString() + " y: " + this.y.toString();
    }

    public Pair<X,Y> copy() {return new Pair<>(this.x, this.y);}

    public boolean equals(Pair<Integer,Integer> pair)
    {
        return this.getX() == pair.getX() && this.getY() == pair.getY();
    }
}