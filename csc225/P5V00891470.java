import java.io.*;
import java.util.*;

public class Solution {

    private static class Pair{
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static Pair[] inputGenerator(int n){ /* be careful to not modify this function */
        long last = 5000011;
        long mult1 = 5000087;
        long mult2 = 5000167;

        Pair[] arr = new Pair[n];
        for(int i = 0; i < n; i++){
            last = (last * mult1 + mult2)%n;
            int x = (int)last;
            last = (last * mult2 + i + mult1)%n;
            int y = (int)last;
            arr[i] = new Pair(x, y);
        }
        return arr;
    }

    static void usefulCountingSort(Pair[] A, int k, int type){
        ArrayList< ArrayList<Pair> > C = new ArrayList<>();

        for(int i = 0; i < k; i++)
            C.add(new ArrayList<>());

        if(type == 1){
            for(int i = 0; i < A.length; i++){
                int key = (A[i].second + i) % k;
                C.get(key).add(A[i]);
            }
        }

        if(type == 2){
            for(int i = 0; i < A.length; i++){
                int key = (A[i].first + 2*i) % k;
                C.get(key).add(A[i]);
            }
        }

        int index = 0;
        for(int i = 0; i < k; i++)
            for(int j = 0; j < C.get(i).size(); j++)
                A[index++] = C.get(i).get(j);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.next());
        int k = Integer.parseInt(in.next());

        Pair[] inputArray = inputGenerator(n);

        usefulCountingSort(inputArray, n, 1);
        usefulCountingSort(inputArray, n, 2);

        for(int i=0; i<n; i = i+k){
            System.out.println("(" + inputArray[i].first + "," + inputArray[i].second + ")");
        }
    }
}
