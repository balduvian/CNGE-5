package game;

import cnge.core.AssetBundle;
import cnge.graphics.shape.CircleShape;
import cnge.graphics.shape.RectShape;
import cnge.graphics.texture.Texture;
import game.shaders.CircleShader;
import game.shaders.ColorShader;
import game.shaders.TextureShader;

import static cnge.graphics.texture.TexturePreset.TP;

public class GameAssets extends AssetBundle {

    public static final int GAME_ASSETS_LOAD_NUMBER = 6;

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
    public void loadRoutine() {
        doLoad(lagTexture = new Texture("res/icon.png", TP()));
        doLoad(textureShader = new TextureShader());
        doLoad(circleShader = new CircleShader());
        doLoad(colorShader = new ColorShader());
        doLoad(rect = new RectShape());
        doLoad(circle = new CircleShape(16));
    }

    @Override
    public void unLoadRoutine() {
        doLoad(lagTexture.destroy());
        doLoad(textureShader.destroy());
        doLoad(rect.destroy());
    }

}
