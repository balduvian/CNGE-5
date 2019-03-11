package cnge.graphics.shape;

import static org.lwjgl.opengl.GL15.*;

public class VAO3D extends VAO {

	private int nor;

	public VAO3D(int num, float[] vertices, int[] indices, float[] normals, int drawMode) {
		super(num, vertices, indices, drawMode);
		nor = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, nor);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
	}

}
