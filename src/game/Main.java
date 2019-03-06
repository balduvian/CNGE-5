package game;

import cnge.core.*;
import cnge.core.interfaces.LoadSubLooper;
import cnge.core.interfaces.SubLooper;
import cnge.graphics.FBO;
import cnge.graphics.Shader;
import cnge.graphics.Window;
import cnge.graphics.shapes.RectShape;
import cnge.graphics.texture.Texture;
import cnge.graphics.texture.TexturePreset;
import game.shaders.ColorShader;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;

public class Main {

	/*
	 * these ones are for the load screens so they don't have to load themselves
	 */
	public static ColorShader colorShader;
	public static RectShape rect;

	private BaseShader baseShader;
	private Scene scene;

	protected Main() {
		TexturePreset.setDefaults(GL_REPEAT, GL_REPEAT, GL_LINEAR, GL_LINEAR);

		CNGE.initGameSize(256, 144);
		CNGE.initScreenMode(Screen.makeAspectScreen(), -1);
		CNGE.initWindow(new Window().initFull(false).initName("here we go").initIcon("res/icon.png").init());
		CNGE.initLoopers(this::updateRender, this::loadRender);
		CNGE.initAssetBundles(new GameAssets());
		CNGE.initDebug(true);

		colorShader = new ColorShader();
		baseShader = new BaseShader();
		rect = new RectShape();

		//entry scene
		scene = new GameScene();

		new Loop().run(scene::update, scene::render);
	}

	private void updateRender(SubLooper update, SubLooper render) {
		preRender();
		update.subLoop();
		midRender();
		render.subLoop();
		postRender();
	}

	public void loadRender(LoadSubLooper render, int along, int total) {
		preRender();
		midRender();
		render.subLoop(along, total);
		postRender();
	}

	public void preRender() {
		CNGE.window.pollEvents();
	}

	public void midRender() {
		CNGE.camera.update();
		CNGE.gameBuffer.enable();
		Window.clear(1, 0, 0, 1);
	}

	public void postRender() {
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
