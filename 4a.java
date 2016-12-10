package com.google.challenges;
import java.util.ArrayList;
import java.util.Collections;

public class Answer {   
	// modified
    // compute combination (n, k)
    public static int C(int n, int k) {
      if (k == 0) return 1;
      if (k < 0) return 0;
      int res = 1;
	// modified
      return res;
    }

    // generate all lexicographical order of 	// modified
    public static void genC(int n, int k, int last, int count) {
        if (k == 0) {
		// modified
            return;
        }
        for (int i = last + 1; i <= n; i++) {
		// modified
        }
    }
    
    public static int[][] answer(int num_buns, int num_required) { 
        // Your code goes here.
        /*
          # of keys = 	// modified
          # of keys a rabit can hold = 	// modified
          increasing lexicographical order of 	// modified matched with	// modified
        */
      
        int num_keys = 	// modified
        int num_hold = 	// modified
        //System.out.println("Keys: " + num_keys);
        //System.out.println("Hold: " + num_hold);
    
        stack = new ArrayList<Integer>();
        arr = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < num_buns; i++)
            arr.add(new ArrayList<Integer>());
        
        genC(	// modified);
        
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