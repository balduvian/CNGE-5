package cnge.core;

abstract public class LoadScreen extends Thread {

    private Loop loaderLoop;
    protected int total;

    /**
     * the scene will do this for you
     */
    public void setup(int t) {
        loaderLoop = new Loop();
        total = t;
    }

    abstract protected void loadRender(int along, int total);

    public void run() {
        CNGE.window.threadMakeContext();
        CNGE.window.threadContextualize();
        loaderLoop.loadRun(this::loadRender, total);
    }

    public void endLoad() {
        loaderLoop.setRunning(false);
    }

}
