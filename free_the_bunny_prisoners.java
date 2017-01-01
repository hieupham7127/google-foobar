package com.google.challenges;
import java.util.ArrayList;
import java.util.Collections;

public class Answer {   
    static ArrayList<Integer> stack;
    static ArrayList<ArrayList<Integer>> arr;
    // compute combination (n, k)
    public static int C(int n, int k) {
      if (k == 0) return 1;
      if (k < 0) return 0;
      int res = 1;
      for (int i = k + 1; i <= n; i++)
          res = res * i / (i - k);
      return res;
    }

    // generate all lexicographical order of C(n, r - 1), count is reversed
    public static void genC(int n, int k, int last, int count) {
        if (k == 0) {
            for (int i = 0; i < stack.size(); i++) 
                arr.get(stack.get(i) - 1).add(count);
            return;
        }
        for (int i = last + 1; i <= n; i++) {
            stack.add(i);
            genC(n, k - 1, i, count);
            count -= C(n - i, k - 1);
            stack.remove(stack.size() - 1);
        }
    }
    
    public static int[][] answer(int num_buns, int num_required) { 
        // Your code goes here.
        /*
          # of keys = C(n, r - 1)
          # of keys a rabit can hold = C(n - 1, r - 2)
          increasing lexicographical order of (r - 1) rabits matched with decreasing key #
        */
      
        int num_keys = C(num_buns, num_required - 1);
        int num_hold = num_keys - C(num_buns - 1, num_required - 2);
        //System.out.println("Keys: " + num_keys);
        //System.out.println("Hold: " + num_hold);
    
        stack = new ArrayList<Integer>();
        arr = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < num_buns; i++)
            arr.add(new ArrayList<Integer>());
        
        genC(num_buns, num_required - 1, 0, num_keys - 1);
      
        /*for (int i = 0; i < num_buns; i++) {
            System.out.print("[" + i + "]: ");
            for (int j = 0; j < arr.get(i).size(); j++) {
                System.out.print(arr.get(i).get(j) + " ");
            }
            System.out.println();
        }*/
        
        // reverse order of rabits in the list for each key # (low to high)
        for (int i = 0; i < num_buns; i++)
            Collections.reverse(arr.get(i));
        
        // convert to int[][], count key # that does not appear in list
        int [][] res = new int[num_buns][num_hold];
        int temp, hold;
        for (int i = 0; i < num_buns; i++) {
            temp = 0;
            hold = 0;
            for (int j = 0; j < num_keys; j++) {
                if (temp < arr.get(i).size() && j == arr.get(i).get(temp)) temp++;
                    else res[i][hold++] = j;
            }
        }
        return res;
    } 
}