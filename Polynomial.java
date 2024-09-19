public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0.0;
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        for(int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
    }

    public Polynomial add(Polynomial p) {
        double[] result;
        int max_length;
        if(this.coefficients.length >= p.coefficients.length) {
            max_length = this.coefficients.length;
        }
        else {
            max_length = p.coefficients.length;
        }
        result = new double[max_length];
        for(int i = 0; i < max_length; i++) {
            if (i < this.coefficients.length) {
                result[i] += this.coefficients[i];
            }
            if (i < p.coefficients.length) {
                result[i] += p.coefficients[i];
            }
        }
        Polynomial r = new Polynomial(result);
        return r;
    }

    public double evaluate(double x) {
        double result = 0.0;
        for(int i = 0; i < this.coefficients.length; i++) {
            result += Math.pow(x, i) * this.coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double r) {
        return evaluate(r) == 0.0;
    }
}