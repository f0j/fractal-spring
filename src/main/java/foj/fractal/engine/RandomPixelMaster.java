package foj.fractal.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@SuppressWarnings({
        "CommentedOutCode"
})

public class RandomPixelMaster {

    public RandomPixelMaster(
            final int maxx,
            final int maxy,
            final ExecutorService executorService,
            final WorkPublisher publisher) {

        final List<RandomPixelWorker> workers = new ArrayList<>();

        // workers[0] contains xpxs[0]xpys[0]=1/1, xpxs[1]xpys[1]=1/2, xpxs[2]xpys[2]=1/3, etc.
        // workers[1] contains xpxs[0]xpys[0]=2/1, xpxs[1]xpys[1]=2/2, xpxs[2]xpys[2]=2/3, etc.
        // etc., i.e. sliced horizontally 1 px wide

        for (int x = 0; x < maxx; x++) {
            final int[] xpxs = new int[maxy];
            final int[] ypxs = new int[maxy];
            for (int y = 0; y < maxy; y++) {
                xpxs[y] = x;
                ypxs[y] = y;
            }
            workers.add(new RandomPixelWorker(xpxs, ypxs, publisher));
        }

//        {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("maxx=").append(maxx).append(COMMENT_DELIM);
//            sb.append("maxy=").append(maxy).append(COMMENT_DELIM);
//            sb.append("workers=").append(workers.size()).append(COMMENT_DELIM);
//            log.info(sb.toString());
//        }

        for (final RandomPixelWorker worker : workers) {
            executorService.execute(worker::work);
        }
    }
}
