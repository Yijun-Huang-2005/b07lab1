import java.io.*;
import java.util.*;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0.0;
        exponents = new int[1];
        exponents[0] = 0;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];
        for(int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
        for(int i = 0; i < exponents.length; i++) {
            this.exponents[i] = exponents[i];
        }
    }

    public boolean find_negative(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '-') {
                return true;
            }
        }
        return false;
    }

    public Polynomial(File f) throws IOException {
        Scanner s = new Scanner(f);
        String line = s.nextLine();
        String[] terms = line.split("\\+");
        int term_num = terms.length;
        for(int i = 0; i < terms.length; i++) {
            if(find_negative(terms[i])) {
                if(terms[i].charAt(0) == '-') {
                    String[] sub_terms = terms[i].split("-");
                    term_num = term_num + sub_terms.length - 2;
                }
                else {
                    String[] sub_terms = terms[i].split("-");
                    term_num = term_num + sub_terms.length - 1;
                }
            }
        }
        String[] all_terms = new String[term_num];
        int pointer = 0;
        for(int i = 0; i < terms.length; i++) {
            if(find_negative(terms[i])) {
                if(terms[i].charAt(0) == '-') {
                    String[] sub_terms = terms[i].split("-");
                    for(int j = 1; j < sub_terms.length; j++) {
                        all_terms[pointer] = "-" + sub_terms[j];
                        pointer++;
                    }
                }
                else {
                    String[] sub_terms = terms[i].split("-");
                    all_terms[pointer] = sub_terms[0];
                    pointer++;
                    for(int j = 1; j < sub_terms.length; j++) {
                        all_terms[pointer] = "-" + sub_terms[j];
                        pointer++;
                    }
                }
            }
            else {
                all_terms[pointer] = terms[i];
                pointer++;
            }
        }
        this.coefficients = new double[term_num];
        this.exponents = new int[term_num];
        for(int i = 0; i < term_num; i++) {
            String[] coe_exp = all_terms[i].split("x");
            if(coe_exp.length == 1) {
                this.coefficients[i] = Double.parseDouble(coe_exp[0]);
                this.exponents[i] = 0;
            }
            else {
                this.coefficients[i] = Double.parseDouble(coe_exp[0]);
                this.exponents[i] = Integer.parseInt(coe_exp[1]);
            }
        }
    }

    public double add_find(double[] coe, int[] exp, int target) {
        for(int i = 0; i < coe.length; i++) {
            if(exp[i] == target) {
                return coe[i];
            }
        }
        return 0;
    }

    public Polynomial add(Polynomial p) {
        int max_length = -1;
        for(int i = 0; i < this.exponents.length; i++) {
            if(this.exponents[i] > max_length) {
                max_length = this.exponents[i];
            }
        }
        for(int i = 0; i < p.exponents.length; i++) {
            if(p.exponents[i] > max_length) {
                max_length = p.exponents[i];
            }
        }
        max_length++;
        boolean[] temp_exp = new boolean[max_length];
        for(int i = 0; i < this.exponents.length; i++) {
            temp_exp[this.exponents[i]] = true;
        }
        for(int i = 0; i < p.exponents.length; i++) {
            temp_exp[p.exponents[i]] = true;
        }
        int count = 0;
        for(int i = 0; i < max_length; i++) {
            if(temp_exp[i]) {
                count++;
            }
        }
        int[] result_exp = new int[count];
        double[] result_coe = new double[count];
        int pointer = 0;
        for(int i = 0; i < max_length; i++) {
            if(temp_exp[i]) {
                result_exp[pointer] = i;
                pointer++;
            }
        }
        for(int i = 0; i < count; i++) {
            result_coe[i] = add_find(this.coefficients, this.exponents, result_exp[i]) + add_find(p.coefficients, p.exponents, result_exp[i]);
        }
        Polynomial result = new Polynomial(result_coe, result_exp);
        return result;
    }

    public double mult_find(double[] c1, int[] e1, double[] c2, int[] e2, int target) {
        double result = 0;
        for(int i = 0; i < c1.length; i++) {
            for(int j = 0; j < c2.length; j++) {
                if(e1[i] + e2[j] == target) {
                    result += c1[i] * c2[j];
                }
            }
        }
        return result;
    }

    public Polynomial multiply(Polynomial p) {
        int max_exp = -1;
        for(int i = 0; i < this.exponents.length; i++) {
            for(int j = 0; j < p.exponents.length; j++) {
                if(this.exponents[i] + p.exponents[j] > max_exp) {
                    max_exp = this.exponents[i] + p.exponents[j];
                }
            }
        }
        max_exp++;
        boolean[] temp_exp = new boolean[max_exp];
        for(int i = 0; i < this.exponents.length; i++) {
            for(int j = 0; j < p.exponents.length; j++) {
                temp_exp[this.exponents[i] + p.exponents[j]] = true;
            }
        }
        int count = 0;
        for(int i = 0; i < max_exp; i++) {
            if(temp_exp[i]) {
                count++;
            }
        }
        int[] result_exp = new int[count];
        double[] result_coe = new double[count];
        int pointer = 0;
        for(int i = 0; i < max_exp; i++) {
            if(temp_exp[i]) {
                result_exp[pointer] = i;
                pointer++;
            }
        }
        for(int i = 0; i < count; i++) {
            result_coe[i] = mult_find(this.coefficients, this.exponents, p.coefficients, p.exponents, result_exp[i]);
        }
        Polynomial result = new Polynomial(result_coe, result_exp);
        return result;
    }

    public double evaluate(double x) {
        double result = 0.0;
        for(int i = 0; i < this.coefficients.length; i++) {
            result += Math.pow(x, this.exponents[i]) * this.coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double r) {
        return evaluate(r) == 0.0;
    }

    public void saveToFile(String name) throws IOException {
        FileWriter output = new FileWriter(name, false);
        String result = "";
        for(int i = 0; i < this.coefficients.length; i++) {
            if(this.exponents[i] == 0) {
                if(this.coefficients[i] < 0) {
                    result = result + this.coefficients[i];
                }
                else {
                    if(i == 0) {
                        result = result + this.coefficients[i];
                    }
                    else {
                        result = result + "+" + this.coefficients[i];
                    }
                }
            }
            else {
                if(this.coefficients[i] < 0) {
                    result = result + this.coefficients[i] + "x" + this.exponents[i];
                }
                else {
                    if(i == 0) {
                        result = result + this.coefficients[i] + "x" + this.exponents[i];
                    }
                    else {
                        result = result + "+" + this.coefficients[i] + "x" + this.exponents[i];
                    }
                }
            }
        }
        output.append(result);
        output.close();
    }
}