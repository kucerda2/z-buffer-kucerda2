package renderer;

import model.Part;
import model.TopologyType;
import model.Vertex;
import rasterize.FilledLineRasterizer;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;
import java.util.List;

public class Renderer3D implements GPURenderer {

    private Mat4 model, view, projection;
    private FilledLineRasterizer filledLineRasterizer;

    public Renderer3D(FilledLineRasterizer filledLineRasterizer){
        this.filledLineRasterizer = filledLineRasterizer;
        this.model = new Mat4Identity();
    }

    @Override
    public void draw(List<Part> partBuffer, List<Integer> indexBuffer, List<Vertex> vertexBuffer) {
        for (Part part : partBuffer) {
            TopologyType topologyType = part.getTopologyType();
            int start = part.getStart();
            int count = part.getCount();
            if (topologyType == TopologyType.TRIANGLE) {
                for (int i = start; i < start + count * 3; i += 3) {
                    Integer index1 = indexBuffer.get(i);
                    Integer index2 = indexBuffer.get(i + 1);
                    Integer index3 = indexBuffer.get(i + 2);
                    Vertex vertex1 = vertexBuffer.get(index1);
                    Vertex vertex2 = vertexBuffer.get(index2);
                    Vertex vertex3 = vertexBuffer.get(index3);
                    drawTriangle(vertex1, vertex2, vertex3);
                }
            } else if (topologyType == TopologyType.LINE) {
                for (int i = start; i < start + count * 2; i += 2) {
                    Integer index1 = indexBuffer.get(i);
                    Integer index2 = indexBuffer.get(i + 1);
                    Vertex vertex1 = vertexBuffer.get(index1);
                    Vertex vertex2 = vertexBuffer.get(index2);
                    drawLine(vertex1, vertex2);
                }
            } else if (topologyType == TopologyType.POINT) {
                for (int i = start; i < start + count; i++) {
                    Integer index1 = indexBuffer.get(i);
                    Vertex vertex1 = vertexBuffer.get(index1);
                    drawPoint(vertex1);
                }
            }
        }
    }

    private void drawTriangle(Vertex v1, Vertex v2, Vertex v3) {
       drawLine(v1,v2);
       drawLine(v2,v3);
    }

    private void drawLine(Vertex v1, Vertex v2){
       Vec3D vec1 = drawPoint(v1);
       Vec3D vec2 = drawPoint(v2);

        filledLineRasterizer.rasterize((int) Math.round(vec1.getX()),(int) Math.round(vec1.getY()),(int) Math.round(vec2.getX()),(int) Math.round(vec2.getY()),new Color(0x00ff00));
    }

    private Vec3D drawPoint(Vertex v1){
        Point3D a = v1.getPoint();
        Point3D aTrans = a.mul(model).mul(view).mul(projection);

        Vec3D vec3D = null;
        if (clip(aTrans)) {


            if (aTrans.dehomog().isPresent()) {
                vec3D = aTrans.dehomog().get();

                vec3D = vec3D.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((800 - 1) / 2.0, (600 - 1) / 2.0, 1.));

            }
        }
        return vec3D;
    }

    public boolean clip(Point3D p) {
        double x = p.getX();
        double y = p.getY();
        double z = p.getZ();
        double w = p.getW();

        return x >= -w && x <= w && y >= -w && y <= w && z >= 0 && z <= w;
    }

    @Override
    public void clear() {

    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }
}
