package game;

import cnge.core.AssetBundle;
import cnge.graphics.texture.Texture;
import cnge.graphics.texture.TexturePreset;

import static cnge.graphics.texture.TexturePreset.TP;

public class MenuAssets extends AssetBundle {

	private static final int MENU_LOADS = 1;

	public MenuAssets() {
		super(MENU_LOADS);
	}

	public static Texture menuTexture;

	@Override
	protected void loadRoutine() {
		doLoad(menuTexture = new Texture("res/textures/gibs.png", TP().mag_nearest().min_nearest()));
	}

	@Override
	protected void unloadRoutine() {
		doLoad(menuTexture.destroy());
	}
}
