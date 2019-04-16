package game;

import cnge.core.AssetBundle;
import cnge.core.CNGE;
import cnge.core.Scene;
import cnge.graphics.FrameBuffer;
import cnge.graphics.Transform;
import cnge.graphics.texture.MultisampleTexture;
import cnge.graphics.texture.Texture;

import static cnge.graphics.texture.TexturePreset.TP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class GameScene extends Scene {

    private Transform box;
    private Transform box2;

    private Transform planetT;
    public FrameBuffer planetBuffer;

    public GameScene(Class<AssetBundle>... unloads) {
        super(unloads, GameLoadScreen.class, GameAssets.class, SharedAssets.class);
    }

    @Override
    public void sceneStart() {
        box = new Transform(10, 10, 60, 60);
        box2 = new Transform(20, 10, 60, 60);
        System.out.println("DAB");
    }

    @Override
    public void windowReszied(int w, int h) {
        planetBuffer.resize(w * 2, h * 2);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        window.clear(1f, 1f, 1f, 1f);

        planetBuffer.enableTexture();

        window.clear(0, 0f, 0f, 1f);

       // GameAssets.lagTexture.bind();

        GameAssets.circleShader.enable();
        GameAssets.circleShader.setUniforms(1f, 0f, 0f, 1f);
        GameAssets.circleShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(box)));
        //GameAssets.circleShader.setMvp(CNGE.camera.ndcFullMatrix());

        GameAssets.rect.render();

        //

        CNGE.gameBuffer.enable();

        GameAssets.textureShader.enable();
        GameAssets.textureShader.setMvp(CNGE.camera.ndcFullMatrix());

        GameAssets.rect.render();

       // planetBuffer.resolve(CNGE.gameBuffer);
    }

}
