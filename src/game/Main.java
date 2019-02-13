package game;

import java.io.IOException;

import cnge.core.BaseShader;
import cnge.core.CNGE;
import cnge.core.GameLoop;
import cnge.core.Screen;
import cnge.graphics.FBO;
import cnge.graphics.Shader;
import cnge.graphics.Transform;
import cnge.graphics.Window;
import cnge.graphics.shapes.RectShape;
import cnge.graphics.texture.Texture;
import cnge.graphics.texture.TexturePreset;
import game.shaders.ColorShader;
import org.w3c.dom.Text;

public class Main {
	
	public static void main(String[] args) {
		
		/*
		 * mac startup sequence
		 * 
		 * have to add the command beforehand then re run jar
		 */
		String os = System.getProperty("os.name");

		if (os.indexOf("mac") >= 0){
			if (args.length == 0) {
				try {
	                Runtime.getRuntime().exec(new String[]{"java", "-XstartOnFirstThread", "-jar", "Spark Runner 2.jar", "noReRun"});
	                System.exit(-1);
	            } catch (IOException ex) { ex.printStackTrace(); }
			}else {
				new Main();
			}
		}else {
			new Main();
		}
		
	}

	BaseShader baseShader;
    RectShape rect;
    ColorShader colorShader;
	Texture tt;

	private Main() {
		CNGE.initGameSize(160, 90);
		CNGE.initScreenMode(Screen.makeAspectScreen(), -1);
		CNGE.initTextureDefaults(CNGE.WRAP, CNGE.WRAP, CNGE.NEAREST, CNGE.NEAREST);
		CNGE.initWindow(new Window().initFull(false).initName("here we go").initIcon("res/icon.png").init());

		baseShader = new BaseShader();
		rect = new RectShape();
		colorShader = new ColorShader();

		tt = new Texture("res/icon.png", new TexturePreset());

		new GameLoop(this::frame).run(true);
	}

	private void frame() {
		CNGE.window.pollEvents();

		CNGE.camera.update();


		//TODO update

        CNGE.gameBuffer.enable();
        Window.clear(1, 0, 0, 1);

        render();

        FBO.enableDefault();
        Window.clear(0, 0.5f, 0.6f, 1);

        CNGE.gameBuffer.getTexture().bind();
        baseShader.enable();
        baseShader.setMvp(CNGE.camera.ndcFullMatrix());
        CNGE.screen.setScreenViewport();

        rect.render();

        Shader.disable();
        Texture.unbind();

        CNGE.window.swap();
	}

	Transform bt = new Transform(20, 20, 20, 20);

	private void render() {

        colorShader.enable();

        colorShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(bt)));

        colorShader.setUniforms(1, 0, 1, 1);

        rect.render();

        Shader.disable();
	}

}
