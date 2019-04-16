package cnge.graphics;

/**
 * the positioning class for many many things in cnge
 * 
 */
public class Transform3D {

	public float x;
	public float y;
	public float z;
	public float width;
	public float height;
	public float depth;
	public float wScale;
	public float hScale;
	public float dScale;
	public float rotationX;
	public float rotationY;
	public float rotationZ;

	public Transform3D(float a, float o, float p, float w, float h, float d, float rx, float ry, float rz) {
		x = a;
		y = o;
		z = p;
		width = w;
		height = h;
		depth = d;
		wScale = 1;
		hScale = 1;
		dScale = 1;
		rotationX = rx;
		rotationY = ry;
		rotationZ = rz;
	}

	public Transform3D(float a, float o, float p, float w, float h, float d) {
		x = a;
		y = o;
		z = p;
		width = w;
		height = h;
		depth = d;
		wScale = 1;
		hScale = 1;
		dScale = 1;
		rotationX = 0;
		rotationY = 0;
		rotationZ = 0;
	}

	public Transform3D(float w, float h, float d) {
		x = 0;
		y = 0;
		z = 0;
		width = w;
		height = h;
		depth = d;
		wScale = 1;
		hScale = 1;
		dScale = 1;
		rotationX = 0;
		rotationY = 0;
		rotationZ = 0;
	}

	public Transform3D() {
		x = 0;
		y = 0;
		z = 0;
		width = 1;
		height = 1;
		depth = 1;
		wScale = 1;
		hScale = 1;
		dScale = 1;
		rotationX = 0;
		rotationY = 0;
		rotationZ = 0;
	}

	public Transform3D(Transform3D t) {
		set(t);
	}
	
	public void set(Transform3D t) {
		x = t.x;
		y = t.y;
		z = t.z;
		width = t.width;
		height = t.height;
		depth = t.depth;
		wScale = t.wScale;
		hScale = t.hScale;
		dScale = t.dScale;
		rotationX = t.rotationX;
		rotationY = t.rotationY;
		rotationZ = t.rotationZ;
	}

	public void set(float a, float o, float p, float w, float h, float d, float ws, float hs, float ds, float rx, float ry, float rz) {
		x = a;
		y = o;
		z = p;
		width = w;
		height = h;
		depth = d;
		wScale = ws;
		hScale = hs;
		dScale = ds;
		rotationX = rx;
		rotationY = ry;
		rotationZ = rz;
	}

	public void set(float a, float o, float p, float w, float h, float d, float rx, float ry, float rz) {
		x = a;
		y = o;
		z = p;
		width = w;
		height = h;
		depth = d;
		rotationX = rx;
		rotationY = ry;
		rotationZ = rz;
	}
	
	public void set(float a, float o, float p, float w, float h, float d) {
		x = a;
		y = o;
		z = p;
		width = w;
		height = h;
		depth = d;
	}
	
	public void setTranslation(float a, float o, float p) {
		x = a;
		y = o;
		z = p;
	}

	public void setSize(float w, float h, float d) {
		width = w;
		height = h;
		depth = d;
	}

	public void setScale(float w, float h, float d) {
		wScale = w;
		hScale = h;
		dScale = d;
	}

	public void move(float a, float o, float p) {
		x += a;
		y += o;
		z += p;
	}
	
	public void moveX(float a) {
		x += a;
	}
	
	public void moveY(float o) {
		y += o;
	}

	public void moveZ(float p) {
		z += p;
	}

	public float getWidth() {
		return width * wScale;
	}
	
	public float getHeight() {
		return height * hScale;
	}

	public float getDepth() {
		return depth * dScale;
	}
	
}