package cnge.core.timer;

public abstract class Timer {
	
	protected double time;
	protected double timer;
	protected boolean going;
	protected TimerEnd timerEnd;

	public Timer(double t, TimerEnd e) {
		time = t;
		resetTimer();
		going = false;
	}

	public void start() {
		resetTimer();
		going = true;
	}

	public void end() {
		endTimer();
		going = false;
		if(timerEnd != null) {
			timerEnd.end();
		}
	}

	public void reset() {
		resetTimer();
		going = false;
	}

	public abstract boolean update();

	public abstract void resetTimer();

	public abstract void endTimer();

	public abstract float getAlong();

	public void setTime(double t) {
		time = t;
	}

	public double getTime() {
		return time;
	}

	public void setTimer(double t) {
		timer = t;
	}

	public double getTimer() {
		return timer;
	}

	public void setGoing(boolean g) {
		going = g;
	}

	public boolean getGoing() {
		return going;
	}

}
