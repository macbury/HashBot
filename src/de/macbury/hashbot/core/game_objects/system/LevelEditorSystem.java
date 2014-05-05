package de.macbury.hashbot.core.game_objects.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.debug.LineBoxComponent;
import de.macbury.hashbot.core.game_objects.components.level.CursorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LevelEditorComponent;
import de.macbury.hashbot.core.graphics.ui.GameUIOverlay;
import de.macbury.hashbot.core.level.LevelEditor;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.partition.QuadTreeObject;

import java.util.ArrayList;

/**
 * Created by macbury on 03.05.14.
 */
public class LevelEditorSystem extends EntitySystem {
  private static final String TAG = "LevelEditorSystem";
  private Label debugLabel;
  private PerspectiveCamera camera;
  private LevelEditor level;
  private Vector3 tempVector                    = new Vector3();
  private ArrayList<QuadTreeObject> tempObjects = new ArrayList<QuadTreeObject>();
  @Mapper
  ComponentMapper<CursorComponent> cm;
  @Mapper
  ComponentMapper<ActorComponent> am;
  @Mapper
  ComponentMapper<LineBoxComponent> lbm;
  private GameUIOverlay overlay;

  public LevelEditorSystem(LevelEditor levelEditor) {
    super(Aspect.getAspectForOne(LevelEditorComponent.class));
    this.level = levelEditor;
    this.camera = level.getCameraController().getCamera();
  }

  @Override
  protected void initialize() {
    super.initialize();
    this.debugLabel = HashBot.ui.label("");
    this.debugLabel.setPosition(50, 50);

    level.getUiLayout().getStage().addActor(debugLabel);
  }

  @Override
  protected void processEntities(ImmutableBag<Entity> entities) {
    for(int i = 0; i < entities.size(); i++) {
      Entity e = entities.get(i);
      if (cm.has(e)) {
        updateCursor(e, false);
      }
    }

  }

  private void updateCursor(Entity e, boolean updateCursorPos) {
    CursorComponent cursorComponent   = cm.get(e);
    ActorComponent  actorComponent    = am.get(e);
    LineBoxComponent lineBoxComponent = lbm.get(e);

    if (updateCursorPos) {
      Ray pickupRay = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());
      if (level.getTree().intersect(tempObjects, pickupRay, tempVector)) {
        cursorComponent.endPositon.set(tempVector).y = Block.BLOCK_HEIGHT;
        cursorComponent.snapPosition();
      }
    }

    actorComponent.setVisible(overlay.focused());

    if (cursorComponent.selection) {
      lineBoxComponent.setColor(Color.WHITE);
      actorComponent.size.set(cursorComponent.getWidth(),Block.BLOCK_HEIGHT,cursorComponent.getHeight());
      actorComponent.position.set(cursorComponent.getBottomRightPosition());
    } else {
      lineBoxComponent.setColor(Color.GRAY);
      actorComponent.size.set(Block.BLOCK_SIZE,Block.BLOCK_HEIGHT,Block.BLOCK_SIZE);
      actorComponent.position.set(cursorComponent.endPositon);
    }

  }

  public CursorComponent getCursorComponent() {
    return cm.get(level.getCursor());
  }

  public ActorComponent getActorComponent() {
    return am.get(level.getCursor());
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }

  public void setOverlay(GameUIOverlay overlay) {
    this.overlay = overlay;
    this.overlay.addCaptureListener(new InputListener() {

      @Override
      public boolean mouseMoved(InputEvent event, float x, float y) {
        LevelEditorSystem.this.updateCursor(LevelEditorSystem.this.level.getCursor(), true);
        return false;
      }

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == 0) {
          getCursorComponent().startSelection();
          return true;
        } else {
          return false;
        }
      }

      @Override
      public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (getCursorComponent().selection) {
          LevelEditorSystem.this.updateCursor(LevelEditorSystem.this.level.getCursor(), true);
        }
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (button == 0) {
          getCursorComponent().endSelection();
        }
      }

    });
  }
}
