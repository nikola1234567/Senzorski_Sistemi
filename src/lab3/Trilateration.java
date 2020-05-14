package lab3;

public class Trilateration {
    public Point Trilateration(int id,
                               Node awd1,
                               Node awd2,
                               Node awd3) {
        //List<Double> newCoordinates = new ArrayList<>();
        double i1 = awd1.point.x, i2 = awd2.point.x,i3 = awd3.point.x;
        double j1 = awd1.point.y, j2 = awd2.point.y,j3 = awd3.point.y;
        double x, y;
        double d1 = awd1.nodeNeighbors.get(id).distance;
        double d2 = awd2.nodeNeighbors.get(id).distance;
        double d3 = awd3.nodeNeighbors.get(id).distance;
        x = (((((2 * j3) - (2 * j2)) * (((d1 * d1) - (d2 * d2)) + ((i2 * i2) - (i1 * i1)) + ((j2 * j2) - (j1 * j1)))) - (((2 * j2) - (2 * j1)) * (((d2 * d2) - (d3 * d3)) + ((i3 * i3) - (i2 * i2)) + ((j3 * j3) - (j2 * j2))))) / ((((2 * i2) - (2 * i3)) * ((2 * j2) - (2 * j1))) - (((2 * i1) - (2 * i2)) * ((2 * j3) - (2 * j2)))));
        y = (((d1 * d1) - (d2 * d2)) + ((i2 * i2) - (i1 * i1)) + ((j2 * j2) - (j1 * j1)) + (x * ((2 * i1) - (2 * i2)))) / ((2 * j2) - (2 * j1));

        return new Point((int)x,(int)y);
    }
}