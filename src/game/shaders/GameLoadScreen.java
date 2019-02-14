package game.shaders;

import cnge.core.CNGE;
import cnge.core.LoadScreen;
import cnge.graphics.Camera;
import cnge.graphics.shapes.RectShape;

public class GameLoadScreen extends LoadScreen {

    private ColorShader colorShader;
    private RectShape rectShape;

    @Override
    protected void loadRender(int along, int total) {
        Camera cam = CNGE.camera;
    }

}
