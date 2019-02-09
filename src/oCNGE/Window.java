package oCNGE;

import static org.lwjgl.glfw.GLFW.*; // allows us to create windows
import static org.lwjgl.system.MemoryUtil.*; // allows us to use 'NULL' in our code, note this is slightly different from java's 'null'

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import org.lwjgl.opengl.*;

public class Window {

    /*
     * constants
     */
    public static final int DEFAULT_MONITOR_REFRESH = -1;

    /*
     * window init parameters with their default values
     */
    private boolean resizable = true;
    private boolean full = false;
    private int width = 640;
    private int height = 480;
    private String name = "oCNGE 5";
    private int monitorIndex = 0;
    private int refreshRate = DEFAULT_MONITOR_REFRESH;
    private boolean vSync = true;

    private PointerBuffer monitors;
    private int numMonitors;
    private long window;
    private GLFWVidMode vidMode;
    private long monitor;

    public Window() {
        init();
    }

    public Window initResizable(boolean r) {
        resizable = r;
        return this;
    }

    public Window initFull(boolean f) {
        full = true;
        return this;
    }

    public Window initWidth(int w) {
        width = w;
        return this;
    }

    public Window initHeight(int h) {
        height = h;
        return this;
    }

    public Window initName(String n) {
        name = n;
        return this;
    }

    public Window initMonitorIndex(int m){
        monitorIndex = m;
        return this;
    }

    public Window initRefreshRate(int r) {
        refreshRate = r;
        return this;
    }

    public Window initVsync(boolean v) {
        vSync = v;
        return this;
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            System.err.println("GLFW failed to init p");
        }

        //GET THE MONITORS
        reGetMonitors();

        //set our monitor based on our inputted monitor index
        monitor = monitors.get(monitorIndex);

        //get the vidmode from the monitor
        vidMode = glfwGetVideoMode(monitor);

        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_REFRESH_RATE, calcRefreshRate());

        if(full) {
            window = glfwCreateWindow(0, 0, name, monitor, NULL);
        } else {
            window = glfwCreateWindow(width, height, name, NULL, NULL);
        }

        //create opengl context
        glfwMakeContextCurrent(window);
        glfwSwapInterval(vSync ? 1 : 0);

        glfwShowWindow(window);

        GL.createCapabilities();
    }

    public void reGetMonitors(){
        monitors = glfwGetMonitors();
        numMonitors = monitors.limit();
    }

    public void setRefreshRate(int r) {
        refreshRate = r;
    }

    public void setMonitor(int index) {
        monitorIndex = index;
    }

    public void resetMode() {
        monitor = monitors.get(monitorIndex);
        vidMode = glfwGetVideoMode(monitor);
        glfwSetWindowMonitor(window, monitor, 0, 0, width, height, calcRefreshRate());
    }

    public int calcRefreshRate() {
        if (refreshRate == DEFAULT_MONITOR_REFRESH) {
            return vidMode.refreshRate();
        } else {
            return refreshRate;
        }
    }

    public long getLong() {
        return window;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

}
