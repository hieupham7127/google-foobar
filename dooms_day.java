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
            if (y == 0) return x;
            return gcd(y, x % y);
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
            long temp = gcd(Math.abs(x), Math.abs(y));
            return temp > 0 ? new Fraction(x /= temp, y /= temp) : new Fraction();
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
            for (int j = 0; j < i; j++)
                sum = sum.add(A[i][j].mul(x[j]));
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
            for (int j = i + 1; j < n; j++) 
                sum = sum.add(A[i][j].mul(x[j]));
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
                L[i][j] = A[i][j];
                for (int k = 0; k < j; k++)
                    L[i][j] = L[i][j].sub(L[i][k].mul(U[k][j]));
                L[i][j] = L[i][j].div(U[j][j]);
            }
            for (int j = i; j < n; j++) {
                U[i][j] = A[i][j];
                for (int k = 0; k < i; k++) 
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
            x = BackSubstitution(U, ForwardSubstitution(L, b));
            for (int i = 0; i < n; i++) Inverse[i][j] = x[i];
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
        Arrays.fill(sum, 0);
        Fraction[][] P = new Fraction[n][n];
        // fundamental matrix: N = (I - Q)^-1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) sum[i] += arr[i][j];
            for (int j = 0; j < n; j++) {
                P[i][j] = arr[i][j] > 0 ? new Fraction(-arr[i][j], sum[i]) : new Fraction();
                if (i == j) P[i][j] = (new Fraction(1, 1)).add(P[i][j]);
            }
        }
        
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
            temp = list.get(i).getY();
            denominator = denominator / Fraction.gcd(denominator, temp) * temp;
        }
        
        int[] res = new int[list.size() + 1];
        res[list.size()] = (int) denominator;
        for (int i = 0; i < list.size(); i++) {
            res[i] = (int) (denominator / list.get(i).getY() * list.get(i).getX());
            System.out.print(res[i] + " ");
        }
        System.out.println(res[list.size()]);
        return res;
    }    
}