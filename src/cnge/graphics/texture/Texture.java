package cnge.graphics.texture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import static cnge.graphics.texture.TexturePreset.TP;
import static cnge.graphics.texture.TexturePreset.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

import cnge.graphics.Destroyable;
import org.lwjgl.BufferUtils;

public class Texture implements Destroyable {
	
	private int id;
	
	private int width;
	private int height;

	protected class TextureInfo {
		public int wi;
		public int hi;
		public ByteBuffer bb;

		public TextureInfo(int w, int h, ByteBuffer b) {
			wi = w;
			hi = h;
			bb = b;
		}
	}

	/*
	 * CONSTRUCTORS
	 */
	
	public Texture(String path, TexturePreset tp) {
		TextureInfo ti = createTextureInfo(path);
		init(ti.wi, ti.hi, ti.bb, tp.clampHorz, tp.clampVert, tp.minFilter, tp.magFilter);
		System.gc();
	}

	public Texture(String path) {
		TextureInfo ti = createTextureInfo(path);
		init(ti.wi, ti.hi, ti.bb, defaultClampHorz, defaultClampVert, defaultMinFilter, defaultMagFilter);
		System.gc();
	}

	public Texture(int w, int h, ByteBuffer bb, TexturePreset tp) {
		init(w, h, bb, tp.clampHorz, tp.clampVert, tp.minFilter, tp.magFilter);
	}

	public Texture(int w, int h, ByteBuffer bb) {
		init(w, h, bb, defaultClampHorz, defaultClampVert, defaultMinFilter, defaultMagFilter);
	}

	public Texture(int w, int h) {
		init(w, h, BufferUtils.createByteBuffer(w * h * 4), GL_CLAMP_TO_BORDER, GL_CLAMP_TO_BORDER, GL_NEAREST, GL_NEAREST);
	}

	public Texture() {
		id = glGenTextures();
	}

	/*
	 * constructor helpers
	 */
	
	protected void init(int wi, int hi, ByteBuffer bb, int ch, int cv, int mf, int gf) {
		id = glGenTextures();
		width = wi;
		height = hi;

		bind();

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, ch);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, cv);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, mf);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, gf);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, wi, hi, 0, GL_RGBA, GL_UNSIGNED_BYTE, bb);

		unbind();
	}

	protected TextureInfo createTextureInfo(String path) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(path));
		} catch(IOException ex) {
			ex.printStackTrace();
			System.err.println("TEXTURE NOT FOUND, resolving to placeholder");
			try {
				image = ImageIO.read(new File("res/cnge/missing.png"));
			} catch (IOException ex2) { ex2.printStackTrace(); System.exit(-3); }
		}

		int wi = image.getWidth();
		int hi = image.getHeight();

		int[] pixels = image.getRGB(0, 0, wi, hi, null, 0, wi);

		ByteBuffer buffer = BufferUtils.createByteBuffer(wi * hi * 4);

		for(int i = 0; i < hi; ++i) {
			for(int j = 0; j < wi; ++j) {
				int pixel = pixels[i * wi + j];
				buffer.put((byte)((pixel >> 16) & 0xff));
				buffer.put((byte)((pixel >>  8) & 0xff));
				buffer.put((byte)((pixel      ) & 0xff));
				buffer.put((byte)((pixel >> 24) & 0xff));
			}
		}

		buffer.flip();

		return new TextureInfo(wi, hi, buffer);
	}

	/*
	 * stuff to do
	 */

	public void resize(int wi, int hi) {
		width = wi;
		height = hi;

		ByteBuffer bb = BufferUtils.createByteBuffer(wi * hi * 4);
		bb.flip();

		bind();

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, wi, hi, 0, GL_RGBA, GL_UNSIGNED_BYTE, bb);

		unbind();
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Void destroy() {
		glDeleteTextures(id);
		return null;
	}

}
