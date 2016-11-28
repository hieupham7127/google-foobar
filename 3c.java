package com.google.challenges; 
import java.math.BigInteger;

public class Answer {   
    // alternatives: AtomicBoolean, Pair class
	// modified
    
    /* 
    one is divisible by another and bigger than 1 => impossible
    recursive solution is similar to implementation of gcd
    Formula: F(a, b) = (a / b) + F(b, a mod b)
    */
    public static BigInteger cycles(BigInteger a, BigInteger b) {
	// modified
    }
    
    public static String answer(String M, String F) { 
        // Your code goes here.
        BigInteger m = new BigInteger(M);
        BigInteger f = new BigInteger(F);
	// modified
        BigInteger res = cycles(m, f); // modified
        if (!isPossible) return "impossible";
            else return res.toString();
    } 
}