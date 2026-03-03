package foj.fractal.configuration;

import foj.fractal.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebConfig {

    @Bean
    public HandlerMapping handlerMapping() {
        final Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/randomized-ws", new RandomPixelHandler());
        map.put("/mandelbrot-ws", new MandelbrotFractalHandler());
        map.put("/squared-divided-ws", new SquaredDividedFractalHandler());
        map.put("/multiplied-exponent-ws", new MultipliedExponentFractalHandler());
        map.put("/julia-ws", new JuliaFractalHandler());
        return new SimpleUrlHandlerMapping(map, -1);
    }
}
