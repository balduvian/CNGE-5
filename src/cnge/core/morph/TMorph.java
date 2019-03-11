package cnge.core.morph;

import cnge.core.timer.Timer;
import cnge.graphics.Transform;

public class TMorph {
	
	/*
	 * the morphers
	 */
	
	private static final Morpher doNothing = new Morpher() {
		public void morph(Transform t, Interpolator i, float start, float end, float along) {}
	};
	
	private static final Morpher doTransX = new Morpher() {
		public void morph(Transform t, Interpolator i, float start, float end, float along) {
			t.x = i.interpolate(start, end, along);
		}
	};
	
	private static final Morpher doTransY = new Morpher() {
		public void morph(Transform t, Interpolator i, float start, float end, float along) {
			t.y = i.interpolate(start, end, along);
		}
	};
	
	private static final Morpher doRotate = new Morpher() {
		public void morph(Transform t, Interpolator i, float start, float end, float along) {
			t.rotation = i.interpolate(start, end, along);
		}
	};
	
	private static final Morpher doScaleX = new Morpher() {
		public void morph(Transform t, Interpolator i, float start, float end, float along) {
			t.wScale = i.interpolate(start, end, along);
		}
	};
	
	private static final Morpher doScaleY = new Morpher() {
		public void morph(Transform t, Interpolator i, float start, float end, float along) {
			t.hScale = i.interpolate(start, end, along);
		}
	};
	
	/*
	 * the interpolators
	 */
	
	public static final Interpolator lINEAR = new Interpolator() {
		public float interpolate(float start, float end, float along) {
			return betweenValues(start, end, along);
		}
	};
	
	public static final Interpolator SQUARE = new Interpolator() {
		public float interpolate(float start, float end, float along) {
			return betweenValues(start, end, (float)Math.pow(along, 2));
		}
	};
	
	public static final Interpolator ROOT = new Interpolator() {
		public float interpolate(float start, float end, float along) {
			return betweenValues(start, end, (float)Math.sqrt(along));
		}
	};
	
	public static final Interpolator UNDERCIRCLE = new Interpolator() {
		public float interpolate(float start, float end, float along) {
			return betweenValues(start, end, 1 - (float)Math.sqrt(1 - Math.pow(along, 2)));
		}
	};
	
	public static final Interpolator OVERCIRCLE = new Interpolator() {
		public float interpolate(float start, float end, float along) {
			return betweenValues(start, end, (float)Math.sqrt(1 - Math.pow(along - 1, 2)));
		}
	};
	
	public static final Interpolator COSINE = new Interpolator() {
		public float interpolate(float start, float end, float along) {
			return betweenValues(start, end, (float)( (Math.cos(Math.PI * (along - 1)) + 1) / 2));
		}
	};
	
	private Transform modify;
	
	private float xPos;
	private float yPos;
	private float rotation;
	private float wScale;
	private float hScale;
	
	private float oXPos;
	private float oYPos;
	private float oRotation;
	private float oWScale;
	private float oHScale;
	
	private Morpher translatorX;
	private Morpher translatorY;
	private Morpher rotator;
	private Morpher scalorX;
	private Morpher scalorY;
	
	private Interpolator interpolator;

	private Timer timer;

	public static float betweenValues(float start, float end, float interpolated) {
		return ((end - start) * interpolated + start);
	}
	
	public TMorph(Timer r, Transform t, Interpolator i) {
		timer = r;
		
		modify = t;
		interpolator = i;
		
		oXPos = modify.x;
		oYPos = modify.y;
		oRotation = modify.rotation;
		oWScale = modify.wScale;
		oHScale = modify.hScale;
		xPos = oXPos;
		yPos = oYPos;
		rotation = oRotation;
		wScale = oWScale;
		hScale = oHScale;
		
		translatorX = doNothing;
		translatorY = doNothing;
		rotator = doNothing;
		scalorX = doNothing;
		scalorY = doNothing;
	}
	
	public TMorph addPositionX(float x) {
		xPos = x;
		translatorX = doTransX;
		return this;
	}
	
	public TMorph addPositionY(float y) {
		yPos = y;
		translatorY = doTransY;
		return this;
	}
	
	public TMorph addRotation(float r) {
		oRotation = r;
		rotator = doRotate;
		return this;
	}
	
	public TMorph addScaleW(float w) {
		wScale = w;
		scalorX = doScaleX;
		return this;
	}
	
	public TMorph addScaleH(float h) {
		hScale = h;
		scalorY = doScaleY;
		return this;
	}
	
	/**
	 * @return TRUE if it's done
	 */
	public boolean update() {
		boolean finished = timer.update();
		
		float along = timer.getAlong();

		translatorX.morph(modify, interpolator, oXPos, xPos, along);
		translatorY.morph(modify, interpolator, oYPos, yPos, along);
		rotator.morph(modify, interpolator, oRotation, rotation, along);
		scalorX.morph(modify, interpolator, oWScale, wScale, along);
		scalorY.morph(modify, interpolator, oHScale, hScale, along);
			
		return finished;
	}

	public void reset() {
		timer.reset();
		oXPos = modify.x;
		oYPos = modify.y;
		oRotation = modify.rotation;
		oWScale = modify.wScale;
		oHScale = modify.hScale;
	}
	
	public Timer timer() {
		return timer;
	}
	
	private interface Morpher {
		public void morph(Transform t, Interpolator i, float start, float end, float along);
	}
	
	protected interface Interpolator {
		public float interpolate(float start, float end, float along);
	}
	
}
