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

    public static void main(String[] args) {
        FastReader in = new FastReader();
        int length = in.nextInt();
        int rln = in.nextInt();
        int[] arr = new int[length];
        for(int i=0; i<length; i++){
            arr[i] = in.nextInt();
        }
        Arrays.sort(arr);
        int stat = length;
        for(int i = 0; i<length-1; i++){
            for(int j=i+1; j<length; j++){
                if(Math.abs(arr[j]-arr[i]) <= rln){
                    stat += 2; 
                } else {
                    j+=length;
                }
            }   
        }
        System.out.println(stat);
    }
}