package main.java;

public class Position {
    private int width;
    private int height;

    public Position(int x, int y){
        this.width=x;
        this.height=y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Position)) {
            return false;
        }

        Position c = (Position) o;

        if(c.getWidth()==this.getWidth()&&c.getHeight()==this.getHeight()) return true;
        return false;
    }

}