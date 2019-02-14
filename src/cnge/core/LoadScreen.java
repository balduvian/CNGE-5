package cnge.core;

abstract public class LoadScreen extends Thread {

    protected int total;

    private Loop loaderLoop;

    public void giveTotal(int t) {
        total = t;
    }

    abstract protected void loadRender(int along, int total);

    public void run() {
        CNGE.window.threadMakeContext();
        loaderLoop = new Loop(this::loadRender);
        CNGE.window.threadContextualize();
        loaderLoop.loadRun(total);
    }

    public void endLoad() {
        loaderLoop.setRunning(false);
    }

}
