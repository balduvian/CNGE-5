package cnge.core;

public class Loop extends CNGE {

    /*
     * the fps printer is above all
     */
    private static GameCall fpsPrinter;

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
    private boolean running;

    /*
     * interfaces
     */
    private GameCall gameCall;
    private LoadCall loadCall;

    public interface GameCall {
        void call();
    }

    public interface LoadCall {
        void call(int along, int total);
    }

    public static void setFpsPrinter() {
        fpsPrinter = debug ? PRINT_FPS : NO_PRINT_FPS;
    }

    public Loop(GameCall gc) {
        init(gc, null);
    }

    public Loop(LoadCall lc) {
        init(null, lc);
    }

    private void init(GameCall gc, LoadCall lc) {
        gameCall = gc;
        loadCall = lc;
    }

    private void beginLoop() {
        usingFPS = 1000000000 / framerate;
        last = System.nanoTime();
        lastSec = last;
        frames = 0;
        now = 0;
        running = true;
    }

    private boolean check1() {
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

    private void check2() {
        if(now-lastSec > 1000000000) {
            fps = frames;
            frames = 0;
            lastSec = now;
            fpsPrinter.call();
        }
    }

    public void gameRun() {
        beginLoop();
        while(!window.shouldClose()) {
           if(check1())
               gameCall.call();
           check2();
        }
    }

    public void loadRun(int total) {
        beginLoop();
        while(running) {
            if(check1())
                loadCall.call(AssetBundle.getAlong(), total);
            check2();
        }
    }

    public static final GameCall PRINT_FPS = () -> {
        System.out.println(fps);
    };

    public static final GameCall NO_PRINT_FPS = () -> {
        //haha you get nothing, you lose
    };

    public void setRunning(boolean r) {
        running = r;
    }

}
