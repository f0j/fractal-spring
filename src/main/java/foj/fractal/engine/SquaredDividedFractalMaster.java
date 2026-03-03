package foj.fractal.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

import static foj.fractal.engine.Statics.describeMem;

@SuppressWarnings({
        "FieldCanBeLocal",
        "DuplicatedCode"
})

public class SquaredDividedFractalMaster extends AbstractFractalMaster {

    private final Logger log = LoggerFactory.getLogger(SquaredDividedFractalMaster.class);
    private final SquaredDividedFractalContext context;

    @Override
    AbstractFractalContext getContext() {
        return context;
    }

    @Override
    AbstractFractalWorker getWorker(
            final int[] xpxs,
            final int[] ypxs,
            final WorkPublisher publisher) {
        return new SquaredDividedFractalWorker(
                xpxs,
                ypxs,
                context,
                publisher);
    }

    public SquaredDividedFractalMaster(
            final ExecutorService executorService,
            final SquaredDividedFractalContext context,
            final WorkPublisher publisher) {

        log.info(context.describe());
        log.info(describeMem());

        this.context = context;
        sliceWorkHorizontally(executorService, publisher);
    }
}
