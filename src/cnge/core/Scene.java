package cnge.core;

import cnge.graphics.Camera;
import cnge.graphics.Transform;
import cnge.graphics.Window;

public abstract class Scene extends CNGE {

	/**
	 * starts the scene yo
	 */
	public Scene(LoadScreen loadScreen, int[] dependencies, int sceneLoadsTotal) {

		AssetBundle.resetAlong();
		int total = sceneLoadsTotal;

		//first we get the length of how many assets will be loaded
		int dl = dependencies.length;
		for(int i = 0; i < dl; ++i) {
			if(assetBundles[dependencies[i]] == null) {
				total += assetBundles[i].getTotal();
			}
		}

		//startup the load screen
		loadScreen.giveTotal(total);
		loadScreen.start();

		//now actually load
		for(int i = 0; i < dl; ++i) {
			if(assetBundles[dependencies[i]] == null) {
				assetBundles[i].load(loadScreen);
			}
		}

		sceneLoad();

		//shut down load screen
		try {
			loadScreen.join();
		} catch (Exception ex) {}
		window.threadContextualize();

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