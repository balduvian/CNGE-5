package cnge.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import cnge.core.CNGE;
import cnge.graphics.texture.Texture;

public class FBO extends CNGE {
	
    private int id;
    private int depthRenderBufferID;
    private Texture texture;

    /**
     * creates a new fbo that is linked to the given texture
     * 
     * @param tex - texture for the fbo
     */
    public FBO(Texture tex) {
        id = glGenFramebuffers();
        
        //bind
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        
        glDrawBuffer(GL_COLOR_ATTACHMENT0);
        
        //binding the texture
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, tex.getId(), 0);
        texture = tex;
        bindDepthRenderBuffer(tex.getWidth(), tex.getHeight());
        
        //unbind
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * makes a dummy fbo with not yet a real texture
     */
    public FBO() {
    	Texture tex = new Texture();
    	
        id = glGenFramebuffers();
        
        //bind
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        
        glDrawBuffer(GL_COLOR_ATTACHMENT0);
        
        //binding the texture
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, tex.getId(), 0);
        texture = tex;
        bindDepthRenderBuffer(tex.getWidth(), tex.getHeight());
        
        //unbind
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * after the texture has been changed, call this to adapt the framebuffer
     */
    public void resize() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture.getId(), 0);
        bindDepthRenderBuffer(texture.getWidth(), texture.getHeight());
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
    
    /**
     * binds a depth loadRender buffer to the fbo
     * 
     * @param w - width of the loadRender buffer
     * @param h - height of the loadRender buffer
     */
    private void bindDepthRenderBuffer(int w, int h) {
        depthRenderBufferID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBufferID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, w, h);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBufferID);
    }
    
    /**
     * enables THIS frameBuffer
     */
    public void enable() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glViewport(0, 0, texture.getWidth(), texture.getHeight());
    }
    
    /**
     * switches the frameBuffer back to the window
     */
    public static void enableDefault() {
    	glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, window.getWidth(), window.getHeight());
    }
    
    /**
     * returns the current texture bound to the fbo
     * 
     * @return current texture
     */
    public Texture getTexture() {
        return texture;
    }
    
    /**
     * gets the fbo handle
     * 
     * @return fbo handle
     */
    public int getId() {
        return id;
    }
    
    /**
     * gets the handle for the depth loadRender buffer
     * 
     * @return depth loadRender buffer
     */
    public int getDepthRenderBufferID() {
        return depthRenderBufferID;
    }
}