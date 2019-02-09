package oCNGE;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class Base {

    public static int fps;
    public static double time;
    public static long nanos;

    private Window window;
    private int framerate;

    public Base(Window w) {
        window = w;
        framerate = window.calcRefreshRate();
    }

    public void gameLoop() {
        long usingFPS = 1000000000 / framerate;
        long last = System.nanoTime();
        long lastSec = last;
        int frames = 0;
        do {
            long now = System.nanoTime();
            if(now-last > usingFPS) {
                nanos = (now-last);

                frame();

                last = now;
                ++frames;
            }
            if(now-lastSec > 1000000000) {
                fps = frames;
                frames = 0;
                lastSec = now;
                //
                System.out.println(fps);
                //
            }
        } while(!window.shouldClose());
    }

    private void frame() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwPollEvents();

        glfwSwapBuffers(window.getLong());
    }

}
