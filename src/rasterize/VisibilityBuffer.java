package rasterize;


import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VisibilityBuffer {

    private ImageBuffer img;
    private DepthBuffer zBuffer;
    private int width, height;
    private Col backgroundColor;

    public VisibilityBuffer(BufferedImage bufferedImage) {
    }

    public void clear(){
        img.clear();
        zBuffer.clear();
    }

    public void drawPixelZtest(int x, int y, double dbl, Col col){

    }

    public void drawPixel(int x, int y, Col col){
        img = new ImageBuffer(width,height);
        img.setClearValue(new Col(Color.GREEN.getRGB()));
    }

    public String getPixel(int x, int y){
        return "";
    }

}
