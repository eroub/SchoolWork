import java.io.*;
import java.util.*;

public class Solution {

    private static class Cell{
        int column;
        int row;
        //boolean visited;

        public Cell(){
            this.row = Integer.MAX_VALUE;
            this.column = Integer.MAX_VALUE;
        }

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public void set(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        /* ----------------------------------
            NOTE:
            Implementation largely based off of this video:
            https://www.youtube.com/watch?v=KiCBXu4P-2Y
            by William Fiset
            --------------------------------- */

        int n = in.nextInt();
        in.nextLine();

        int move_count = 0;
        int left_in_layer = 1;
        int left_in_next_layer = 0;
        boolean found_king = false;
        int[] dir_row = {-1, +1, 0, 0};
        int[] dir_column = {0, 0, +1, -1};

        boolean visited[][] = new boolean[n][n];

        Cell soldier = new Cell();

        char[][] table = new char[n][n];
        for(int i=0; i<n; i++){
            String line = in.nextLine();
            for(int j=0; j<n; j++){
                char val = line.charAt(j);
                table[i][j] = val;
                visited[i][j] = false;
                if(val == 'S') soldier.set(i, j);
            }
        }

        Queue<Cell> q = new ArrayDeque<>();
        q.add(soldier);

        visited[soldier.row][soldier.column] = true;

        while(!q.isEmpty()){
            Cell current = q.poll();
            if(table[current.row][current.column] == 'K'){
                found_king = true;
                break;
            }

            for(int i=0; i<4; i++){
                int row = current.row + dir_row[i];
                int column = current.column + dir_column[i];

                // Skip (BOUNDS)
                if((row < 0 || row >= n) || (column < 0 || column >= n)) continue;
                //Skip (VISITED OR "x")
                if(visited[row][column] || table[row][column] == 'x') continue;

                Cell adj = new Cell(row, column);

                q.add(adj);
                visited[row][column] = true;
                left_in_next_layer++;
            }
            left_in_layer--;

            if(left_in_layer == 0){
                left_in_layer = left_in_next_layer;
                left_in_next_layer = 0;
                move_count++;
            }
        }

        if(found_king){
            System.out.println(move_count);
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }
}
