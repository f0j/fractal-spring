package foj.fractal.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static foj.fractal.engine.Statics.FIELD_DELIM;
import static foj.fractal.engine.Statics.STATE_NAME;

public abstract class AbstractFractalMaster {

    abstract AbstractFractalContext getContext();

    abstract AbstractFractalWorker getWorker(
            final int[] xpxs,
            final int[] ypxs,
            final WorkPublisher publisher);

    public void sliceWorkHorizontally(
            final ExecutorService executorService,
            final WorkPublisher publisher) {

        final AbstractFractalContext context = getContext();
        final List<AbstractFractalWorker> workers = new ArrayList<>();

        // workers[0] contains xpxs[0]xpys[0]=1/1, xpxs[1]xpys[1]=1/2, xpxs[2]xpys[2]=1/3, etc.
        // workers[1] contains xpxs[0]xpys[0]=2/1, xpxs[1]xpys[1]=2/2, xpxs[2]xpys[2]=2/3, etc.
        // etc., i.e. sliced horizontally 1 px wide
        for (int x = 0; x < context.width; x++) {
            final int[] xpxs = new int[context.height];
            final int[] ypxs = new int[context.height];
            for (int y = 0; y < context.height; y++) {
                xpxs[y] = x;
                ypxs[y] = y;
            }

            workers.add(getWorker(
                    xpxs,
                    ypxs,
                    publisher));
        }

        for (final AbstractFractalWorker worker : workers) {
            executorService.execute(worker::work);
        }
    }

    public String getEncodedState() {
        final AbstractFractalContext context = getContext();

        final DoubleComplex max = AbstractFractalWorker.translateDouble(
                context.width,
                context.height,
                context.width,
                context.height,
                context.range,
                context.zoom,
                context.origRe,
                context.origIm);

        return STATE_NAME + FIELD_DELIM +
                context.origRe + FIELD_DELIM +
                context.origIm + FIELD_DELIM +
                max.re + FIELD_DELIM +
                max.im + FIELD_DELIM +
                context.zoom + FIELD_DELIM;
    }
}
