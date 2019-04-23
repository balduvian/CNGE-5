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

    private Player player;

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

        player = new Player(128, 128);
    }

    @Override
    public void windowReszied(int w, int h) {
        GameAssets.planetBuffer.resize(w * 2, h * 2);
    }

    @Override
    public void update() {
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

        if(window.keyPressed(GLFW_KEY_W)) {
            camera.getTransform().moveY((float)(Loop.time * -64));
        }
        if(window.keyPressed(GLFW_KEY_A)) {
            camera.getTransform().moveX((float)(Loop.time * -64));
        }
        if(window.keyPressed(GLFW_KEY_S)) {
            camera.getTransform().moveY((float)(Loop.time * 64));
        }
        if(window.keyPressed(GLFW_KEY_D)) {
            camera.getTransform().moveX((float)(Loop.time * 64));
        }

        if(window.keyPressed(GLFW_KEY_Z)) {
            camera.getTransform().scale((float)(0.1 * Loop.time), (float)(0.1 * Loop.time));
        }
        if(window.keyPressed(GLFW_KEY_X)) {
            camera.getTransform().scale((float)(-0.1 * Loop.time), (float)(-0.1 * Loop.time));
        }

        GameAssets.song0.setVolume(songMix);
        GameAssets.song1.setVolume(1 - songMix);

        player.update(map);
        player.cameraUpdate();
    }

    @Override
    public void render() {
        GameAssets.planetBuffer.enableTexture();

        window.clear(0, 0f, 0f, 1f);

        Transform t = camera.getTransform();
        map.render(t.x, t.y, t.width + t.x, t.height + t.y);

        for(int i = 0; i < map.getZonesWide(); ++i) {
            for(int j = 0; j < map.getZonesTall(); ++j) {
                SharedAssets.colorShader.enable();
                SharedAssets.colorShader.setUniforms(0f, 0f, 1f, 1f);
                Transform tra = new Transform(i * map.getZoneWidth() - 5, j * map.getZoneHeight() - 5, 10, 10);
                SharedAssets.colorShader.setMvp(camera.getMVP(camera.getM(tra)));
                SharedAssets.rect.render();
            }
        }

        player.render();

        //render to gamebuffer

        CNGE.gameBuffer.enable();

        SharedAssets.textureShader.enable();
        SharedAssets.textureShader.setMvp(CNGE.camera.ndcFullMatrix());

        SharedAssets.rect.render();
    }

}
