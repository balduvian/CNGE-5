package cnge.graphics.shape;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class CubeShape extends Shape {

	public CubeShape() {
		vao = new VAO3D(
			2,
			new float[] {
				0, 0, 0,
				0, 1, 0,
				1, 1, 0,
				1, 0, 0,

				0, 0, 1,
				0, 1, 1,
				1, 1, 1,
				1, 0, 1
			},
			new int[] {
				0, 1, 2,
				0, 2, 3,

				0, 1, 4,
				1, 4, 5,

				4, 5, 6,
				4, 6, 7,

				7, 6, 2,
				7, 2, 3,

				0, 3, 4,
				3, 4, 7,

				1, 2, 5,
				2, 5, 6
			},
			new float[] {
				1, 0, 0,
				1, 0, 0,

				0, 1, 0,
				0, 1, 0,

				0, 0, 1,
				0, 0, 1,

				1, 1, 0,
				1, 1, 0,

				0, 1, 1,
				0, 1, 1,

				1, 0, 1,
				1, 0, 1,
			},
			GL_TRIANGLES
		);
	}

}
