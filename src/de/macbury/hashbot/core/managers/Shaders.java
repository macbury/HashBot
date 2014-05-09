package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.shader.ShaderManager;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by macbury on 09.05.14.
 */
public class Shaders extends ShaderManager {
  public static final String SHADER_EMPTY     = "SHADER_EMPTY";
  public static final String SHADER_DEFAULT   = "SHADER_DEFAULT";
  public static final String SHADER_DEFFERED_GBUFFER = "SHADER_DEFFERED_GBUFFER";
  public static final String SHADER_APPLY_LIGHT = "SHADER_APPLY_LIGHT";
  public static final String SHADER_APPLY_FOG = "SHADER_APPLY_FOG";

  private WatchService watchService;

  public Shaders() {
    super(Assets.SHADERS_PREFIX, HashBot.assets);
    ShaderProgram.pedantic = false;
    add(SHADER_EMPTY, "empty.vert", "empty.frag");
    add(SHADER_DEFAULT, "default.vert", "default.frag");
    add(SHADER_APPLY_LIGHT, "default.vert", "deffered/apply_light.fragment.glsl");
    add(SHADER_APPLY_FOG, "default.vert", "deffered/apply_fog.fragment.glsl");
    add(SHADER_DEFFERED_GBUFFER, "deffered.vertex.glsl", "deffered.fragment.glsl");
  }

  public void update() {
    if (HashBot.args.liveShaders) {
      reloadOnChange();
    }
  }

  //TODO: move to other class
  private void reloadOnChange() {
    if (watchService == null) {
      try {
        this.watchService = FileSystems.getDefault().newWatchService();
        Path shadersFolder = Paths.get(Gdx.files.internal(Assets.SHADERS_PREFIX+"deffered").file().getAbsolutePath());

        shadersFolder.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    WatchKey watchKey = watchService.poll();

    if (watchKey != null && watchKey.pollEvents().size() > 0) {
      reload();
      watchKey.reset();
    }
  }
}
