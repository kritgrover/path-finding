import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Maze {
    private final boolean[][] maze;
    private final Vertex[][] vertices;
    private final ArrayList<Edge> edges;
    private final ArrayList<Edge> minimumTree;

    public Maze(int height, int length) {
        int vertexHeight;
        int vertexLength;

        maze = new boolean[height][length];

        vertexHeight = (height - 1) / 2;
        vertexLength = (length - 1) / 2;

        vertices = new Vertex[vertexHeight][vertexLength];

        edges = new ArrayList<>();
        minimumTree = new ArrayList<>();
    }

    public void generate() {
        Random rng = new Random();
        int max;
        int maxPosition;
        int entry;
        int exit;

        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices[0].length; j++) {
                vertices[i][j] = new Vertex(i * 2 + 1, j * 2 + 1);
            }
        }

        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices[0].length; j++) {
                if (j + 1 < vertices[0].length) {
                    edges.add(new Edge(vertices[i][j], vertices[i][j + 1], rng.nextInt()));
                }

                if (i + 1 < vertices.length) {
                    edges.add(new Edge(vertices[i][j], vertices[i + 1][j], rng.nextInt()));
                }
            }
        }

        for (int i = 0; i < edges.size(); i++) {
            max = Integer.MIN_VALUE;
            maxPosition = 0;

            for (Edge edge : edges) {
                if (!edge.isChecked()) {
                    if (edge.getValue() >= max) {
                        max = edge.getValue();
                        maxPosition = edges.indexOf(edge);
                    }
                }
            }

            if (!isCycle(edges.get(maxPosition))) {
                minimumTree.add(edges.get(maxPosition));
            }

            edges.get(maxPosition).checked();
        }

        for (Edge edge : minimumTree) {
            Vertex a = edge.getA();
            Vertex b = edge.getB();

            maze[a.getY()][a.getX()] = true;
            maze[b.getY()][b.getX()] = true;
            maze[edge.getY()][edge.getX()] = true;
        }

        entry = rng.nextInt(vertices.length) * 2 + 1;
        exit = rng.nextInt(vertices.length) * 2 + 1;

        maze[entry][0] = true;
        maze[exit][maze[0].length - 1] = true;

        if (maze.length % 2 == 0) {
            maze[exit][maze[0].length - 2] = true;
        }
    }

    public void save(String file) {
        try (PrintWriter w = new PrintWriter(file)) {
            w.println(maze.length);

            for (boolean[] booleans : maze) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (booleans[j]) {
                        w.print(1);
                    } else {
                        w.print(0);
                    }
                }

                w.println();
            }
        } catch (IOException e) {
            System.out.println("IOError: Can't write to file");
        }
    }

    public static Maze load(String filePath) {
        Maze output = null;
        int size;
        boolean[][] maze = null;
        String line;
        File file = new File(filePath);

        try (Scanner s = new Scanner(file)) {
            size = s.nextInt();

            output = new Maze(size, size);
            maze = new boolean[size][size];

            for (int i = 0; i < size; i++) {
                line = s.next();

                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '1') {
                        maze[i][j] = true;
                    } else if (line.charAt(j) == '0') {
                        maze[i][j] = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: File not found");
        }

        if (output != null && maze != null) {
            output.setMaze(maze);
        }

        return output;
    }

    public void setMaze(boolean[][] maze) {
        if (this.maze.length == maze.length && this.maze[0].length == maze[0].length) {
            for (int i = 0; i < maze.length; i++) {
                System.arraycopy(maze[i], 0, this.maze[i], 0, maze[0].length);
            }
        } else {
            System.out.println("Error: File has invalid format");
        }
    }

    public void print() {
        for (boolean[] booleans : maze) {
            for (int j = 0; j < maze[0].length; j++) {
                if (booleans[j]) {
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }
            }

            System.out.println();
        }
    }

    public void escape() {
        boolean[][] escape = new boolean[maze.length][maze[0].length];
        boolean reachedEnd;
        int entry = 0;

        for (int i = 0; i < maze[0].length; i++) {
            if (maze[i][0]) {
                entry = i;
            }
        }

        reachedEnd = findEscape(escape, 0, 0, entry, 0);

        if (reachedEnd) {
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (maze[i][j]) {
                        if (escape[i][j]) {
                            System.out.print("//");
                        } else {
                            System.out.print("  ");
                        }
                    } else {
                        System.out.print("\u2588\u2588");
                    }
                }

                System.out.println();
            }
        } else {
            System.out.println("Error: Impossible maze");
        }
    }

    private boolean isCycle(Edge edge) {
        Vertex a = edge.getA();
        Vertex b = edge.getB();
        boolean output;

        output = findConnection(edge, a, b);

        return output;
    }

    private boolean findConnection(Edge origin, Vertex comingFrom, Vertex target) {
        boolean targetFound = false;
        Vertex nextVertex;

        for (Edge edge : minimumTree) {
            if (edge != origin && !targetFound) {
                if (edge.getA() == comingFrom || edge.getB() == comingFrom) {
                    if (edge.getA() == comingFrom) {
                        nextVertex = edge.getB();
                    } else {
                        nextVertex = edge.getA();
                    }

                    if (edge.getA() == target || edge.getB() == target) {
                        targetFound = true;
                    } else {
                        targetFound = findConnection(edge, nextVertex, target);
                    }
                }
            }
        }

        return targetFound;
    }

    private boolean findEscape(boolean[][] escape, int originY, int originX, int nextY, int nextX) {
        boolean reachedEnd = false;

        if (nextX == maze[0].length - 1) {
            escape[nextY][nextX] = true;
            return true;
        }

        for (int i = nextY - 1; i <= nextY + 1; i++) {
            for (int j = nextX - 1; j <= nextX + 1; j++) {
                if (i >= 0 && i < maze.length && j >= 0 && j < maze[0].length) {
                    if (i == nextY || j == nextX) {
                        if (!(i == originY && j == originX)) {
                            if (i != nextY || j != nextX) {
                                if (maze[i][j]) {
                                    reachedEnd = findEscape(escape, nextY, nextX, i, j);
                                }

                                if (reachedEnd) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (reachedEnd) {
                break;
            }
        }

        escape[nextY][nextX] = reachedEnd;
        return reachedEnd;
    }
}