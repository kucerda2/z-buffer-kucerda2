package solids;

import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Solid {
    List<Point3D> vertexBuffer = new ArrayList<>();
    List<Integer> indexBuffer = new ArrayList<>();
    Color color;

    protected void addIndices(Integer... indices) {
        indexBuffer.addAll(Arrays.asList(indices));
    }

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
