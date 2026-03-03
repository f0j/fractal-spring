package foj.fractal.engine;

public abstract class AbstractFractalWorker {

    abstract void work();

    /**
     * Converts pixels [x,y] on an image sized [width,height]
     * into complex values based on a fixed range, zoom factor
     * and origin at pixel [0,0].
     */
    public static DoubleComplex translateDouble(
            final int xpx,
            final int ypx,
            final double width,
            final double height,
            final double range,
            final double zoom,
            final double origRe,
            final double origIm) {

        final double factor = range * zoom;

        final double re = origRe == 0 ? origRe :
                origRe + (factor * (xpx / width));

        final double im = origIm == 0 ? origIm :
                origIm + (factor * (ypx / height));

        return new DoubleComplex(re, im);
    }
}
