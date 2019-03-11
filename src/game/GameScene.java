package game;

import cnge.core.AssetBundle;
import cnge.core.CNGE;
import cnge.core.Scene;
import cnge.graphics.FrameBuffer;
import cnge.graphics.Transform;
import cnge.graphics.texture.MultisampleTexture;
import cnge.graphics.texture.Texture;

public class GameScene extends Scene {

    private Transform box;
    private Transform box2;

    private Transform planetT;
    private FrameBuffer planetBuffer;

    public GameScene() {
        super(new GameLoadScreen(), 1, GameAssets.class);
    }

    @Override
    public void sceneLoad() {
        AssetBundle.doLoad(planetBuffer = new FrameBuffer(new MultisampleTexture(1, 1, 4), false));
    }

    @Override
    public void sceneStart() {
        box = new Transform(10, 10, 60, 60);
        box2 = new Transform(20, 10, 60, 60);
        System.out.println("DAB");
    }

    @Override
    public void windowReszied(int w, int h) {
        planetT = new Transform(0, 0, h, h);
        planetBuffer.resize(w, h);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        window.clear(1f, 1f, 1f, 1f);

        planetBuffer.enableTextureMultisample();

        window.clear(0, 0f, 0f, 1f);

       // GameAssets.lagTexture.bind();

        GameAssets.circleShader.enable();
        GameAssets.circleShader.setUniforms(1f, 0f, 0f, 1f);
        GameAssets.circleShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(box)));
        //GameAssets.circleShader.setMvp(CNGE.camera.ndcFullMatrix());

        GameAssets.circle.render();

        planetBuffer.resolve(CNGE.gameBuffer);
    }

}
