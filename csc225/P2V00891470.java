import java.io.*;
import java.util.*;

public class Solution {

    public static int A__B(int[] arr_a, int[] arr_b, int len) {
        //Odd case
        if(len%2 == 1){
            int sum = 0;
            for(int i=0; i<len; i++) sum += arr_a[i]*arr_b[i];
            return sum;
        } else {
        //Even case
            int[] a1 = new int[len/2];
            int[] a2 = new int[len/2];
            int[] b1 = new int[len/2];
            int[] b2 = new int[len/2];
            for(int i=0; i<len/2; i++){
                a1[i] = arr_a[i];
                a2[i] = arr_a[i+len/2];
                b1[i] = arr_b[i];
                b2[i] = arr_b[i+len/2];
            }
            return A__B(a1, a2, len/2) + A__B(b1, b2, len/2) + A__B(a1, b2, len/2) + A__B(a2, b1, len/2);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();

        int[] arr_a = new int[length];
        for(int i=0; i<length; i++) arr_a[i] = in.nextInt();
        int[] arr_b = new int[length];
        for(int i=0; i<length; i++) arr_b[i] = in.nextInt();

        System.out.println(A__B(arr_a, arr_b, length));
    }
}
