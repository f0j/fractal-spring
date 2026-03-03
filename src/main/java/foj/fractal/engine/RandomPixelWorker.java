package foj.fractal.engine;

import java.awt.*;
import java.util.Random;

import static foj.fractal.engine.Statics.encodePixel;

@SuppressWarnings({
        "CommentedOutCode"
})

public class RandomPixelWorker {

    private final int[] xpxs;
    private final int[] ypxs;
    private final WorkPublisher publisher;
    private final Random rand = new Random();

    public RandomPixelWorker(
            final int[] xpxs,
            final int[] ypxs,
            final WorkPublisher publisher) {

        this.xpxs = xpxs;
        this.ypxs = ypxs;
        this.publisher = publisher;
    }

    public void work() {
        final int size = xpxs.length;
        final StringBuilder builder = new StringBuilder(4 * 4 * 4 * 3 * 3 * 3 * size);
        for (int i = 0; i < size; i++) {
            final int xpx = xpxs[i];
            final int ypx = ypxs[i];

            final ColorScheme colorScheme = ColorScheme.MIDDLE_64_MONOCHROME;
            final int red =  colorScheme.red(rand.nextInt(ColorScheme.MAX_COLOR));
            final int green = colorScheme.green(rand.nextInt(ColorScheme.MAX_COLOR));
            final int blue = colorScheme.blue(rand.nextInt(ColorScheme.MAX_COLOR));

            final String result = encodePixel(xpx, ypx, red, green, blue);
            builder.append(result);

//            {
//                final StringBuilder sb = new StringBuilder();
//                sb.append("work=").append(result);
//                log.info(sb.toString());
//            }

        }

        publisher.publish(builder.toString());
    }
}
