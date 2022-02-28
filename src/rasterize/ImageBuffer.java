package rasterize;

import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageBuffer implements Raster<Col> {

    private BufferedImage img;
    private Col clearColor;

    public ImageBuffer(int width, int height){
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }


    @Override
    public Optional<Col> getElement(int x, int y) {
        return Optional.empty();
    }

    @Override
    public void setElement(int x, int y, Col value) {

    }

    @Override
    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(new Color(clearColor.getRGB()));
        g.fillRect(0,0,img.getWidth(),img.getHeight());
    }

    @Override
    public void setClearValue(Col value) {
        this.clearColor = value;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void repaint(Graphics g) {
        g.drawImage(img,0,0,null);
    }
}
