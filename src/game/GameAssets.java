package game;

import cnge.core.AssetBundle;
import cnge.graphics.FrameBuffer;
import cnge.graphics.shape.CircleShape;
import cnge.graphics.shape.RectShape;
import cnge.graphics.texture.Texture;
import game.shaders.CircleShader;
import game.shaders.ColorShader;
import game.shaders.TextureShader;

import static cnge.graphics.texture.TexturePreset.TP;

public class GameAssets extends AssetBundle<GameScene> {

    public static final int GAME_ASSETS_LOAD_NUMBER = 7;

    /*
     *
     */

    public static Texture lagTexture;
    public static TextureShader textureShader;
    public static CircleShader circleShader;
    public static RectShape rect;
    public static CircleShape circle;
    public static ColorShader colorShader;

    /*
     *
     */

    public GameAssets() {
        super(GAME_ASSETS_LOAD_NUMBER);
    }

    @Override
    public void loadRoutine(GameScene scene) {
        doLoad(lagTexture = new Texture("res/icon.png", TP()));
        doLoad(textureShader = new TextureShader());
        doLoad(circleShader = new CircleShader());
        doLoad(colorShader = new ColorShader());
        doLoad(rect = new RectShape());
        doLoad(circle = new CircleShape(16));
        doLoad(scene.planetBuffer = new FrameBuffer(new Texture(1, 1, TP().min_linear()), false));
    }

    @Override
    public void unloadRoutine(GameScene scene) {
        doLoad(lagTexture.destroy());
        doLoad(textureShader.destroy());
        doLoad(rect.destroy());
    }

}
