package foj.fractal.engine;

import static foj.fractal.engine.Statics.encodePixel;

@SuppressWarnings({
        "ExtractMethodRecommender",
        "UnnecessaryLocalVariable",
        "CommentedOutCode",
        "DuplicatedCode"
})

public class MandelbrotFractalWorker extends AbstractFractalWorker {

    private static final int MAX_ITERATIONS_INT = 500;
    private static final double MAX_ITERATIONS_DBL = MAX_ITERATIONS_INT;
    private static final double MAX_VALUE = 10.0;

    private final int[] xpxs;
    private final int[] ypxs;
    private final MandelbrotFractalContext params;
    private final WorkPublisher publisher;

    public MandelbrotFractalWorker(
            final int[] xpxs,
            final int[] ypxs,
            final MandelbrotFractalContext params,
            final WorkPublisher publisher) {

        this.xpxs = xpxs;
        this.ypxs = ypxs;
        this.params = params;
        this.publisher = publisher;
    }

    @Override
    public void work() {
        final int size = xpxs.length;
        final StringBuilder builder = new StringBuilder(size * params.maxEncodedPixelLength);
        for (int i = 0; i < size; i++) {
            final int xpx = xpxs[i];
            final int ypx = ypxs[i];
            final String result = mandelbrot(xpx, ypx);
            builder.append(result);
        }

        publisher.publish(builder.toString());
    }

    final String mandelbrot(
            final int xpx,
            final int ypx) {

        final DoubleComplex cur = translateDouble(
                xpx,
                ypx,
                params.width,
                params.height,
                params.range,
                params.zoom,
                params.origRe,
                params.origIm);

        double abs = 0.0;
        DoubleComplex z = new DoubleComplex();
        int it = 0;
        for (; it < MAX_ITERATIONS_INT && abs <= MAX_VALUE; it++) {
            // z^2 + <current>
            z = DoubleComplex.multiply(z, z);
            z = DoubleComplex.add(z, cur);
            abs = DoubleComplex.absolute(z);
        }

        final ColorScheme colorScheme = ColorScheme.LESS48RED_LESS16BLUE_64MIN_248MAX;
        final int red = colorScheme.red(it / MAX_ITERATIONS_DBL);
        final int green = colorScheme.green(it / MAX_ITERATIONS_DBL);
        final int blue = colorScheme.blue(it / MAX_ITERATIONS_DBL);
        final String encoded = encodePixel(xpx, ypx, red, blue, green);

//        {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("xpx=").append(xpx);
//            sb.append(", ypx=").append(ypx);
//            sb.append(", cur=").append(cur);
//            sb.append(", i=").append(i);
//            sb.append(", red=").append(red);
//            sb.append(", green=").append(green);
//            sb.append(", blue=").append(blue);
//            sb.append(", abs=").append(abs);
//            log.info(sb.toString());
//        }

        return encoded;
    }
}
