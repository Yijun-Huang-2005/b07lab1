import java.io.*;
import java.util.*;

public class Driver {
    public static void main(String [] args) throws IOException {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,2,3,5};
        int[] e1 = {0, 2, 3, 6};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2, -9, 3};
        int[] e2 = {1, 3, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        Polynomial m = p1.multiply(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.println("m(0.1) = " + m.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        if(m.hasRoot(0))
            System.out.println("0 is a root of m");
        else
            System.out.println("0 is not a root of m");
        Polynomial from_file = new Polynomial(new File("test.txt"));
        for(int i = 0; i < from_file.coefficients.length; i++) {
            System.out.println(from_file.coefficients[i] + " " + from_file.exponents[i]);
        }
        from_file.saveToFile("output.txt");
    }
}