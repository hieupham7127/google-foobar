package com.google.challenges; 
import java.lang.Math.*;
import java.util.Arrays;

public class Answer {   
    public static int answer(int[] l) { 
        // Your code goes here
        int n = l.length;
        int[] left = new int[n];
        int[] right = new int[n];
	Arrays.fill(left, 0);
	Arrays.fill(rightt, 0);
        /* 
        left: # of elements is divisable by l[j]
        right: # of elements l[i] is divisable by
        */
        for (int i = 0; i < l.length - 1; i++) 
            for (int j = i + 1; j < l.length; j++) 
            if (l[j] % l[i] == 0) {
		left[j]++;
		right[i]++;
            }
            
        int res = 0;
        for (int i = 1; i < l.length - 1; i++)
            res += left[i] * right[i];
            
        return res;
    } 
}