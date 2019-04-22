package game;

import cnge.core.AssetBundle;
import cnge.core.CNGE;
import cnge.core.Loop;
import cnge.core.Scene;
import cnge.graphics.FrameBuffer;
import cnge.graphics.Transform;
import cnge.graphics.texture.MultisampleTexture;
import cnge.graphics.texture.Texture;

import static cnge.graphics.texture.TexturePreset.TP;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class GameScene extends Scene {

    private Transform box;

    private Map1 map;

    private float songMix;

    public GameScene(Class<? extends AssetBundle>... unloads) {
        super(unloads, GameLoadScreen.class, GameAssets.class, SharedAssets.class);
    }

    @Override
    public void sceneStart() {
        box = new Transform(10, 10, 60, 60);

        GameAssets.song0.play(true);
        GameAssets.song1.play(true);

        map = new Map1();

        songMix = 0;
    }

    @Override
    public void windowReszied(int w, int h) {
        GameAssets.planetBuffer.resize(w * 2, h * 2);
    }

    @Override
    public void update() {
        if(window.keyPressed(GLFW_KEY_W)) {
            System.out.println();
        }
        if(window.keyPressed(GLFW_KEY_ENTER)) {
            setScene(new MenuScene(GameAssets.class));
        }
        if(window.keyPressed(GLFW_KEY_Y)) {
            songMix += Loop.time * 0.5f;
            if(songMix > 1) {
                songMix = 1;
            }
        } else {
            songMix -= Loop.time * 0.5f;
            if(songMix < 0) {
                songMix = 0;
            }
        }
        GameAssets.song0.setVolume(songMix);
        GameAssets.song1.setVolume(1 - songMix);
    }

    @Override
    public void render() {
        GameAssets.planetBuffer.enableTexture();

        window.clear(0, 0f, 0f, 1f);

        Transform t = camera.getTransform();
        map.render(t.x, t.y, t.width, t.height);

        CNGE.gameBuffer.enable();

        SharedAssets.textureShader.enable();
        SharedAssets.textureShader.setMvp(CNGE.camera.ndcFullMatrix());

        SharedAssets.rect.render();
    }

}
