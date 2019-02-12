import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {

    public static int median(int num, PriorityQueue<Integer> min, PriorityQueue<Integer> max){
        if(min.size()==0 || num <= min.peek()){
            min.add(num);
        } else {
            max.add(num);
        }

        if(Math.abs(max.size() - min.size()) == 2){
            if(max.size() > min.size()){
                min.add(max.poll());
            } else {
                max.add(min.poll());
            }
        }

        if(max.size() > min.size()) return max.peek();
        return min.peek();

    }

    public static void main(String[] args) {
        long result = 1;
        Scanner in = new Scanner(System.in);
        int len = in.nextInt();

        PriorityQueue<Integer> greater = new PriorityQueue<Integer>();
        PriorityQueue<Integer> lesser = new PriorityQueue<Integer>(Collections.reverseOrder());

        for(int i=0; i<len; i++) {
            int median = median(in.nextInt(), lesser, greater);
            result *= median; result %= 1000000007;
        }
        System.out.println(result);
    }
}
