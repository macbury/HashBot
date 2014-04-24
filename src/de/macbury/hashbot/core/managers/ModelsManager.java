package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.models.LogoInstance;
import de.macbury.hashbot.core.graphics.ui.FlatColors;

/**
 * Created by macbury on 22.04.14.
 */
public class ModelsManager {

  public ModelInstance menuItemInstance() {
    ModelInstance instance = new ModelInstance((Model)HashBot.assets.get(Assets.MODEL_MENU_ITEM));
    instance.materials.get(0).set(ColorAttribute.createDiffuse(FlatColors.WET_ASPHALT));
    for (Material material : instance.materials) {
      BlendingAttribute blendingAttribute = (BlendingAttribute)material.get(BlendingAttribute.Type);
      blendingAttribute.opacity = 0.5f;
      blendingAttribute.sourceFunction = GL20.GL_ONE;
      blendingAttribute.destFunction = GL20.GL_SRC_ALPHA;
    }
    return instance;
  }

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
    instance.position.set(0, 0, 0);
    return instance;
  }

}
