package game;

import cnge.core.CCD;
import cnge.core.CNGE;
import cnge.graphics.Transform;
import game.shaders.ColorShader;

public class Map1 extends Map {

    Transform lineTransform;

    public Map1() {
        super(6, 6, "res/levels/stage4.svg");
        lineTransform = new Transform();
    }

    public void renderTri(Triangle t, int zx, int zy) {
        GameAssets.triangleShader.enable();
        GameAssets.triangleShader.setMvp(CNGE.camera.getSMVP());
        if(zx == 1 && zy == 2) {
            GameAssets.triangleShader.setUniforms(((float)zx/5+0.1f), ((float)zy/5+0.1f), 0f, 1f, t.points);
        } else {
            GameAssets.triangleShader.setUniforms(1f, 0f, 0f, 1f, t.points);
        }
        GameAssets.mTri.render();
    }

    public void renderLin(CCD.Line l, int zx, int zy) {
        double dist = Math.sqrt(Math.pow(l.x1 - l.x0, 2) + Math.pow(l.y1 - l.y0, 2));
        double cx = (l.x0 + l.x1) / 2;
        double cy = (l.y0 + l.y1) / 2;
        double angle = Math.atan((l.y1 - l.y0) / (l.x1 - l.x0));
        lineTransform.setSize((float)dist, 10f);
        lineTransform.setCenter((float)cx, (float)cy);
        lineTransform.rotation = (float)angle;

        SharedAssets.colorShader.enable();
        if(zx == 1 && zy == 2) {
            SharedAssets.colorShader.setUniforms(0.4f, 1f, 0.9f, 0.5f);
        } else {
            SharedAssets.colorShader.setUniforms(0.4f, 0.1f, 0.9f, 0.5f);
        }
        SharedAssets.colorShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(lineTransform)));
        SharedAssets.rect.render();
    }

}
