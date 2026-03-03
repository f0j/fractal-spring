package foj.fractal.engine;

import java.text.NumberFormat;

@SuppressWarnings({
        "CommentedOutCode",
        "UnnecessaryLocalVariable"
})

public class Statics {

    public final static int THREAD_COUNT = Math.max(Runtime.getRuntime().availableProcessors() - 2, 1);
    public final static String FIELD_DELIM = ",";
    public final static String STATE_NAME = "state";

    public static String encodePixel(
            final int xpx,
            final int ypx,
            final int red,
            final int green,
            final int blue) {

        final String ret = xpx + FIELD_DELIM +
                ypx + FIELD_DELIM +
                red + FIELD_DELIM +
                green + FIELD_DELIM +
                blue + FIELD_DELIM;

        return ret;
    }

    public static String encodeMessage(final String message) {
        return FIELD_DELIM + message;
    }

    public static String describeMem() {
        final StringBuilder sb = new StringBuilder();
        final long freeMem = Runtime.getRuntime().freeMemory();
        final long totalMem = Runtime.getRuntime().totalMemory();

        sb.append("memory=").append(
                NumberFormat.getNumberInstance().format(totalMem - freeMem));

        sb.append("/").append(
                NumberFormat.getInstance().format(Runtime.getRuntime().freeMemory()));

//            sb.append(", totalMem=").append(
//                    NumberFormat.getInstance().format(Runtime.getRuntime().totalMemory()));

//            sb.append(", maxmem=").append(
//                    NumberFormat.getInstance().format(Runtime.getRuntime().maxMemory()));

        return sb.toString();
    }
}
