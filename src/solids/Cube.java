package solids;

import transforms.Point3D;

import java.awt.*;

public class Cube extends Solid{

    public Cube(Color color) {
        this.color = color;

        vertexBuffer.add(new Point3D(0, 0, 1));
        vertexBuffer.add(new Point3D(0, 20, 1));
        vertexBuffer.add(new Point3D(20, 20, 1));
        vertexBuffer.add(new Point3D(20, 0, 1));
        vertexBuffer.add(new Point3D(0,0,20));
        vertexBuffer.add(new Point3D(0, 20, 20));
        vertexBuffer.add(new Point3D(20, 20, 20));
        vertexBuffer.add(new Point3D(20, 0, 20));


        addIndices(0, 1, 1, 2, 2, 3, 3, 0, 0, 4, 4, 5, 5, 6, 6, 7, 7, 4, 1, 5, 2, 6, 3, 7);
    }
}
