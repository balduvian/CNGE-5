package cnge.graphics;

/**
 * the positioning class for many many things in cnge
 * 
 */
public class Transform3d {

	/** the x coordinate */
	public float x;
	/** the y coordinate */
	public float y;
	public float width;
	public float height;
	public float wScale;
	public float hScale;
	public float rotation;

	public Transform3d(float a, float o, float w, float h, float r) {
		x = a;
		y = o;
		width = w;
		height = h;
		wScale = 1;
		hScale = 1;
		rotation = r;
	}

	public Transform3d(float a, float o, float w, float h) {
		x = a;
		y = o;
		width = w;
		height = h;
		wScale = 1;
		hScale = 1;
		rotation = 0;
	}

	public Transform3d(float w, float h) {
		x = 0;
		y = 0;
		width = w;
		height = h;
		wScale = 1;
		hScale = 1;
		rotation = 0;
	}

	public Transform3d() {
		x = 0;
		y = 0;
		width = 1;
		height = 1;
		wScale = 1;
		hScale = 1;
		rotation = 0;
	}

	public Transform3d(Transform3d t) {
		x = t.x;
		y = t.y;
		width = t.width;
		height = t.height;
		wScale = t.wScale;
		hScale = t.hScale;
		rotation = t.rotation;
	}
	
	public void set(Transform3d t) {
		x = t.x;
		y = t.y;
		width = t.width;
		height = t.height;
		wScale = t.wScale;
		hScale = t.hScale;
		rotation = t.rotation;
	}
	
	public void set(float a, float o, float w, float h, float r) {
		x = a;
		y = o;
		width = w;
		height = h;
		rotation = r;
	}
	
	public void set(float a, float o, float w, float h) {
		x = a;
		y = o;
		width = w;
		height = h;
	}
	
	public void setTranslation(float a, float o) {
		x = a;
		y = o;
	}
	
	public void setScale(float w, float h) {
		wScale = w;
		hScale = h;
	}
	
	public Transform3d destinationScale(float w, float h) {
		Transform3d ret = new Transform3d(this);
		ret.setScale(w, h);
		return ret;
	}
	
	public Transform3d destinationTranslate(float a, float o) {
		move(a, o);
		return this;
	}
	
	public void setSize(float w, float h) {
		width = w;
		height = h;
	}

	public void move(float a, float o) {
		x += a;
		y += o;
	}
	
	public void moveX(float a) {
		x += a;
	}
	
	public void moveY(float o) {
		y += o;
	}
	
	public float getWidth() {
		return width * wScale;
	}
	
	public float getHeight() {
		return height * hScale;
	}
	
}