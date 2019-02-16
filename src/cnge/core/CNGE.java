package cnge.core;

import cnge.core.interfaces.Framer;
import cnge.core.interfaces.LoadLooper;
import cnge.core.interfaces.Looper;
import cnge.core.interfaces.SubLooper;
import cnge.graphics.ALManagement;
import cnge.graphics.Camera;
import cnge.graphics.FBO;
import cnge.graphics.Window;
import cnge.graphics.texture.Texture;

public class CNGE {

    public static final boolean CLAMP = true;
    public static final boolean WRAP = false;
    public static final boolean NEAREST = true;
    public static final boolean BILINEAR = false;

    public static boolean debug;

    public static float gameWidth;
    public static float gameHeight;

    public static boolean defaultClampHorz;
    public static boolean defaultClampVert;
    public static boolean defaultMagInterp;
    public static boolean defaultMinInterp;

    public static int framerate;

    public static Window window;
    public static Camera camera;
    public static FBO gameBuffer;
    public static Framer framer;
    public static Screen screen;
    public static ALManagement audio;
    public static AssetBundle[] assetBundles;

    public static Looper looper;
    public static LoadLooper loadLooper;

    /**
     * initializes the width and height of the game.
     * this is the most crucial piece of information on which,
     * the main game classes will be based
     *
     * @param width - the game's width, in game units
     * @param height - the game's height, ingame units
     */
    public static void initGameSize(float width, float height) {
        gameWidth = width;
        gameHeight = height;
    }

    /**
     * initializes default values in texture creation
     *
     * @param ch - deafult clamp horizontal
     * @param cv - deafult clamp vertical
     * @param gi - default mag interpolation
     * @param ni - deafult min interpolation
     */
    public static void initTextureDefaults(boolean ch, boolean cv, boolean gi, boolean ni) {
        defaultClampHorz = ch;
        defaultClampVert = cv;
        defaultMagInterp = gi;
        defaultMinInterp = ni;
    }

    /**
     * initializes screen modes for rendering the game
     *
     * @param sc - the screen, including black bar mode
     * @param fr - the call rate the game runs at
     */
    public static void initScreenMode(Screen sc, int fr) {
        screen = sc;
        framerate = fr;
    }

    /**
     * initializes not just the window, but all the remaining classes,
     * after screen.
     *
     * @param w - the window
     */
    public static void initWindow(Window w) {
        window = w;
        camera = new Camera(gameWidth, gameHeight);
        gameBuffer = new FBO(new Texture());
        audio = new ALManagement();
        screen.reFrame(window.getWidth(), window.getHeight());
    }

    public static void initDebug(boolean d) {
        debug = d;
        Loop.setFpsPrinter();
    }

    public static void initLooper(Looper l, LoadLooper ll) {
        looper = l;
        loadLooper = ll;
    }

    /**
     * make all the asset bundles immediately and store themm
     *
     * @param abs - the array of assetbundles
     */
    public static void initAssetBundles(AssetBundle... abs) {
        assetBundles = abs;
    }

}
