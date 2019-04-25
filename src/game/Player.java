package game;

import cnge.core.CCD;
import cnge.core.CNGE;
import cnge.core.Loop;
import cnge.graphics.Transform;

import static org.lwjgl.glfw.GLFW.*;

public class Player {

	public Transform transform;
	public float rotation;
	public float speed = 64;

	public Player(float x, float y) {
		transform = new Transform(x, y, 16, 16);
		rotation = 0;
	}

	public void update(Map map) {
		float dx = 0;
		float dy = 0;

		if(CNGE.window.keyPressed(GLFW_KEY_A)) {
			dx -= 64 * Loop.time;
			//rotation -= Math.PI * Loop.time;
		}
		if(CNGE.window.keyPressed(GLFW_KEY_D)) {
			dx += 64 * Loop.time;
			//rotation += Math.PI * Loop.time;
		}
		if(CNGE.window.keyPressed(GLFW_KEY_W)) {
			dy -= 64 * Loop.time;
			//dx += Math.cos(rotation) * speed * Loop.time;
			//dy += Math.sin(rotation) * speed * Loop.time;
		}
		if(CNGE.window.keyPressed(GLFW_KEY_S)) {
			dy += 64 * Loop.time;
			//dx -= Math.cos(rotation) * speed * Loop.time;
			//dy -= Math.sin(rotation) * speed * Loop.time;
		}

		CCD.Collision nearest = null;
		CCD.Line bestLine = null;
		CCD.Line bestWall = null;
		float bestOffX = 0;
		float bestOffY = 0;
		double nearT = 2;

		for(int o = 0; o < 2; ++o) {
			for(int p = 0; p < 2; ++p) {
				float offx = o * transform.width;
				float offy = p * transform.height;

				CCD.Line movement = new CCD.Line(
						transform.x + offx,
						transform.y + offy,
						transform.x + dx + offx,
						transform.y + dy + offy
				);

				CCD.Line[][][] levelLines = map.getTriangleZones();
				int[] bounds = map.getBoundsUnsorted(
						movement.x0,
						movement.y0,
						movement.x1,
						movement.y1
				);

				for (int i = bounds[0]; i <= bounds[1]; ++i) {
					for (int j = bounds[2]; j <= bounds[3]; ++j) {
						if (map.zoneInRange(i, j)) {
							CCD.Line[] lines = levelLines[i][j];
							int len = lines.length;
							for (int k = 0; k < len; ++k) {
								CCD.Line wall = lines[k];
								CCD.Collision now = CCD.result(movement, wall);
								if (now.collision() && now.t_ < nearT) {
									nearest = now;
									nearT = now.t_;
									bestLine = movement;
									bestWall = wall;
									bestOffX = offx;
									bestOffY = offy;
								}
							}
						}
					}
				}
			}
		}

		if (nearest != null) {
			double newT = CCD.closestPoint(bestLine.x1, bestLine.y1, bestWall);

			double wallX = CCD.xAlong(newT, bestWall) - bestOffX;
			double wallY = CCD.yAlong(newT, bestWall) - bestOffY;

			CCD.Line newLine = new CCD.Line(bestLine.x1, bestLine.y1, (float)wallX, (float)wallY);

			double colX = CCD.xAlong(1.01, newLine);
			double colY = CCD.yAlong(1.01, newLine);

			dx = (float) (colX - transform.x);
			dy = (float) (colY - transform.y);
		}

		transform.move(dx, dy);

		transform.rotation = rotation;
	}

	public void cameraUpdate() {
		Transform ct = CNGE.camera.getTransform();
		//ct.rotation = (float)(transform.rotation + Math.PI / 2);
		ct.setCenter(transform);
	}

	public void render() {
		SharedAssets.colorShader.enable();
		SharedAssets.colorShader.setUniforms(1f, 0f, 0f, 1f);
		SharedAssets.colorShader.setMvp(CNGE.camera.getMVP(CNGE.camera.getM(transform)));
		SharedAssets.rect.render();
	}

}
