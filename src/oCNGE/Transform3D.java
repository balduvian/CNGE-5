package oCNGE;

public class Transform3D {

    public float x;
    public float y;
    public float z;
    public float rx;
    public float ry;
    public float rz;
    public float width;
    public float height;
    public float depth;

    public Transform3D(float w, float h, float d) {
        init(0, 0, 0, 0, 0, 0, w, h, d);
    }

    public Transform3D() {
        init(0, 0, 0, 0, 0, 0, 1, 1, 1);
    }

    private void init(float x, float y, float z, float rx, float ry, float rz, float w, float h, float d) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.width = w;
        this.height = h;
        this.depth = d;
    }

}
