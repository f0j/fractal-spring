package foj.fractal.handler;

import foj.fractal.engine.AbstractFractalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import static foj.fractal.engine.Statics.encodeMessage;

@SuppressWarnings({
        "NullableProblems",
        "StringBufferReplaceableByString",
        "UnnecessaryLocalVariable",
        "CommentedOutCode"
})

public abstract class AbstractStreamHandler implements WebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractStreamHandler.class);

    @Override
    public Mono<Void> handle(final WebSocketSession session) {

//        {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("  handle=").append(session.hashCode());
//            LOG.info(sb.toString());
//        }

        final Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        final Mono<Void> sessionMono =  session.send(
                session.receive()
                        .map(message -> process(session, message.getPayloadAsText(), sink))
                        .mergeWith(sink.asFlux())
                        .map(session::textMessage));

        return sessionMono;
    }

    protected String process(
            final WebSocketSession session,
            final String payload,
            final Sinks.Many<String> sink) {

        {
            final StringBuilder sb = new StringBuilder();
            sb.append("received=").append(payload);
            LOG.info(sb.toString());
        }

        if (payload == null || payload.isEmpty()) {
            final String sent = encodeMessage("bad-payload");
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                LOG.info(sb.toString());
            }
            return sent;
        }

        if (payload.equals(",close")) {
            final String sent = ",closed";
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                LOG.info(sb.toString());
            }
            return sent;
        }

        if (payload.equals(",ping")) {
            final String sent = ",pong";
            {
                final StringBuilder sb = new StringBuilder();
                sb.append("    sent=").append(sent);
                LOG.info(sb.toString());
            }
            return sent;
        }

        return null;
    }

    protected int getWidth(final String[] params) {
        return Integer.parseInt(params[0]);
    }

    protected int getHeight(final String[] params) {
        return Integer.parseInt(params[1]);
    }

    protected void checkMoveParams(
            final String payload,
            final String[] split,
            final AbstractFractalContext context) {

        if (payload.contains(",move,")) {
            for (int i = 0; i < split.length - 1; i++) {
                if (split[i].equals("move")) {
                    final int shiftX = Integer.parseInt(split[i + 1]);
                    final int shiftY = Integer.parseInt(split[i + 2]);
                    final double re = Double.parseDouble(split[i + 3]);
                    final double im =  Double.parseDouble(split[i + 4]);
                    final double zoom = Double.parseDouble(split[i + 5]);
                    context.addMoveParams(shiftX, shiftY, re, im, zoom);
                }
            }
        }
    }

    protected void checkZoomInParams(
            final String payload,
            final String[] split,
            final AbstractFractalContext context) {

        if (payload.contains(",two-zoom-in,")) {
            for (int i = 0; i < split.length - 1; i++) {
                if (split[i].equals("two-zoom-in")) {
                    final double re = Double.parseDouble(split[i + 1]);
                    final double im =  Double.parseDouble(split[i + 2]);
                    final double zoom = Double.parseDouble(split[i + 3]);
                    context.addZoomIn(re, im, zoom);
                }
            }
        }
    }

    protected void checkZoomOutParams(
            final String payload,
            final String[] split,
            final AbstractFractalContext context) {

        if (payload.contains(",two-zoom-out,")) {
            for (int i = 0; i < split.length - 1; i++) {
                if (split[i].equals("two-zoom-out")) {
                    final double re = Double.parseDouble(split[i + 1]);
                    final double im =  Double.parseDouble(split[i + 2]);
                    final double zoom = Double.parseDouble(split[i + 3]);
                    context.addZoomOut(re, im, zoom);
                }
            }
        }
    }
}
