package foj.fractal.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

import static foj.fractal.engine.Statics.*;

@SuppressWarnings({
        "FieldCanBeLocal",
        "DuplicatedCode"
})

public class MultipliedExponentFractalMaster extends AbstractFractalMaster {

    private final Logger log = LoggerFactory.getLogger(MultipliedExponentFractalMaster.class);
    private final MultipliedExponentFractalContext context;

    @Override
    AbstractFractalContext getContext() {
        return context;
    }

    @Override
    AbstractFractalWorker getWorker(
            final int[] xpxs,
            final int[] ypxs,
            final WorkPublisher publisher) {

        return new MultipliedExponentFractalWorker(
                xpxs,
                ypxs,
                context,
                publisher);
    }

    public MultipliedExponentFractalMaster(
            final ExecutorService executorService,
            final MultipliedExponentFractalContext context,
            final WorkPublisher publisher) {

        log.info(context.describe());
        log.info(describeMem());

        this.context = context;
        sliceWorkHorizontally(executorService, publisher);
    }
}
