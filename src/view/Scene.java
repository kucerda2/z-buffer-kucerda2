package view;

import rasterize.FilledLineRasterizer;
import rasterize.ImageBuffer;
import renderer.Renderer3D;
import solids.Cube;
import solids.Solid;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Solid> solids = new ArrayList<>();
    private Mat4 projection, view;
    private Point3D light;
    private int color;
    private Renderer3D renderer3D;
    private ImageBuffer imageBuffer;
    private static int height = 600;
    private static int width = 800;
    private int indexSolid;
    private Camera cam;
    private Vec2D originPosition;
    JPanel panel;

    public Scene(int width, int height){
        imageBuffer = new ImageBuffer(width, height);
        renderer3D = new Renderer3D(new FilledLineRasterizer(imageBuffer));

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        initScene();

        initListeners();
    }

    public void initScene(){
        solids.add(new Cube(Color.YELLOW));

        cam = new Camera(
                new Vec3D(10, -30, 12),
                Math.toRadians(90),
                Math.toRadians(0),
                1,
                true
        );

        projection = new Mat4PerspRH(Math.toRadians(60), height / (double) width, 0.1, 1000);
    }

    public void drawScene(){
        imageBuffer.clear();

        renderer3D.setView(cam.getViewMatrix());
        renderer3D.setProjection(projection);
        //renderer3D.setModel(model);
        //renderer3D.draw();

        panel.repaint();
    }

    public void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                originPosition = new Vec2D(e.getX(), e.getY());
                drawScene();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double dx, dy;
                dx = (e.getX() - originPosition.getX()) / 100.0;
                dy = (e.getY() - originPosition.getY()) / 100.0;

                double azimuth = cam.getAzimuth();
                double zenith = cam.getZenith();

                zenith -= 180 * dy / height;
                if (zenith > 90)
                    zenith = 90;
                if (zenith < -90)
                    zenith = -90;

                azimuth += 180 * dx / width;
                azimuth = azimuth % 360;

                cam = cam.withAzimuth(azimuth).withZenith(zenith);
                drawScene();
                originPosition = new Vec2D(e.getX(), e.getY());
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam = cam.left(5);
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam = cam.forward(5);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam = cam.backward(5);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam = cam.right(5);
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    indexSolid = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    indexSolid = 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    indexSolid = 2;
                }

                if (e.getKeyCode() == KeyEvent.VK_X) {
                    rotateX(solids.get(indexSolid));
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    rotateZ(solids.get(indexSolid));
                }
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    rotateY(solids.get(indexSolid));
                }

                if (e.getKeyCode() == KeyEvent.VK_B) {
                    scale(solids.get(indexSolid), 1.2);
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    scale(solids.get(indexSolid), 0.8);
                }
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    translate(solids.get(indexSolid), 5, 0, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_G) {
                    translate(solids.get(indexSolid), -5, 0, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_H) {
                    translate(solids.get(indexSolid), 0, 5, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    translate(solids.get(indexSolid), 0, -5, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    translate(solids.get(indexSolid), 0, 0, 5);
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    translate(solids.get(indexSolid), 0, 0, -5);
                }
                if (e.getKeyCode() == KeyEvent.VK_O) {
                    projection = new Mat4OrthoRH(width, height, 0.1, 1000.0);
                }
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    projection = new Mat4PerspRH(Math.toRadians(60), height / (double) width, 0.1, 1000);
                }
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    rotateX(solids.get(0));
                    rotateX(solids.get(1));
                    rotateX(solids.get(2));
                }
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    rotateY(solids.get(0));
                    rotateY(solids.get(1));
                    rotateY(solids.get(2));
                }
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    rotateZ(solids.get(0));
                    rotateZ(solids.get(1));
                    rotateZ(solids.get(2));
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    scale(solids.get(0), 1.2);
                    scale(solids.get(1), 1.2);
                    scale(solids.get(2), 1.2);
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    scale(solids.get(0), 0.8);
                    scale(solids.get(1), 0.8);
                    scale(solids.get(2), 0.8);
                }

                drawScene();
            }
        });
    }
    public void rotateY(Solid solid) {
        Mat4RotY rotateY = new Mat4RotY(Math.toRadians(10));
        transform(solid, rotateY);
    }

    public void rotateX(Solid solid) {
        Mat4RotX rotateX = new Mat4RotX(Math.toRadians(10));
        transform(solid, rotateX);
    }

    public void rotateZ(Solid solid) {
        Mat4RotZ rotateZ = new Mat4RotZ(Math.toRadians(10));
        transform(solid, rotateZ);
    }

    public void scale(Solid solid, double value) {
        Mat4Scale scale = new Mat4Scale(value);
        transform(solid, scale);
    }

    public void translate(Solid solid, int x, int y, int z) {
        Mat4Transl trans = new Mat4Transl(x, y, z);
        transform(solid, trans);
    }


    public void transform(Solid solid, Mat4 mat) {
        for (int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            Point3D a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(i));
            Point3D b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(i + 1));


            Point3D aRotatedY = a.mul(mat);
            Point3D bRotatedY = b.mul(mat);


            solid.getVertexBuffer().remove((int) solid.getIndexBuffer().get(i));
            solid.getVertexBuffer().add(solid.getIndexBuffer().get(i), aRotatedY);
            solid.getVertexBuffer().remove((int) solid.getIndexBuffer().get(i + 1));
            solid.getVertexBuffer().add(solid.getIndexBuffer().get(i + 1), bRotatedY);

        }
    }

    public void clear() {
        imageBuffer.clear();
    }

    public void present(Graphics graphics) {
        imageBuffer.repaint(graphics);
    }

    public void start() {
        clear();
        panel.repaint();
    }

}
