package cnge.core;

abstract public class AssetBundle {

	private static int along;
	private static int total;
	private static LoadScreen loadScreen;

	protected int subTotal;

	/**
	 * sets up a load session, with the loadscreen to use, and the total number of assets for this session,
	 * also resets the along variable
	 *
	 * @param l - loadscreen
	 * @param t - total
	 */
	public static void setup(LoadScreen l, int t) {
		loadScreen = l;
		total = t;
		along = 0;
	}

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

	abstract public void loadRoutine();

	abstract public void unLoadRoutine();

	@SuppressWarnings("unused")
	public static void doLoad(Object... asset) {
		//first it lods the asset
		//then it renders that it has loaded
		loadScreen.loadRender(++along, total);
	}

}
