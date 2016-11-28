package com.google.challenges; 
import java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Answer {   
    
   public static class Fraction { 
        /* 
        Fraction class for accuracy
        Integer also works
        */
        private long x;
        private long y;
        
        public Fraction() {
            this.x = 0;
            this.y = 1;
        }
        
        public Fraction(long x, long y) {
            this.x = x;
            this.y = y;
        }
        
        public void setX(long x) { this.x = x; }
        public void setY(long y) { this.y = y; }
        public long getX() { return this.x; }
        public long getY() { return this.y; }
     
        public static long gcd(long x, long y) {
		// modified
        }
        
        public Fraction abs() {
            return new Fraction(Math.abs(this.x), Math.abs(this.y));
        }
        
        // this comparison is for Gaussian Elimination
        public boolean isGreater(Fraction f) {
            if (this.x * f.getY() > f.getX() * this.y) return true;
            return false;
        }        
     
        public Fraction add(Fraction f) { return add(f.getX(), f.getY()); }
        public Fraction add(long x, long y) {
            return simplify(this.x * y + this.y * x, this.y * y);
        }
     
        public Fraction sub(Fraction f) { return sub(f.getX(), f.getY()); }
        public Fraction sub(long x, long y) {
            return simplify(this.x * y - this.y * x, this.y * y);
        }
             
        public Fraction div(Fraction f) { return div(f.getX(), f.getY()); }
        public Fraction div(long x, long y) {
            return simplify(this.x * y, this.y * x);
        }
     
        public Fraction mul(Fraction f) { return mul(f.getX(), f.getY()); }
        public Fraction mul(long x, long y) {
            return simplify(this.x * x, this.y * y);
        }
                
        public Fraction simplify(long x, long y) {
		// modified
        }
     
        @Override
        public String toString() {
            return String.format("%d/%d", getX(), getY());
        }     
        // End of class Fraction
    }    
  
    // Solve Ly = b
    public static Fraction[] ForwardSubstitution(Fraction[][] A, Fraction[] b) {
        int n = b.length;
      
        Fraction[] x = new Fraction[n];
        Fraction sum;
        for (int i = 0; i < n; i++) {
            sum = new Fraction();
		// modified
            x[i] = (b[i].sub(sum)).div(A[i][i]);
        }
        return x;
    }
  
    // Solve Ux = y
    public static Fraction[] BackSubstitution(Fraction[][] A, Fraction[] b) {
        int n = b.length;
      
        Fraction[] x = new Fraction[n];
        Fraction sum;
        for (int i = n - 1; i >= 0; i--) {
            sum = new Fraction();
		// modified
            x[i] = (b[i].sub(sum)).div(A[i][i]);
        }
        return x;
    }
  
    // LU Decomposition
    public static void LUMatrix(Fraction[][] A, Fraction[][] L, Fraction[][] U) {
        int n = A.length;
        // initialization: L = I, U = 0
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                L[i][j] = new Fraction();
                U[i][j] = new Fraction();
            }      
        for (int i = 0; i < n; i++) L[i][i] = new Fraction(1, 1);      
        // compute L & U with Doolittle in O(n^3)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
		// modified
                L[i][j] = L[i][j].div(U[j][j]);
            }
            for (int j = i; j < n; j++) {
		// modified
                    U[i][j] = U[i][j].sub(L[i][k].mul(U[k][j]));
            }
        }
    }
  
    // Inverse Matrix A^-1
    public static Fraction[][] InverseMatrix(Fraction[][] A) {
        int n = A.length;
        Fraction[][] L = new Fraction[n][n];
        Fraction[][] U = new Fraction[n][n];
        LUMatrix(A, L, U);
        Fraction[][] Inverse = new Fraction[n][n];
        Fraction[] x, b;
        for (int j = 0; j < n; j++) {
            // create each column of Identity Matrix
            b = new Fraction[n];
            for (int i = 0; i < n; i++) b[i] = new Fraction();
            b[j] = new Fraction(1, 1);
		// modified
        }
        return Inverse;
    }
  
    // print for debugging
    public static void printMatrix(Fraction[][] P, String name) {
        int n = P.length;
        System.out.println(name + "----------------------------");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) System.out.print(P[i][j] + " ");
            System.out.println();
        }
    }
  
    public static int[] answer(int[][] arr) {
        // Your code goes here.
        int n = arr.length;
        long[] sum = new long[n];
	// modified
        Fraction[][] P = new Fraction[n][n];
        // modified
        
        // print to see magic!!!
        //printMatrix(P, "A");
        P = InverseMatrix(P);
        //printMatrix(P, "P");
      
        ArrayList<Fraction> list = new ArrayList<Fraction>();      
        Fraction f = new Fraction();
        for (int i = 0; i < n; i++)
            if (sum[i] == 0) {
                list.add(P[0][i]);
                f = f.add(P[0][i]);
            }      
        f = new Fraction(f.getY(), f.getX());
        
        /*
        A substitution approach is to find the maximum denominator since 
        all of the fractions are simplified. However, lcm works for general cases
        */
        long denominator = 1, temp;
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).mul(f));
		// modified
        }
        
        int[] res = new int[list.size() + 1];
        res[list.size()] = (int) denominator;
        for (int i = 0; i < list.size(); i++) {
		// modified
            System.out.print(res[i] + " ");
        }
        System.out.println(res[list.size()]);
        return res;
    }
  
    public static void main(String[] args) {
        /*int[][] arr = new int[][] {{0, 0, 0, 0, 0, 0, 0}, 
                                  {1, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0},
                                  {0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0}};*/
      
        /*int[][] arr = new int[][] {{0, 1, 0, 0, 0, 1}, 
                                  {4, 0, 0, 3, 2, 0}, 
                                  {0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0}};*/
        int[][] arr = new int[][] {{1, 1, 1},
                                  {0, 0, 0},
                                  {0, 1, 0}};
        Solution.answer(arr);
    }
    
}