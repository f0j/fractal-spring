package foj.fractal.engine;

import java.util.Arrays;

@SuppressWarnings({
        "FieldCanBeLocal",
        "unused",
        "NonStrictComparisonCanBeEquality"
})

public class ColorScheme {

    public final static int MAX_COLOR = 255;

    public final static ColorScheme SIMPLE_MONOCHROME =  new ColorScheme(0);
    public final static ColorScheme SIMPLE_HALF_LIGHTER_MONOCHROME =  new ColorScheme(1);
    public final static ColorScheme MIDDLE_64_MONOCHROME = new ColorScheme(2);
    public final static ColorScheme LESS_32_BLUE_64_MIN = new ColorScheme(3);
    public final static ColorScheme LESS48RED_LESS16BLUE_64MIN_248MAX = new ColorScheme(4);

    private final int[] red = new int[MAX_COLOR + 1];
    private final int[] green = new int[MAX_COLOR + 1];
    private final int[] blue = new int[MAX_COLOR + 1];

    /**
     * @param ix Expects int between >= 0 and < {@link #MAX_COLOR}
     * @return int corresponding exactly to underlying color
     * @throws ArrayIndexOutOfBoundsException when parameter not valid
     */
    public int red(final int ix) throws ArrayIndexOutOfBoundsException {
        return red[ix];
    }

    /**
     * @param ix Expects int between >= 0 and < {@link #MAX_COLOR}
     * @return int corresponding exactly to underlying color
     * @throws ArrayIndexOutOfBoundsException when parameter not valid
     */
    public int green(final int ix) throws ArrayIndexOutOfBoundsException {
        return green[ix];
    }

    /**
     * @param ix Expects int between >= 0 and < {@link #MAX_COLOR}
     * @return int corresponding exactly to underlying color
     * @throws ArrayIndexOutOfBoundsException when parameter not valid
     */
    public int blue(final int ix) throws ArrayIndexOutOfBoundsException {
        return blue[ix];
    }

    /**
     * @param frac Expects fraction between >= 0 and <= 1
     * @return int between 0 and {@link #MAX_COLOR}
     * @throws ArrayIndexOutOfBoundsException when parameter not valid
     */
    public int red(final double frac) throws ArrayIndexOutOfBoundsException{
        return red[(int) (MAX_COLOR * frac)];
    }

    /**
     * @param frac Expects fraction between >= 0 and <= 1
     * @return int between 0 and {@link #MAX_COLOR}
     * @throws ArrayIndexOutOfBoundsException when parameter not valid
     */
    public int green(final double frac) throws ArrayIndexOutOfBoundsException{
        return green[(int) (MAX_COLOR * frac)];
    }

    /**
     * @param frac Expects fraction between >= 0 and <= 1
     * @return int between 0 and {@link #MAX_COLOR}
     * @throws ArrayIndexOutOfBoundsException when parameter not valid
     */
    public int blue(final double frac) throws ArrayIndexOutOfBoundsException{
        return blue[(int) (MAX_COLOR * frac)];
    }

    private ColorScheme() {
        Arrays.fill(red, 0);
        Arrays.fill(green, 0);
        Arrays.fill(blue, 0);
    }

    private ColorScheme(final int type) {
        switch (type) {
            case 0: {
                for (int i = 0; i < red.length; i++) {
                    red[i] = i;
                    green[i] = i;
                    blue[i] = i;
                }
                break;
            }
            case 1: {
                for (int i = 0; i < red.length; i++) {
                    red[i] = i / 2;
                    green[i] = i / 2;
                    blue[i] = i / 2;
                }
                break;
            }
            case 2: {
                final int min = 64;
                final int max = MAX_COLOR - min;
                for (int i = 0; i < red.length; i++) {
                    if (i <= min) {
                        red[i] = min;
                        green[i] = min;
                        blue[i] = min;
                    } else if (i >= max) {
                        red[i] = max;
                        green[i] = max;
                        blue[i] = max;
                    } else {
                        red[i] = i;
                        green[i] = i;
                        blue[i] = i;
                    }
                }
                break;
            }
            case 3: {
                final int min = 64;
                final int max = MAX_COLOR;
                final int lessBlue = 32;
                for (int i = 0; i < red.length; i++) {
                    if (i <= min) {
                        red[i] = min;
                        green[i] = min;
                        blue[i] = min - lessBlue;
                    } else if (i >= max) {
                        red[i] = max;
                        green[i] = max;
                        blue[i] = max - lessBlue;
                    } else {
                        red[i] = i;
                        green[i] = i;
                        blue[i] = i - lessBlue;
                    }
                }
            } break;
            case 4: {
                final int min = 64;
                final int max = MAX_COLOR - 8;
                final int lessRed = 48;
                final int lessBlue = 16;
                for (int i = 0; i < red.length; i++) {
                    if (i <= min) {
                        red[i] = min - lessRed;
                        green[i] = min;
                        blue[i] = min - lessBlue;
                    } else if (i >= max) {
                        red[i] = max - lessRed;
                        green[i] = max;
                        blue[i] = max - lessBlue;
                    } else {
                        red[i] = i - lessRed;
                        green[i] = i;
                        blue[i] = i - lessBlue;
                    }
                }
            } break;
            default: {
            }
        }
    }
}
