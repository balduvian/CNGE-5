package game;

import cnge.core.AssetBundle;
import cnge.graphics.shapes.RectShape;
import cnge.graphics.texture.Texture;
import cnge.graphics.texture.TexturePreset;
import game.shaders.TextureShader;

public class GameAssets extends AssetBundle {

    public static final int GAME_ASSETS_LOAD_NUMBER = 3;

    /*
     *
     */

    public static Texture lagTexture;
    public static TextureShader textureShader;
    public static RectShape rect;

    /*
     *
     */

    public GameAssets() {
        super(GAME_ASSETS_LOAD_NUMBER);
    }

    @Override
    protected void loadRoutine() {
        //doLoad(lagTexture = new Texture("res/icon.png", new TexturePreset()));
       // doLoad(textureShader = new TextureShader());
        //doLoad(rect = new RectShape());
        doLoad();
        doLoad();
        doLoad(rect = new RectShape());
    }

    @Override
    protected void unLoadRoutine() {

    }

}
