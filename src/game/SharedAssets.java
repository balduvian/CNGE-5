package game;

import cnge.core.AssetBundle;
import cnge.core.Scene;
import cnge.graphics.shape.CircleShape;
import cnge.graphics.shape.RectShape;
import cnge.graphics.texture.Texture;
import game.shaders.CircleShader;
import game.shaders.ColorShader;
import game.shaders.TextureShader;

public class SharedAssets extends AssetBundle {

	private static final int NUM_LOADS = 5;

	public static RectShape rect;
	public static Texture sharedTexture;

	public static TextureShader textureShader;
	public static CircleShader circleShader;
	public static ColorShader colorShader;

	public SharedAssets() {
		super(NUM_LOADS);
	}

	@Override
	public void loadRoutine() {
		doLoad(rect = new RectShape());
		doLoad(sharedTexture = new Texture("res/textures/carrot.png"));
		doLoad(textureShader = new TextureShader());
		doLoad(circleShader = new CircleShader());
		doLoad(colorShader = new ColorShader());
	}

	@Override
	public void unloadRoutine() {
		doLoad(rect.destroy());
		doLoad(sharedTexture.destroy());
		doLoad(textureShader.destroy());
		doLoad(circleShader.destroy());
		doLoad(colorShader.destroy());
	}
}
