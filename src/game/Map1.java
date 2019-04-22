package game;

import cnge.core.CNGE;
import cnge.graphics.Transform;
import game.shaders.ColorShader;

public class Map1 extends Map {

    public Map1() {
        super(2, 2, "res/levels/stage3.svg");
    }

    public void renderTri(Triangle t) {
        GameAssets.triangleShader.enable();
        GameAssets.triangleShader.setMvp(CNGE.camera.getSMVP());
        if(t.type == 0) {
            GameAssets.triangleShader.setUniforms(0f, 1f, 0f, 1f, t.points);
        } else {
            GameAssets.triangleShader.setUniforms(1f, 0f, 0f, 1f, t.points);
        }
        GameAssets.mTri.render();
    }

}
