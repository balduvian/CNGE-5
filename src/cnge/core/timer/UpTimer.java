package cnge.core.timer;

import cnge.core.Loop;

public class UpTimer extends Timer {

	public UpTimer(double t, TimerEnd e) {
		super(t, e);
	}

	@Override
	public boolean update() {
		if(going) {
			timer += Loop.time;
			if (timer >= time) {
				end();
				return true;
			}
		}
		return false;
	}

	@Override
	public void resetTimer() {
		time = 0;
	}

	@Override
	public void endTimer() {
		timer = time;
	}

	@Override
	public float getAlong() {
		return (float)(timer / time);
	}

}
