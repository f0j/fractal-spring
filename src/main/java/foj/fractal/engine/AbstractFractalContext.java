package foj.fractal.engine;

public abstract class AbstractFractalContext {

    private static final int ZOOM = 2;
    private static final int RANGE = 4;

    protected int width;
    protected int height;
    protected int maxEncodedPixelLength;
    protected double range;

    protected boolean hasMove = false;
    protected boolean hasZoom = false;
    protected double prevRe;
    protected double prevIm;
    protected double origRe;
    protected double origIm;
    protected double zoom;

    public void addMoveParams(
            final int shiftX,
            final int shiftY,
            final double origRe,
            final double origIm,
            final double zoom) {

        this.hasMove = true;
        this.prevRe = origRe;
        this.prevIm = origIm;
        this.zoom = zoom;

        final DoubleComplex orig = MandelbrotFractalWorker.translateDouble(
                shiftX,
                shiftY,
                this.width,
                this.height,
                this.range,
                this.zoom,
                this.prevRe,
                this.prevIm);

        this.origRe = orig.re;
        this.origIm = orig.im;
    }

    public void addZoomIn(
            final double origRe,
            final double origIm,
            final double zoom) {

        this.hasZoom = true;
        this.zoom = zoom / ZOOM;

        this.hasMove = true;
        this.prevRe = origRe;
        this.prevIm = origIm;

        final DoubleComplex orig = MandelbrotFractalWorker.translateDouble(
                this.width / ZOOM,
                this.height / ZOOM,
                this.width,
                this.height,
                this.range,
                this.zoom,
                this.prevRe,
                this.prevIm);

        this.origRe = orig.re;
        this.origIm = orig.im;
    }

    public void addZoomOut(
            final double origRe,
            final double origIm,
            final double zoom) {

        this.hasZoom = true;
        this.zoom = zoom * ZOOM;

        this.hasMove = true;
        this.prevRe = origRe;
        this.prevIm = origIm;

        final DoubleComplex orig = MandelbrotFractalWorker.translateDouble(
                this.width / -RANGE,
                this.height / -RANGE,
                this.width,
                this.height,
                this.range,
                this.zoom,
                this.prevRe,
                this.prevIm);

        this.origRe = orig.re;
        this.origIm = orig.im;
    }

    public String describe() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", maxEncodedPixelLength=").append(maxEncodedPixelLength);

        if (hasMove) {
            sb.append(", move");
            sb.append(", prevRe=").append(prevRe);
            sb.append(", prevIm=").append(prevIm);
            sb.append(", origRe=").append(origRe);
            sb.append(", origIm=").append(origIm);
        }

        if (hasZoom) {
            sb.append(", range=").append(range);
            sb.append(", zoom=").append(zoom);
        }

        return sb.toString();
    }
}
