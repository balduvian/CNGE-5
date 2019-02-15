package game;

import cnge.core.AssetBundle;
import cnge.graphics.texture.Texture;
import cnge.graphics.texture.TexturePreset;
import game.shaders.TextureShader;

public class GameAssets extends AssetBundle {

    public static final int GAME_ASSETS_LOAD_NUMBER = 14;

    /*
     *
     */

    public static Texture[] lagTextures;
    public static TextureShader textureShader;

    /*
     *
     */

    public GameAssets() {
        super(GAME_ASSETS_LOAD_NUMBER);
    }

    @Override
    protected void loadRoutine() {
        doLoad(lagTextures = new Texture[GAME_ASSETS_LOAD_NUMBER - 2]);
        doLoad(lagTextures[0] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[1] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[2] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[3] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[4] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[5] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[6] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[7] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[8] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[9] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[10] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(lagTextures[11] = new Texture("res/icon.png", new TexturePreset()));
        doLoad(textureShader = new TextureShader());
    }

    @Override
    protected void unLoadRoutine() {

    }

}
