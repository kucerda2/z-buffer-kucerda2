package rasterize;

import java.util.Optional;

public interface Raster<E> {

    default boolean isInsideBounds(int x,int y){
  /*      if(//TODO) {
            return true;
        } else {
            return false;
        }*/
        return true;
    }

    Optional<E> getElement(int x, int y);

    void setElement(int x, int y, E value);

    void clear();

    void setClearValue(E value);

    int getWidth();

    int getHeight();

    // boolean isInsideBounds(int x,int y);
}
