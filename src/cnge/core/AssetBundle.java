package cnge.core;

abstract public class AssetBundle {

	private static int along;
	protected int subTotal;
	private boolean loaded;

	/**
	 * makes a new type of assetbundle, call this in a super for extention bundles
	 *
	 * @param st - the total number of assets to load in this bundle
	 */
	public AssetBundle(int st) {
		subTotal = st;
		loaded = false;
	}

	public int getTotal() {
		return subTotal;
	}

	public static int getAlong() {
		return along;
	}

	public static void resetAlong() {
		along = 0;
	}

	public boolean getLoaded() {
		return loaded;
	}

	public void load() {
		loaded = true;
		loadRoutine();
	}

	public void unLoad() {
		loaded = false;
		unLoadRoutine();
	}

	abstract protected void loadRoutine();

	abstract protected void unLoadRoutine();

	public static void doLoad(Object... asset) {
		++along;
	}

}
