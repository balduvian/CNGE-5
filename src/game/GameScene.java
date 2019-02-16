package game;

import cnge.core.AssetBundle;
import cnge.core.CNGE;
import cnge.core.Scene;
import cnge.graphics.Shader;
import cnge.graphics.Transform;
import cnge.graphics.Window;
import cnge.graphics.shapes.RectShape;
import cnge.graphics.texture.Texture;
import game.shaders.TextureShader;

public class GameScene extends Scene {

    private Transform box;

    public GameScene() {
        super(new GameLoadScreen(), 10, GameAssets.class);
    }

    @Override
    protected void sceneLoad() {
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
        AssetBundle.doLoad();
    }

    @Override
    protected void sceneStart() {
        box = new Transform(10, 10, 60, 60);
        GameAssets.lagTexture = new Texture("res/icon.png");
        GameAssets.textureShader = new TextureShader();
        //GameAssets.rect = new RectShape();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        Window.clear(1, 0, 0.5f, 1);

        GameAssets.lagTexture.bind();

        GameAssets.textureShader.enable();
        GameAssets.textureShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(box)));

        GameAssets.rect.render();

        Shader.disable();

        Texture.unbind();

    }

}
