import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Solution {

/*This class is taken from geeksforgeeks.org*/
    static class FastReader {

        BufferedReader br;
        StringTokenizer st;

     public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        FastReader in = new FastReader();
        int num_applicants = in.nextInt();
        int num_consecutive = in.nextInt();

        int arr[] = new int[num_applicants];
        for(int i=0; i < num_applicants; i++) arr[i] = in.nextInt();

        TreeSet<Integer> tree = new TreeSet<Integer>();
        boolean construct_tree = false;
        int pointer = 0;

        for(int i=0; i < num_applicants-num_consecutive; i++){
            if(!construct_tree){
                construct_tree = true;
                pointer = arr[0];
                for(int k=0; k < num_consecutive; k++) tree.add(arr[k]);
                int one = tree.last();
                int sum = tree.lower(one) + one;
                out.write(String.valueOf(sum));
                out.write(" ");
            }
            tree.remove(pointer);
            tree.add(arr[i+num_consecutive]);
            int one = tree.last();
            pointer = arr[i+1];
            int sum = tree.lower(one) + one;
            out.write(String.valueOf(sum));
            out.write(" ");
        }
        out.flush();
    }
}
