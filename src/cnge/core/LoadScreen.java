package cnge.core;

import cnge.graphics.Window;

abstract public class LoadScreen {

    protected int total;

    /**
     * the scene will do this for you
     */
    public void setup(int t) {
        total = t;
    }

    abstract protected void loadRender(int along, int total);

}
