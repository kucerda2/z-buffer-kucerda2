package model;

import transforms.Col;
import transforms.Point3D;

public class Vertex {

    private final Point3D point;
    private final Col color;

    // private final Vec2D textureCoord; // souřadnice do textury pro mapování obrázku na plochu
    // private final Vec3D normal; // normála pro potřeby osvětlení

    public Vertex(Point3D point, Col color) {
        this.point = point;
        this.color = color;
    }

    public Vertex mul(double t) {
        return new Vertex(
                point.mul(t),
                color.mul(t)
        );
    }

    public Vertex add(Vertex otherVertex) {
        return new Vertex(
                point.add(otherVertex.getPoint()),
                color.add(otherVertex.getColor())
        );
    }

    public Point3D getPoint() {
        return point;
    }

    public Col getColor() {
        return color;
    }

    public double getX() {
        return getPoint().getX();
    }

    public double getY() {
        return getPoint().getY();
    }

    public double getZ(){
        return getPoint().getZ();
    }

    public double getW() {
        return getPoint().getW();
    }
}
