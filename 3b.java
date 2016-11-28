package com.google.challenges; 
import java.lang.Math.*;
import java.util.Arrays;

public class Answer {   
    public static int answer(int[] l) { 
        // Your code goes here
        int n = l.length;
        int[] left = new int[n];
        int[] right = new int[n];
	// modified
        /* 
        left: # of elements is divisable by l[j]
        right: # of elements l[i] is divisable by
        */
        for (int i = 0; i < l.length - 1; i++) 
            for (int j = i + 1; j < l.length; j++) 
            if (l[j] % l[i] == 0) {
		// modified
            }
            
        int res = 0;
        for (int i = 1; i < l.length - 1; i++)
            left[i] * right[i]; // modified
            
        return res;
    } 
}