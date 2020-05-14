package lab3;
import java.util.LinkedList;

public class Graphic {
    LinkedList<Point> points = new LinkedList<>();
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPoint(Point p){
        this.points.add(p);
    }

    @Override
    public String toString(){
        String ret="";
        ret+=(this.getName()+"\n");
        for (Point point: points){
            ret+=(point+"\n");
        }
        return ret;
    }
}