package maze;

public class Edge {

    private final Vertex a;
    private final Vertex b;
    private int x;
    private int y;
    private final int value;
    private boolean isChecked;

    public Edge(Vertex a, Vertex b, int value) {
        this.a = a;
        this.b = b;
        this.value = value;

        findXAndY();
    }

    private void findXAndY() {
        int ax = a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();

        if (ax != bx) {
            this.y = ay;

            if (ax > bx) {
                this.x = bx + 1;
            } else {
                this.x = ax + 1;
            }
        } else {
            this.x = ax;

            if (ay > by) {
                this.y = by + 1;
            } else {
                this.y = ay + 1;
            }
        }
    }

    public void checked() {
        this.isChecked = true;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public Vertex getA() {
        return a;
    }

    public Vertex getB() {
        return b;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}