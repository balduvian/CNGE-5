package cnge.core;

public class GameLoop extends CNGE {

    public static int fps;
    public static double time;
    public static long nanos;

    private LoopCall loopCall;
    private LoopCall fpsPrinter;

    public interface LoopCall {
        void call();
    }

    public GameLoop(LoopCall lc) {
        loopCall = lc;
    }

    public void run(boolean debug) {

        fpsPrinter = debug ? PRINT_FPS : NO_PRINT_FPS;

        long usingFPS = 1000000000 / framerate;
        long last = System.nanoTime();
        long lastSec = last;
        int frames = 0;
        while(!window.shouldClose()) {
            long now = System.nanoTime();
            if(now-last > usingFPS) {
                nanos = (now-last);

                loopCall.call();

                last = now;
                ++frames;
            }
            if(now-lastSec > 1000000000) {
                fps = frames;
                frames = 0;
                lastSec = now;
                fpsPrinter.call();
            }
        }
    }

    public static final LoopCall PRINT_FPS = () -> {
        System.out.println(fps);
    };

    public static final LoopCall NO_PRINT_FPS = () -> {
        //haha you get nothing, you lose
    };

}
