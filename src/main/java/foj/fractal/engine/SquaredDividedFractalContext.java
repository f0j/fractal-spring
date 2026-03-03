package foj.fractal.engine;

import static foj.fractal.engine.Statics.FIELD_DELIM;

public class SquaredDividedFractalContext extends AbstractFractalContext {

    protected final DoubleComplex constant;

    public SquaredDividedFractalContext(
            final int width,
            final int height) {

        this.width = width;
        this.height = height;
        this.origRe = -2;
        this.origIm = -2;
        this.zoom = 1.0;
        this.range = 4.0;
        this.constant = new DoubleComplex(0.1, -0.129);

        final int widthSize = String.valueOf(width).length();
        final int heightSize = String.valueOf(height).length();
        final int delimSize = FIELD_DELIM.length();
        final int colorSize = String.valueOf(ColorScheme.MAX_COLOR).length();
        this.maxEncodedPixelLength =
                widthSize + delimSize +
                        heightSize + delimSize +
                        colorSize + delimSize +
                        colorSize + delimSize +
                        colorSize + delimSize;
    }
}
