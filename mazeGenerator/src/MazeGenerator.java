import java.util.Collections;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, 1, 0, -1};
    private final Random random = new Random();

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x * 2 + 1][this.y * 2 + 1];
        for(int[] row : maze)
            Arrays.fill(row, 1);
        generateMaze();
    }

    private void generateMaze() {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{1, 1});

        while(!stack.isEmpty()) {
            int[] pos = stack.peek();

            int x = pos[0];
            int y = pos[1];
            maze[x][y] = 0;

            Integer[] directions = {0, 1, 2, 3};
            Collections.shuffle(Arrays.asList(directions), random);

            boolean moved = false;
            for(int dir : directions) {
                int nx = x + dx[dir] * 2;
                int ny = y + dy[dir] * 2;
                if(nx > 0 && ny > 0 && nx < this.x * 2 && ny < this.y * 2 && maze[nx][ny] == 1) {
                    stack.push(new int[]{nx, ny});
                    maze[x + dx[dir]][y + dy[dir]] = 0;
                    moved = true;
                    break;
                }
            }
            if(!moved)
                stack.pop();
        }

        // 시작점을 뚫어줍니다.
        maze[0][1] = 0;

        // 출구를 랜덤하게 생성합니다.
        generateRandomExit();
    }

    private void generateRandomExit() {
        int side = random.nextInt(4);
        int exit;

        switch (side) {
            case 0: // Top
                exit = random.nextInt(y) * 2 + 1;
                maze[0][exit] = 0;
                break;
            case 1: // Right
                exit = random.nextInt(x) * 2 + 1;
                maze[exit][y*2] = 0;
                break;
            case 2: // Bottom
                exit = random.nextInt(y) * 2 + 1;
                maze[x*2][exit] = 0;
                break;
            case 3: // Left
                exit = random.nextInt(x) * 2 + 1;
                maze[exit][0] = 0;
                break;
        }
    }

    public void display() {
        for (int i = 0; i < x * 2 + 1; i++) {
            for (int j = 0; j < y * 2 + 1; j++) {
                System.out.print(maze[i][j] == 0 ? "  " : "##");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int x = 10;
        int y = 10;
        MazeGenerator maze = new MazeGenerator(x, y);
        maze.display();
    }
}
