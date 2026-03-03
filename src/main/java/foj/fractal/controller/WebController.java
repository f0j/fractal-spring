package foj.fractal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings({
        "CommentedOutCode",
        "FieldCanBeLocal",
        "FieldMayBeFinal"
})

@Controller
public class WebController {

    private static final Logger LOG = LoggerFactory.getLogger(WebController.class);
    private static final String INLINE_CSS_NAME = "inlineCss";
    private static final String INLINE_JS_NAME =  "inlineJs";

    private static boolean USE_DYNAMIC = true;
    private static String INLINE_CSS = null;
    private static String INLINE_JS = null;
    static {

//        {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("absolute=").append(Path.of("").toAbsolutePath());
//            LOG.info(sb.toString());
//        }

        try {
            INLINE_CSS = Files.readString(Path.of("src/main/resources/static/css/main.css"));
        } catch (final Exception e) {
            LOG.warn(e.getMessage());
        }

        try {
            INLINE_JS = Files.readString(Path.of("src/main/resources/static/js/main.js"));
        } catch (final Exception e) {
            LOG.warn(e.getMessage());
        }
    }

    /**
     * Inline the JS and CSS so that changes to these files are
     * reflected without worrying about client caching or server restarts.
     */
    private void addInline(final Model model) {
        if (USE_DYNAMIC) {
            try {
                model.addAttribute(
                        INLINE_CSS_NAME,
                        Files.readString(Path.of("src/main/resources/static/css/main.css")));

            } catch (final Exception e) {
                LOG.warn(e.getMessage());
            }

            try {
                model.addAttribute(
                        INLINE_JS_NAME,
                        Files.readString(Path.of("src/main/resources/static/js/main.js")));

            } catch (final Exception e) {
                LOG.warn(e.getMessage());
            }
        } else {
            model.addAttribute(INLINE_CSS_NAME, INLINE_CSS);
            model.addAttribute(INLINE_JS_NAME, INLINE_JS);
        }
    }

    @GetMapping("/")
    public String index(final Model model) {
        addInline(model);
        return "index";
    }

    @GetMapping("/randomized")
    public String randomized(final Model model) {
        addInline(model);
        return "randomized";
    }

    @GetMapping("/mandelbrot")
    public String mandelbrot(final Model model) {
        addInline(model);
        return "mandelbrot";
    }

    @GetMapping("/squared-divided")
    public String squaredDivided(final Model model) {
        addInline(model);
        return "squared-divided";
    }

    @GetMapping("/multiplied-exponent")
    public String multipliedExponent(final Model model) {
        addInline(model);
        return "multiplied-exponent";
    }

    @GetMapping("/julia")
    public String julia(final Model model) {
        addInline(model);
        return "julia";
    }
}
