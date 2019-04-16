package game;

import cnge.core.AssetBundle;
import cnge.core.Scene;
import cnge.graphics.shape.RectShape;
import cnge.graphics.texture.Texture;

public class SharedAssets extends AssetBundle<Scene.Shared> {

	private static final int NUM_LOADS = 2;

	public static RectShape rect;
	public static Texture sharedTexture;

	public SharedAssets() {
		super(NUM_LOADS);
	}

	@Override
	public void loadRoutine(Scene.Shared s) {
		doLoad(rect = new RectShape());
		doLoad(sharedTexture = new Texture("res/textures/carrot.png"));
	}

	@Override
	public void unloadRoutine(Scene.Shared s) {
		doLoad(rect.destroy());
		doLoad(sharedTexture.destroy());
	}
}
