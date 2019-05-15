package bin.system;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Pair<X, Y> {
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

    public static Pair<Integer,Integer> makeIntPair(ArrayList<String> arrayList)
    {
        assert arrayList.size() > 1;
        return new Pair<>(Integer.parseInt(arrayList.get(0)), Integer.parseInt(arrayList.get(1)));
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public Pair<X,Y> copy() {return new Pair<>(this.x, this.y);}
}