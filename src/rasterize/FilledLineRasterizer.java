package rasterize;

import java.awt.*;
import java.util.Optional;

public class FilledLineRasterizer implements Raster<Color>{

    Raster raster;
    Color color;

    public FilledLineRasterizer(Raster raster) {
        this.raster = raster;
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        float dx, dy, steps, xinc, yinc, x, y;
        int i = 1;

        dx = x2 - x1;
        dy = y2 - y1;

        steps = Math.max(Math.abs(dx), Math.abs(dy));

        xinc = dx / steps;
        yinc = dy / steps;

        x = x1;
        y = y1;


        while (i <= steps) {
            x = x + xinc;
            y = y + yinc;

            raster.setElement(Math.round(x),Math.round(y),this.color.getRGB());
            i++;

        }
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color) {
        setColor(color);
        drawLine(x1,y1,x2,y2);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }
    @Override
    public Optional<Color> getElement(int x, int y) {
        return Optional.empty();
    }

    @Override
    public void setElement(int x, int y, Color color) {
        this.color = new Color(color.getRGB());
    }

    @Override
    public void clear() {

    }

    @Override
    public void setClearValue(Color value) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
