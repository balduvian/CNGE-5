package cnge.graphics.shape;

import cnge.graphics.shape.Shape;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

/**
 * a shape that supports textures
 */
abstract public class TexShape extends Shape {

	protected void initTex(float[] vertices, int[] indices, float[] texCoords) {
		init(2, vertices, indices, GL_TRIANGLES);
		vbo.addAttrib(texCoords, 2);
	}

}
