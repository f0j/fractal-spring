package foj.fractal.handler;

import foj.fractal.engine.RandomPixelMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static foj.fractal.engine.Statics.*;

@SuppressWarnings({"StringBufferReplaceableByString"})

public class RandomPixelHandler extends AbstractStreamHandler {

    private final Logger log = LoggerFactory.getLogger(RandomPixelHandler.class);
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
        if (split.length != 2) {
            final String sent = encodeMessage("wrong-input");
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                log.info(sb.toString());
            }
            return sent;
        }

        final int maxx = Integer.parseInt(split[0]);
        final int maxy = Integer.parseInt(split[1]);
        new RandomPixelMaster(maxx, maxy, executorService, new SinkPublisher(sink));

        final String sent = encodeMessage("started");
        {
            final StringBuilder sb = new StringBuilder();
            sb.append("    sent=").append(sent);
            log.info(sb.toString());
        }

        return sent;
    }
}
