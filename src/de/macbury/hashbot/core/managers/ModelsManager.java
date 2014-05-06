package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.models.LogoInstance;

/**
 * Created by macbury on 22.04.14.
 */
public class ModelsManager {


  public LogoInstance menuLogo() {
    LogoInstance instance = new LogoInstance((Model)HashBot.assets.get(Assets.MODEL_MENU_LOGO));

    for (Material material : instance.materials) {
      BlendingAttribute blendingAttribute = (BlendingAttribute)material.get(BlendingAttribute.Type);
      if (blendingAttribute != null) {
        blendingAttribute.opacity = 0.8f;
        blendingAttribute.sourceFunction = GL20.GL_SRC_ALPHA;
        blendingAttribute.destFunction = GL20.GL_ONE_MINUS_SRC_ALPHA;
        blendingAttribute.blended = true;
      }

    }
    return instance;
  }

}
