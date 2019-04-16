package cnge.core;

abstract public class AssetBundle<SCENE extends Scene> extends CNGE {

	protected static Scene.LoadStruct loadStruct;

	private boolean loaded;
	protected int subTotal;

	public static void setup(Scene.LoadStruct ls) {
		loadStruct = ls;
	}

	public AssetBundle(int st) {
		subTotal = st;
		loaded = false;
	}

	public int getTotal() {
		return subTotal;
	}

	/**
	 * call scene calls this to load, no need to call the routine, as this will call it
	 */
	public void load(SCENE scene) {
		loadRoutine(scene);
		loaded = true;
	}

	/**
	 * the scene calls this to unload, no need to call the routine, as this will call it
	 */
	public void unload(SCENE scene) {
		unloadRoutine(scene);
		loaded = false;
	}

	abstract protected void loadRoutine(SCENE scene);

	abstract protected void unloadRoutine(SCENE scene);

	/**
	 * this method consolidates loading an object, and calling the loadscreen to loadRender
	 * @param asset
	 */
	@SuppressWarnings("unused")
	public static void doLoad(Object... asset) {
		loadRender.loadLoop(loadStruct.loadScreen::loadRender, ++loadStruct.along, loadStruct.total);
	}

	/**
	 * the scene schecks this to make sure it doesn't have to reload the asset bundle
	 * @return whether this asset bundle is loaded or not
	 */
	public boolean isLoaded() {
		return loaded;
	}

}
