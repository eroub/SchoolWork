import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class Solution {

    public static BigInteger calculate(BigInteger val, String[] arr){
        BigInteger result = new BigInteger("0");
        for(int i=0; i<arr.length; i++){
            switch (arr[i]) {
                case "x":
                    result = result.add(val);
                    break;
                case "x^2":
                    result = result.add(val.pow(2));
                    break;
                case "x^3":
                    result = result.add(val.pow(3));
                    break;
                case "x^4":
                    result = result.add(val.pow(3));
                    break;
                default:
                    BigInteger op_int = new BigInteger(arr[i]);
                    result = result.add(op_int);
                    break;
            }
        }
    return result;
    }
    // ITERATIVE BINARY SEARCH --------------------------------------
    public static BigInteger bin_search(BigInteger x, String[] ops){
        BigInteger low = BigInteger.ONE;
        BigInteger high = x.add(BigInteger.ONE);

        while(low.compareTo(high) < 0){
            BigInteger mid = (low.add(high)).divide(BigInteger.ONE.add(BigInteger.ONE));
            BigInteger calc = calculate(mid, ops);
            if(calc.equals(x)){
                return mid;
            } else if(x.compareTo(calc) > 0){
               low = mid.add(BigInteger.ONE);
            } else {
                high = mid;
            }
        }
        return BigInteger.ZERO.subtract(BigInteger.ONE);
    }
    // -------------------------------------------------------------
    // RECURSIVE BINARY SEARCH
    public static BigInteger binary_search(BigInteger low, BigInteger high, BigInteger x, String[] ops){
        BigInteger mid = (low.add(high)).divide(new BigInteger("2"));
        BigInteger calculated = calculate(mid, ops);

        if(calculated.equals(x)){
            return mid;
        } else if(x.compareTo(calculated) > 0){
            return binary_search(mid.add(BigInteger.ONE), high, x, ops);
        } else if(x.compareTo(calculated) < 0){
            return binary_search(low, mid, x, ops);
        } else {
            return BigInteger.ZERO.subtract(BigInteger.ONE);
        }
    }
    // -------------------------------------------------------------
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = new String();
        while(in.hasNext()) input += in.next();

        StringTokenizer str = new StringTokenizer(input, "=");
        String left = str.nextToken();
        BigInteger right = new BigInteger(str.nextToken());
        String[] operands = left.split("\\+");

        //System.out.println(binary_search(BigInteger.ONE, right, right, operands));
        System.out.println(bin_search(right, operands));
    }
}
