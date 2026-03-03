package foj.fractal.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

import static foj.fractal.engine.Statics.*;

@SuppressWarnings({
        "FieldCanBeLocal",
        "DuplicatedCode"
})

public class MandelbrotFractalMaster extends AbstractFractalMaster {

    private final Logger log = LoggerFactory.getLogger(MandelbrotFractalMaster.class);
    private final MandelbrotFractalContext context;

    @Override
    AbstractFractalContext getContext() {
        return context;
    }

    @Override
    AbstractFractalWorker getWorker(
            final int[] xpxs,
            final int[] ypxs,
            final WorkPublisher publisher) {

        return new MandelbrotFractalWorker(
                xpxs,
                ypxs,
                context,
                publisher);
    }

    public MandelbrotFractalMaster(
            final ExecutorService executorService,
            final MandelbrotFractalContext context,
            final WorkPublisher publisher) {

        log.info(context.describe());
        log.info(describeMem());

        this.context = context;
        sliceWorkHorizontally(executorService, publisher);
    }
}
