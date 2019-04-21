package game;

import cnge.core.AssetBundle;
import cnge.core.CNGE;
import cnge.core.LoadScreen;
import cnge.core.Scene;
import cnge.graphics.Transform;
import cnge.graphics.shape.RectShape;
import cnge.graphics.texture.Texture;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class MenuScene extends Scene {
	public MenuScene(Class<? extends AssetBundle>... unloadFlags) {
		super(unloadFlags, MenuLoadScreen.class, MenuAssets.class, SharedAssets.class);
	}

	Transform renderT;

	@Override
	public void sceneStart() {
		renderT = new Transform(20, 20, 40, 40);
	}

	@Override
	public void render() {
		SharedAssets.textureShader.enable();
		SharedAssets.textureShader.setMvp(camera.getMVP(camera.getM(renderT)));
		MenuAssets.menuTexture.bind();
		SharedAssets.rect.render();
	}

	@Override
	public void update() {
		if(window.keyPressed(GLFW_KEY_W)) {
			System.out.println("ur in menu");
			Texture ttt = MenuAssets.menuTexture;
			AssetBundle[] aaa = CNGE.assetBundles;
			System.out.println();
		}
		if(window.keyPressed(GLFW_KEY_SPACE)) {
			setScene(new GameScene(MenuAssets.class));
		}
	}
}
