package de.macbury.hashbot.core.graphics.rendering.mrt.buffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;

import java.nio.IntBuffer;

/**
 * Created by macbury on 07.05.14.
 */
public class GBuffer implements Disposable {
  public final static int ATTACHEMNT_COLOR    = GL30.GL_COLOR_ATTACHMENT0;
  public final static int ATTACHEMNT_NORMAL   = GL30.GL_COLOR_ATTACHMENT1;
  public final static int ATTACHEMNT_DEPTH    = GL30.GL_DEPTH_ATTACHMENT;
  public final static int ATTACHEMNT_POSITION = GL30.GL_COLOR_ATTACHMENT2;
  private final int width;
  private final int height;
  private IntBuffer handle;
  private RenderContext renderContext;

  private int framebufferHandle;
  private GL20 gl;
  public GBufferAttachment colorAttachment;
  public GBufferAttachment positionAttachment;
  public GBufferAttachment normalsAttachment;
  public GBufferAttachment depthAttachment;
  private IntBuffer attachmentsToRender;

  public GBuffer(int width, int height, RenderContext renderContext) {
    this.width          = width;
    this.height         = height;
    this.gl             = Gdx.gl;
    this.renderContext  = renderContext;
    this.handle         = BufferUtils.newIntBuffer(1);
    build();
  }

  private void build() {
    handle.clear();
    gl.glGenFramebuffers(1, handle);

    framebufferHandle = handle.get(0);
    colorAttachment    = new GBufferAttachment(ATTACHEMNT_COLOR, handle, width, height, GL30.GL_RGBA, GL30.GL_RGBA, GL20.GL_UNSIGNED_BYTE);
    positionAttachment = new GBufferAttachment(ATTACHEMNT_POSITION, handle, width, height, GL30.GL_RGBA32F, GL30.GL_RGBA, GL20.GL_FLOAT);
    normalsAttachment  = new GBufferAttachment(ATTACHEMNT_NORMAL, handle, width, height, GL30.GL_RGBA16F, GL30.GL_RGBA, GL20.GL_FLOAT);
    depthAttachment    = new GBufferAttachment(ATTACHEMNT_DEPTH, handle, width, height, GL30.GL_DEPTH_COMPONENT24, GL30.GL_RGBA, GL20.GL_FLOAT);


    gl.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferHandle); {
      colorAttachment.bindRenderTarget();
      positionAttachment.bindRenderTarget();
      normalsAttachment.bindRenderTarget();
      depthAttachment.bindRenderTarget();

      colorAttachment.generateAndBindTexture();
      positionAttachment.generateAndBindTexture();
      normalsAttachment.generateAndBindTexture();
    }

    attachmentsToRender = BufferUtils.newIntBuffer(3);

    int result = gl.glCheckFramebufferStatus(GL20.GL_FRAMEBUFFER);
    // final
    gl.glBindRenderbuffer(GL20.GL_RENDERBUFFER, 0);
    gl.glBindTexture(GL20.GL_TEXTURE_2D, 0);
    gl.glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0);

    if (result != GL20.GL_FRAMEBUFFER_COMPLETE) {
      dispose();

      if (result == GL20.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT)
        throw new IllegalStateException("frame buffer couldn't be constructed: incomplete attachment");
      if (result == GL20.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS)
        throw new IllegalStateException("frame buffer couldn't be constructed: incomplete dimensions");
      if (result == GL20.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT)
        throw new IllegalStateException("frame buffer couldn't be constructed: missing attachment");
      if (result == GL20.GL_FRAMEBUFFER_UNSUPPORTED)
        throw new IllegalStateException("frame buffer couldn't be constructed: unsupported combination of formats");
      throw new IllegalStateException("frame buffer couldn't be constructed: unknown error " + result);
    }
  }

  @Override
  public void dispose() {
    colorAttachment.dispose();
    positionAttachment.dispose();
    normalsAttachment.dispose();
    depthAttachment.dispose();
    handle.clear();
    handle.put(framebufferHandle);
    handle.flip();
    gl.glDeleteFramebuffers(1, handle);
  }

  public void begin() {
    bind();
    setFrameBufferViewport();

    attachmentsToRender.clear();
    attachmentsToRender.put(ATTACHEMNT_COLOR);
    attachmentsToRender.put(ATTACHEMNT_NORMAL);
    attachmentsToRender.put(ATTACHEMNT_POSITION);
    attachmentsToRender.flip();

    Gdx.gl30.glDrawBuffers(3, attachmentsToRender);
  }

  public void end() {
    unbind();
  }

  public void bind () {
    Gdx.graphics.getGL20().glBindFramebuffer(GL20.GL_FRAMEBUFFER, framebufferHandle);
  }

  public static void unbind () {
    Gdx.graphics.getGL20().glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0);
  }

  public void setFrameBufferViewport() {
    Gdx.gl.glViewport(0, 0, width, height);
  }

  public void setDefaultFrameBufferViewport () {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void clear() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  }
}
