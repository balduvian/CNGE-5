package cnge.graphics.shape;

import cnge.graphics.Destroyable;

abstract public class Shape implements Destroyable {
	
	protected VAO vbo;

	protected void init(int numAttribs, float vertices[], int[] indices, int drawMode) {
		vbo = new VAO(numAttribs, vertices, indices, drawMode);
	}

	public void render() {
		vbo.render();
	}

	public Void destroy() {
		vbo.destroy();
		return null;
	}
	
}