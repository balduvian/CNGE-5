package oCNGE;

import org.joml.Matrix4f;

public class Camera3D {

    private Transform3D transform;

    private Matrix4f proj;
    private Matrix4f projView;

    private float aspect;

    public Camera3D(float a) {
        transform = new Transform3D();
        aspect = a;
        project();
    }

    public Transform3D getTransform() {
        return transform;
    }

    public void project() {
        proj = new Matrix4f().setPerspective((float)Math.PI / 2, aspect, 0, Float.POSITIVE_INFINITY);
    }

    public void update() {
        proj.scale(transform.width, transform.height, transform.depth, projView);
        proj.rotateX(-transform.rx, projView);
        proj.rotateY(-transform.ry, projView);
        proj.rotateZ(-transform.rz, projView);
        proj.translate(-transform.x, -transform.y, -transform.z, projView);
    }

    public Matrix4f getModel(Transform3D t) {
        Matrix4f model = new Matrix4f();
        model.rotateX(t.x);
        model.rotateY(t.y);
        model.rotateZ(t.z);
        model.translate(t.x, t.y, t.z);
        model.scale(t.width, t.height, t.depth);
        return model;
    }

    public Matrix4f getMVP(Matrix4f model) {
        return new Matrix4f(projView).mul(model);
    }
}
