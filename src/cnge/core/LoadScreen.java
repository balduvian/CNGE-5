package cnge.core;

import cnge.graphics.Window;

abstract public class LoadScreen extends Thread {

    private Loop loaderLoop;
    protected int total;
    private Window window;
    private boolean going;

    /**
     * the scene will do this for you
     */
    public void setup(Window w, int t) {
        loaderLoop = new Loop();
        window = w;
        total = t;
    }

    abstract protected void loadRender(int along, int total);

    public void run() {
        going = true;
        window.threadContextualize();
        window.threadCreateCapabilties();
        loaderLoop.loadRun(this::loadRender, total);
        window.threadUnContextualize();
        going = false;
    }

    public boolean isGoing(){
        return going;
    }

}
