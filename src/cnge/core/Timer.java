package cnge.core;

/**
 * YOOOOOOOOOOOOOOOOOOOOOOOOOO
 * @author Emmett
 *
 */
public abstract class Timer {
	
	private double time;
	private double timer;
	
	private boolean going;
	
	public Timer(double t) {
		time = t;
	}
	
	public void update() {
		if(going) {
			timer -= Loop.time;
			if(timer <= 0) {
				timer = 0;
				going = false;
			}
		}
	}
	
	public void forceEnd() {
		timer = 0;
	}
	
	public void start() {
		timer = time;
		going = true;
	}
	
	public void pause() {
		going = false;
	}
	
	public void resume() {
		going = true;
	}
	
	public void reset() {
		going = false;
		timer = time;
	}
	
	public void setTime(double t) {
		time = t;
	}
	
	public float getTimer() {
		return 1 - (float)(timer / time);
	}

	public boolean isGoing() {
		return going;
	}

}