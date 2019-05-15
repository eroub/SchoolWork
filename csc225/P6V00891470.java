import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map.Entry;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int hash_size = Integer.parseInt(in.next());
        HashMap<String, Integer> hash = new HashMap<>(hash_size);

        String token = "";
        while(in.hasNext()){
            token = in.next();
            if(hash.containsKey(token)){
                hash.put(token, hash.get(token) + 1);
            } else {
                hash.put(token, 1);
            }
        }
        int max_val = 0;
        String max_key = "";
        for(Entry<String, Integer> set : hash.entrySet()){
            if(max_val <= set.getValue()){
                if(max_val == set.getValue()){
                    if(max_key.compareTo(set.getKey()) > 0) max_key = set.getKey();
                } else {
                    max_val = set.getValue();  
                    max_key = set.getKey();
                }
            } 
        }
        System.out.println(max_key + " " + max_val);
    }
}