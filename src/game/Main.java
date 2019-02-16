package game;

import java.io.IOException;

import cnge.core.*;
import cnge.core.interfaces.LoadSubLooper;
import cnge.core.interfaces.SubLooper;
import cnge.graphics.FBO;
import cnge.graphics.Shader;
import cnge.graphics.Window;
import cnge.graphics.shapes.RectShape;
import cnge.graphics.texture.Texture;
import game.shaders.TextureShader;

public class Main {

	BaseShader baseShader;
	RectShape rect;
	Scene scene;

	protected Main() {
		CNGE.initGameSize(256, 144);
		CNGE.initScreenMode(Screen.makeAspectScreen(), -1);
		CNGE.initTextureDefaults(CNGE.WRAP, CNGE.WRAP, CNGE.NEAREST, CNGE.NEAREST);
		CNGE.initWindow(new Window().initFull(false).initName("here we go").initIcon("res/icon.png").init());
		CNGE.initLooper(this::frame, this::loopFrame);
		CNGE.initAssetBundles(new GameAssets());
		CNGE.initDebug(true);

		baseShader = new BaseShader();
		rect = new RectShape();

		scene = new GameScene();

		new Loop().gameRun(scene::update, scene::render);
	}

	private void frame(SubLooper update, SubLooper render) {
		CNGE.window.pollEvents();

		update.subLoop();

		CNGE.camera.update();

        CNGE.gameBuffer.enable();
        Window.clear(1, 0, 0, 1);

        render.subLoop();

        FBO.enableDefault();
        Window.clear(0, 0.5f, 0.6f, 1);

        CNGE.gameBuffer.getTexture().bind();
        baseShader.enable();
        baseShader.setMvp(CNGE.camera.ndcFullMatrix());
        CNGE.screen.setScreenViewport();

        rect.render();

        Shader.disable();
        Texture.unbind();

        CNGE.window.swap();
	}

	private void loopFrame(LoadSubLooper render, int along, int total) {
		CNGE.window.pollEvents();

		//we don't update around these parts

		CNGE.camera.update();

		CNGE.gameBuffer.enable();
		Window.clear(1, 0, 0, 1);

		render.subLoop(along, total);

		FBO.enableDefault();
		Window.clear(0, 0.5f, 0.6f, 1);

		CNGE.gameBuffer.getTexture().bind();
		baseShader.enable();
		baseShader.setMvp(CNGE.camera.ndcFullMatrix());
		CNGE.screen.setScreenViewport();

		rect.render();

		Shader.disable();
		Texture.unbind();

		CNGE.window.swap();
	}

}
