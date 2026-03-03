package foj.fractal.engine;

@SuppressWarnings({
        "ClassCanBeRecord",
        "StringBufferReplaceableByString",
        "unused"
})

public class DoubleComplex {

    protected final double re;
    protected final double im;

    public DoubleComplex(
            final double re,
            final double im) {

        this.re = re;
        this.im = im;
    }

    public DoubleComplex() {
        this(0.0, 0.0);
    }

    public static DoubleComplex add(
            final DoubleComplex left,
            final DoubleComplex right) {

        return new DoubleComplex(
                left.re + right.re,
                left.im + right.im);
    }

    public static DoubleComplex subtract(
            final DoubleComplex left,
            final DoubleComplex right) {

        return new DoubleComplex(
                left.re - right.re,
                left.im - right.re);
    }

    public static DoubleComplex multiply(
            final DoubleComplex left,
            final DoubleComplex right) {

        final double term1 = left.re * right.re;
        final double term2 = left.re * right.im;
        final double term3 = left.im * right.re;
        final double term4 = left.im * right.im * -1.0;

        return new DoubleComplex(
                term1 + term4,
                term2 + term3);
    }

    public static DoubleComplex divide(
            final DoubleComplex left,
            final DoubleComplex right) {

        final DoubleComplex top = DoubleComplex.multiply(
                left,
                DoubleComplex.conjugate(right));

        final DoubleComplex bottom = DoubleComplex.multiply(
                right,
                DoubleComplex.conjugate(right));

        final double denom = bottom.re;

        return new DoubleComplex(
                top.re / denom,
                top.im / denom);
    }

    public static DoubleComplex exponent(final DoubleComplex complex) {
        final double r = Math.exp(complex.re);

        return new DoubleComplex(
                r * Math.cos(complex.im),
                r * Math.sin(complex.im));
    }

    public static DoubleComplex conjugate(final DoubleComplex complex) {
        return new DoubleComplex(
                complex.re,
                complex.im * -1.0);
    }

    public static double absolute(final DoubleComplex complex) {
        final double a = complex.re * complex.re;
        final double b = complex.im * complex.im;
        return Math.sqrt(a + b);
    }

    public String describe() {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(re).append(" + ").append(im).append('i');
        return builder.toString();
    }
}
