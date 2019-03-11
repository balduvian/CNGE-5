package cnge.core.morph;

import cnge.core.Loop;
import cnge.core.morph.TMorph.Interpolator;
import cnge.core.timer.Timer;

public class CMorph {
	
	private Timer timer;
	
	private float start;
	private float end;
	
	private Interpolator interpolator;
	
	public CMorph(Timer t, float s, float e, Interpolator i) {
		timer = t;
		
		interpolator = i;
		
		start = s;
		end = e;
	}
	
	public float update() {
		timer.update();

		float along = timer.getAlong();

		return interpolator.interpolate(start, end, along);
	}

	public void reset(float s, float e) {
		timer.reset();
		start = s;
		end = e;
	}

	public Timer getTimer() {
		return timer;
	}
	
}
