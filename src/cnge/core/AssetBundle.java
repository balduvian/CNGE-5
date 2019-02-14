package cnge.core;

abstract public class AssetBundle {

	private static int along;
	protected int subTotal;

	/**
	 * makes a new type of assetbundle, call this in a super for extention bundles
	 *
	 * @param st - the total number of assets to load in this bundle
	 */
	public AssetBundle(int st) {
		subTotal = st;
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

	public void load(LoadScreen l) {
		l.start();

		loadRoutine();

		l.endLoad();
		try {
			l.join();
		} catch(Exception ex) { System.exit(-3); }
	}

	public void unLoad() {
		unLoadRoutine();
	}

	abstract protected void loadRoutine();

	abstract protected void unLoadRoutine();

	public void doLoad(Object... asset) {
		++along;
	}

}
