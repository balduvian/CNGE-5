package game;

import cnge.core.AssetBundle;
import cnge.core.CNGE;
import cnge.core.Scene;
import cnge.graphics.Transform;
import cnge.graphics.Window;

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
        box = new Transform(10, 10, 10, 10);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        Window.clear(1, 0, 0.5f, 1);

        GameAssets.textureShader.enable();
        GameAssets.textureShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(box)));
    }

}
