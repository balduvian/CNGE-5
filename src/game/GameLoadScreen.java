package game;

import cnge.core.CNGE;
import cnge.core.LoadScreen;
import cnge.graphics.Camera;
import cnge.graphics.Shader;
import cnge.graphics.shapes.RectShape;
import game.shaders.ColorShader;

public class GameLoadScreen extends LoadScreen {

    private static ColorShader colorShader;
    private static RectShape rect;

    public GameLoadScreen() {
        colorShader = new ColorShader();
        rect = new RectShape();
    }

    //TOP LEFT
    //60, 50
    //64, 54

    //BOTTOM LEFT
    //60, 94
    //64, 90

    //TOP RIGHT
    //196, 50
    //192, 54

    //BOTTOM RIGHT
    //196, 64
    //192, 90

    @Override
    protected void loadRender(int along, int total) {
        Camera cam = CNGE.camera;

        colorShader.enable();

        colorShader.setUniforms(1, 1, 1, 1);

        colorShader.setMvp(cam.getMVP(cam.getMBounds(60, 64, 50, 94)));
        rect.render();

        colorShader.setMvp(cam.getMVP(cam.getMBounds(60, 196, 50, 54)));
        rect.render();

        colorShader.setMvp(cam.getMVP(cam.getMBounds(60, 196, 90, 94)));
        rect.render();

        colorShader.setMvp(cam.getMVP(cam.getMBounds(192, 196, 50, 94)));
        rect.render();

        colorShader.setMvp(cam.getMVP(cam.getMBounds(64, 64 + (128 * ((float)along / total)), 54, 90)));
        rect.render();

        Shader.disable();
    }

}
