package cnge.core.timer;

import cnge.core.Loop;

public class DownTimer extends Timer {

	public DownTimer(double t, TimerEnd e) {
		super(t, e);
	}

	@Override
	public boolean update() {
		if(going) {
			timer -= Loop.time;
			if(timer <= 0) {
				end();
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void resetTimer() {
		timer = time;
	}

	@Override
	public void endTimer() {
		timer = 0;
	}

	@Override
	public float getAlong() {
		return (float)(1 - (timer / time));
	}

}
