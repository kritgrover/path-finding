package maze;

public class Vertex {
    private final int x;
    private final int y;

    public Vertex(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}