package cnge.core;

import cnge.core.interfaces.LoadSubLooper;
import cnge.core.interfaces.SubLooper;

public class Loop extends CNGE {

    /*
     * the fps printer is above all
     */
    private static VoidCall fpsPrinter;

    /*
     * external time variables
     */
    public static int fps;
    public static double time;
    public static long nanos;

    /*
     * internal variables for the loop
     */
    private long usingFPS;
    private long last;
    private long lastSec;
    private int frames;
    private long now;

    private interface VoidCall {
        void call();
    }

    public static void setFpsPrinter() {
        fpsPrinter = debug ? PRINT_FPS : NO_PRINT_FPS;
    }

    private void beginLoop() {
        usingFPS = 1000000000 / framerate;
        last = System.nanoTime();
        lastSec = last;
        frames = 0;
        now = 0;
    }

    private boolean shouldDoFrame() {
        now = System.nanoTime();
        if(now-last > usingFPS) {
            nanos = (now-last);
            time = nanos/1000000000d;
            last = now;
            ++frames;
            return true;
        }
        return false;
    }

    private void checkSecondHasPassed() {
        if(now-lastSec > 1000000000) {
            fps = frames;
            frames = 0;
            lastSec = now;
            fpsPrinter.call();
        }
    }

    public void run(SubLooper update, SubLooper render) {
        beginLoop();
        while(!window.shouldClose()) {
           if(shouldDoFrame())
               CNGE.updateRender.loop(update, render);
           checkSecondHasPassed();
        }
    }

    public static final VoidCall PRINT_FPS = () -> System.out.println(fps);


    public static final VoidCall NO_PRINT_FPS = () -> {};

}
