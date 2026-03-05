## fojFractal

Is a web application meant to explore the challenges of rendering complex fractals. Some examples:

![](http://fojfractal.francoisauger.ca/mandelbrot.png)

![](http://fojfractal.francoisauger.ca/julia.png)

## Running the Application 'the easy way' in Microsoft Windows

Download the source code as `.zip`: https://github.com/f0j/fractal-spring/archive/refs/heads/master.zip

Unzip and note location. Mine looks like: `C:\Users\FOJ\Downloads\fractal-spring-master\fractal-spring-master`

Download Java JDK to build and execute the code. For example, https://www.azul.com/downloads/?version=java-17-lts&os=windows&package=jdk#zulu and download (likely `x86 64-bit`) as `.zip`.

Unzip and note location. Mine looks like: `C:\Users\FOJ\Downloads\zulu17.64.17-ca-jdk17.0.18-win_x64\zulu17.64.17-ca-jdk17.0.18-win_x64`

You'll need to start a terminal by pressing `Windows` + `R`, then typing `cmd` and then `Enter`. Then, running three commands:

1) Change directory to the root of the project.
2) Temporarily set an environment variable so the application knows where to find the Java JDK. This variable will reset once you close the terminal.
3) Build and start the local server.

Mine look like (note the directories above; these will be different for you):

1) `cd C:\Users\FOJ\Downloads\fractal-spring-master\fractal-spring-master`
2) `set JAVA_HOME=C:\Users\FOJ\Downloads\zulu17.64.17-ca-jdk17.0.18-win_x64\zulu17.64.17-ca-jdk17.0.18-win_x64`
3) `mvnw.cmd spring-boot:run`

Your commands have to be typed without error or else the application will fail to start.

A prompt may ask you to give the Java JDK permission to access your network. The application requires the internet to download a few dependencies to build and run.

If successful, you should see `[...] Started FractalApplication [...]` in the terminal.

With the server running locally, navigate to http://localhost:8080 in your browser to see the home page. From there, you can generate various complex fractals. A good place to start is the `Mandelbrot Set`.

The `generate` button will generate a complex fractal at the default coordinates. 

Generating complex fractals requires a lot of computer resources and may take a while depending on the size of the window and your screen resolution. Note the `Progress` at the bottom left.

The `zoom-in` and `zoom-out` buttons will zoom the image at the center of the current coordinates.

The `center on click` checkbox enables clicking on the image to re-center the coordinates.

To stop the server, open the terminal that is running the application and press `Ctrl` + `C` then answering `Y` to terminate the batch job. Alternatively, the application will also stop if you simply close the terminal.

Stopping the server while it is generating an image will not automatically end the process. Your computer may be busy until the image completes even if the browser is closed.

If the server fails to stop, it may be that the browser is waiting for an image to complete. Closing the browser should solve the problem.

If the source code and Java JDK were downloaded as `zip` files, you can uninstall the application by simply deleting both folders and `zip` files. Nothing else was installed or configured on your machine.