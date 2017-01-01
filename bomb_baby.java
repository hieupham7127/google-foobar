package com.google.challenges; 
import java.math.BigInteger;

public class Answer {   
    // alternatives: AtomicBoolean, Pair class
    static private boolean isPossible;
    
    /* 
    one is divisible by another and bigger than 1 => impossible
    recursive solution is similar to implementation of gcd
    Formula: F(a, b) = (a / b) + F(b, a mod b)
    */
    public static BigInteger cycles(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.valueOf(0)) == 0) return a;
        else if (a.mod(b) == BigInteger.valueOf(0) && b.compareTo(BigInteger.valueOf(1)) > 0) isPossible = false;
        return a.divide(b).add(cycles(b, a.mod(b)));
    }
    
    public static String answer(String M, String F) { 
        // Your code goes here.
        BigInteger m = new BigInteger(M);
        BigInteger f = new BigInteger(F);
        isPossible = true;
        // 2 is subtracted because we start from (0, 0)
        BigInteger res = cycles(m, f).subtract(BigInteger.valueOf(2));
        if (!isPossible) return "impossible";
            else return res.toString();
    } 
}