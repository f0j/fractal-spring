package foj.fractal.handler;

import foj.fractal.engine.WorkPublisher;
import reactor.core.publisher.Sinks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SinkPublisher implements WorkPublisher {

    private final Lock lock = new ReentrantLock();
    private final Sinks.Many<String> sink;

    public SinkPublisher(final Sinks.Many<String> sink) {
        this.sink = sink;
    }

    @Override
    public void publish(String result) {
        lock.lock();
        try {

//            {
//                final StringBuilder sb = new StringBuilder();
//                sb.append("emitting=").append(result);
//                log.info(sb.toString());
//            }

            // see: https://stackoverflow.com/questions/65186439/how-to-correctly-emit-values-to-sink-from-multiple-fluxes-websocketsessionrec
            // see: https://stackoverflow.com/questions/65029619/how-to-call-sinks-manyt-tryemitnext-from-multiple-threads
            // Might as well synchronize on the emitNext which simplifies everything downstream.
            // Also enables simpler decoding and looping on the client (less garbage, simpler encoding, etc.).
            sink.tryEmitNext(result);
        } finally {
            lock.unlock();
        }
    }
}
