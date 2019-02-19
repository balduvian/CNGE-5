package cnge.core;

import cnge.graphics.Transform;

public abstract class Scene extends CNGE {

	/**
	 * starts the scene yo
	 */
	public Scene(LoadScreen loadScreen, int sceneLoadsTotal, Class<? extends AssetBundle>... bundleClasses) {

		//start to calculate how many things will need to be loaded in
		int total = sceneLoadsTotal;

		int along = 0;
		int al = assetBundles.length;
		int dl = bundleClasses.length;
		AssetBundle[] dependencies = new AssetBundle[dl];

		//get which assetbundles we are loading, also calculate the total amount of assets to load
		for(int i = 0; i < dl; ++i) {
			Class<? extends AssetBundle> bClass = bundleClasses[i];
			for(int j = 0; j < al; ++j) {
				AssetBundle ab = assetBundles[j];
				if(bClass == ab.getClass()) {
					total += ab.getTotal();
					dependencies[along++] = ab;
				}
			}
		}

		//start loadscreen
		loadScreen.setup(total);
		AssetBundle.setup(loadScreen, total);

		//now actually load the assetbundles
		for(int i = 0; i < dl; ++i) {
			dependencies[i].loadRoutine();
		}

		//now load stuff for the scene
		sceneLoad();

		//start the scene finna
		sceneStart();
	}
	
	public void setCameraCenter(float x, float y) {
		Transform ct = camera.getTransform();
		ct.setTranslation(x - ct.getWidth() / 2, y - ct.getHeight() / 2);
	}
	
	public float ccx(float x, float w) {
		Transform ct = camera.getTransform();
		return x - (ct.getWidth() / 2) + (w / 2);
	}
	
	public float ccy(float y, float h) {
		Transform ct = camera.getTransform();
		return y - (ct.getHeight() / 2) + (h / 2);
	}
	
	/*
	 * fun stuff to do with entities
	 */
	
	public void createEntity(Entity e, float x, float y) {
		e.setup(x, y);
	}
	
	public void eUpdate(Entity e) {
		e.update();
	}
	
	public void eUpdate_S(Entity e) {
		if(e != null) {
			e.update();
		}
	}
	
	public void eUpdate_OS(Entity e) {
		if(e != null) {
			e.onScreenUpdate();
			if(e.onScreen) {
				e.update();
			}
		} 
	}
	
	public void eUpdate_O(Entity e) {
		e.onScreenUpdate();
		if(e.onScreen) {
			e.update();
		}
	}

	/*
	 * fun rendering stuff to do with entities
	 */
	
	public void eRender(Entity e) {
		e.render();
	}
	
	/**
	 * renders an entity, checking whether its on screen.
	 * USE FOR ENTITIES THAT WILL NEVER BE NULL
	 * 
	 * @param e - the entity to render
	 */
	public void eRender_O(Entity e) {
		if(e.onScreen) {
			e.render();
		}
	}
	
	/**
	 * 
	 */
	public void eRender_S(Entity e) {
		if(e != null) {
			e.render();
		}
	}
	
	/**
	 * renders an entity, but checks if it's null first
	 * 
	 * @param e - the entity to render
	 */
	public void eRender_OS(Entity e) {
		if(e != null && e.onScreen) {
			e.render();
		}
	}
	
	/*
	 * override this stuff to do stuff with the scene
	 */

	/**
	 * will be called before the scene starts, does loads that will still have the load screen on
	 */
	abstract protected void sceneLoad();

	abstract protected void sceneStart();

	abstract public void render();
	
	abstract public void update();
	
}