package foj.fractal.handler;

import foj.fractal.engine.JuliaFractalContext;
import foj.fractal.engine.JuliaFractalMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Sinks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static foj.fractal.engine.Statics.*;

@SuppressWarnings({
        "DuplicatedCode",
        "StringBufferReplaceableByString"
})

public class JuliaFractalHandler extends AbstractStreamHandler {

    private final Logger log = LoggerFactory.getLogger(JuliaFractalHandler.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT); // shutdown on kill

    @Override
    protected String process(
            final WebSocketSession session,
            final String payload,
            final Sinks.Many<String> sink) {

        final String supret = super.process(session, payload, sink);
        if (supret != null) {
            return supret;
        }

        final String[] split = payload.split(FIELD_DELIM);
        if (split.length < 2) {
            final String sent = encodeMessage("wrong-input");
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                log.info(sb.toString());
            }
            return sent;
        }

        try {
            final JuliaFractalContext context = new JuliaFractalContext(
                    getWidth(split),
                    getHeight(split));

            checkMoveParams(payload, split, context);
            checkZoomInParams(payload, split, context);
            checkZoomOutParams(payload, split, context);

            log.info(context.describe());

            final JuliaFractalMaster master = new JuliaFractalMaster(
                    executorService,
                    context,
                    new SinkPublisher(sink));

            final String sent = encodeMessage(master.getEncodedState());
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                log.info(sb.toString());
            }
            return sent;
        } catch (final Exception e) {
            log.warn("{}:{}", e.getClass().getSimpleName(), e.getMessage());
            final String sent = encodeMessage("error");
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                log.info(sb.toString());
            }
            return sent;
        }
    }
}
