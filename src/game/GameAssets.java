package game;

import cnge.core.AssetBundle;
import cnge.graphics.texture.Texture;
import cnge.graphics.texture.TexturePreset;

public class GameAssets extends AssetBundle {

    public static Texture testTexture;

    public GameAssets() {
        super(1);
    }

    @Override
    protected void loadRoutine() {
        doLoad(testTexture = new Texture("res/icon.png", new TexturePreset()));
    }

    @Override
    protected void unLoadRoutine() {

    }

}
