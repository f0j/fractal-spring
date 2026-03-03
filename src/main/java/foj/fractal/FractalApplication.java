package foj.fractal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FractalApplication {

    // java 17+; firefox
    // # cd ~/Development/FRACTAL/fractal && JAVA_HOME=/home/foj/.jdks/azul-17.0.16 MAVEN_OPTS="-Xms2048m -Xmx8192m" ./mvnw spring-boot:run
    // http://localhost:8080
    // # cd ~/Development/jprofiler13.0.7/bin && ./jprofiler

    // xxx favicon
    // xxx document how to generate size specific images
    // xxx document how to build jar and run
    // xxx confirm impossible to multi-thread painting a canvas in firefox
    // xxx investigate use of async for onmessage and requestAnimationFrame
    // xxx figure out performance issues e.g. gc pauses when generating + try running with fewer machine resources - warm-up?
    // xxx figure out how to close websockets that are hanging on shutdown (hooks, etc.) esp. when 1 snap starts before the previous finishes

	public static void main(final String[] args) {
		SpringApplication.run(FractalApplication.class, args);
	}
}
