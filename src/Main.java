import java.util.*;

public class Main {

    public static int n;
    public static char[][] grid;
    public static int k;
    public static int[] len;
    public static ArrayList<Integer> boardBombedRecord;

    final public static int[] X = {0,1};
    final public static int[] Y = {1,0};


    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        k = scan.nextInt();


        grid = new char[n][];
        for (int i=0; i<n; i++)
            grid[i] = scan.next().toCharArray();


        boardBombedRecord = new ArrayList<Integer>();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 'O') {
                    boardBombedRecord.add(n*i+j);
                    grid[i][j] = '.';
                }
            }
        }


        len = new int[k];
        for (int i = 0; i< k; i++)
            len[i] = scan.nextInt();


        System.out.println(action(0));
    }

    public static long action(int numberOfShips) {


        if (numberOfShips == k) return reactionBoard();

        long res = 0;


        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {


                int lim = len[numberOfShips] > 1 ? 2 : 1;
                for (int dir=0; dir<lim; dir++) {


                    if (placementPiece(i, j, dir, numberOfShips)) {
                        shipPlacement(i, j, dir, numberOfShips);
                        res += action(numberOfShips+1);
                        shipMissed(i, j, dir, numberOfShips);
                    }
                }
            }
        }

        return res;
    }


    public static int reactionBoard() {
        for (Integer x: boardBombedRecord) {
            int r = x/n;
            int c = x%n;
            if (grid[r][c] == '.')
                return 0;
        }
        return 1;
    }


    public static void shipPlacement(int x, int y, int dir, int piece) {
        for (int z=0; z<len[piece]; z++)
            grid[x+ X[dir]*z][y+ Y[dir]*z] = (char)('0'+piece);
    }



    public static void shipMissed(int x, int y, int dir, int piece) {
        for (int z=0; z<len[piece]; z++)
            grid[x+ X[dir]*z][y+ Y[dir]*z] = '.';
    }


    public static boolean placementPiece(int x, int y, int dir, int piece) {


        for (int z=0; z<len[piece]; z++) {


            int nx = x+ X[dir]*z;
            int ny = y+ Y[dir]*z;


            if (!placementBound(nx, ny)) return false;


            if (grid[nx][ny] != '.') return false;
        }


        return true;
    }

    public static boolean placementBound(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
}