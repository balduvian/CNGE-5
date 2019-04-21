package game;

import cnge.core.AssetBundle;
import cnge.graphics.FrameBuffer;
import cnge.graphics.sound.Sound;
import cnge.graphics.shape.CircleShape;
import cnge.graphics.texture.Texture;

import static cnge.graphics.texture.TexturePreset.TP;

public class GameAssets extends AssetBundle {

    public static final int GAME_ASSETS_LOAD_NUMBER = 5;

    /*
     *
     */
    public static Texture lagTexture;
    public static CircleShape circle;
    public static FrameBuffer planetBuffer;
    public static Sound song0;
    public static Sound song1;
    /*
     *
     */

    public GameAssets() {
        super(GAME_ASSETS_LOAD_NUMBER);
    }

    @Override
    public void loadRoutine() {
        doLoad(lagTexture = new Texture("res/icon.png", TP()));
        doLoad(circle = new CircleShape(16));
        doLoad(planetBuffer = new FrameBuffer(new Texture(1, 1, TP().min_linear()), false));
        doLoad(song0 = new Sound("res/sounds/song0.wav"));
        doLoad(song1 = new Sound("res/sounds/song1.wav"));
    }

    @Override
    public void unloadRoutine() {
        doLoad(lagTexture.destroy());
        doLoad(circle.destroy());
        doLoad(planetBuffer.destroy());
        doLoad(song0.destroy());
        doLoad(song1.destroy());
    }

}
