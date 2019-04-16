package cnge.graphics;
import cnge.core.CNGE;
import org.joml.Matrix4f;

public class Camera3D extends CNGE {

	private Transform3D transform;

	private Matrix4f projection, projectionView;

	public Camera3D(float w, float h, float d) {
		transform = new Transform3D();
		projectionView = new Matrix4f();
		setDims(w, h, d);
	}
	
	/**
	 * sets virtual space back to the dimensions of the game
	 */
	public void defaultDims() {
		setDims(gameWidth, gameHeight, gameDepth);
	}
	
	/**
	 * sets the virtual space the camera renders to
	 * 
	 * @param w - virtual width
	 * @param h - virtual height
	 */
	public void setDims(float w, float h, float d) {
		transform.setSize(w, h, d);
		projection = new Matrix4f().setPerspective(5f / 18f, w / h, 1, -d);
		update();
	}
	
	/**
	 * sets the projection view of the camera.
	 * 
	 * you need to do this after every transformation or else things won't loadRender with the new camera transformation
	 */
	public void update() {
		projection.scale(transform.wScale, transform.hScale, transform.dScale, projectionView);
		projection.rotateX(-transform.rotationX, projectionView);
		projection.rotateY(-transform.rotationY, projectionView);
		projection.rotateZ(-transform.rotationZ, projectionView);
		projection.translate(-transform.x, -transform.y, -transform.z, projectionView);
	}
	
	/**
	 * gets the model matrix from vao certain transform
	 * 
	 * @param transform - the transform of the model
	 * 
	 * @return the model matrix in world coordiantes
	 */
	public Matrix4f getM(Transform3D transform) {
		return new Matrix4f()
				.translate(transform.x + ((-transform.width * transform.wScale) / 2) + 2 * (transform.width / 2), transform.y + ((-transform.height * transform.hScale) / 2) + 2 * (transform.height / 2), 0)

				.rotateX(transform.rotationX)
				.rotateY(transform.rotationY)
				.rotateZ(transform.rotationZ)
				
				.translate(-(transform.width / 2), -(transform.height / 2), -(transform.depth / 2))
				
				.scale(transform.getWidth(), transform.getHeight(), transform.getDepth());
	}

	/**
	 * gets the mvp matrix from vao certain model matrix
	 * 
	 * @param model - the matrix of the model
	 * 
	 * @return the mvp matrix in ndc
	 */
	public Matrix4f getMVP(Matrix4f model) {
		return new Matrix4f(projectionView).mul(model);	
	}
	
	/**
	 * gets the view matrix from vao certain model matrix for gui models.
	 * Basically like the camera is at position 0,0
	 * 
	 * @param model - the matrix of the model
	 * 
	 * @return the model projection matrix in gui ndc
	 */
	public Matrix4f getModelProjectionMatrix(Matrix4f model) {
		return new Matrix4f(projection).mul(model);
	}
	
	/**
	 * gets the model projection matrix width vao ceratin width and height centered on the screen
	 * 
	 * @param w - the percent width that the model takes up of the screen
	 * @param h - the percent height that the model takes up of the screen
	 * 
	 * @return the model projection matrix in ndc coordinates
	 */
	public Matrix4f ndcFullMatrix(float w, float h) {
		return new Matrix4f().translate(-w, -h, 0).scale(2 * w, 2 * h, 1);
	}
	
	/**
	 * gets the model projection matrix filling up ndc coordinates
	 * 
	 * @return the model projection matrix in ndc coordinates
	 */
	public Matrix4f ndcFullMatrix() {
		return new Matrix4f().translate(-1, -1, 0).scale(2, 2, 1);
	}
	
	/**
	 * gets the camera's transform
	 * 
	 * @return the camera's transform
	 */
	public Transform3D getTransform() {
		return transform;
	}
}