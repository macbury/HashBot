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
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.debug.LineBoxComponent;
import de.macbury.hashbot.core.game_objects.components.level.CursorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LevelEditorComponent;
import de.macbury.hashbot.core.level.map.Chunk;
import de.macbury.hashbot.core.ui.GameUIOverlay;
import de.macbury.hashbot.core.level.editor.LevelEditor;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.partition.QuadTreeObject;
import de.macbury.hashbot.core.ui.editor.LevelEditorSystemListener;

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
  private ArrayList<Chunk> tempObjects = new ArrayList<Chunk>();
  @Mapper
  ComponentMapper<CursorComponent> cm;
  @Mapper
  ComponentMapper<ActorComponent> am;
  @Mapper
  ComponentMapper<LineBoxComponent> lbm;
  private GameUIOverlay overlay;
  private LevelEditorSystemListener listener;
  public LevelEditorSystem(LevelEditor levelEditor) {
    super(Aspect.getAspectForOne(LevelEditorComponent.class));
    this.level = levelEditor;
    this.camera = level.getCameraController().getCamera();
  }

  public void setListener(LevelEditorSystemListener listener) {
    this.listener = listener;
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
      if (level.getTerrain().intersect(level.getTree(),tempObjects, pickupRay, tempVector)) {
        cursorComponent.endPositon.set(tempVector);
        cursorComponent.snapPosition();
      }
    }

    actorComponent.setVisible(overlay.focused());

    Block block = level.getTerrain().getBlock((int)cursorComponent.endPositon.x, (int)cursorComponent.endPositon.z);

    Vector3 minDim = listener.levelEditorCursorMinimalDimension(this);
    if (block != null) {
      cursorComponent.endPositon.y = block.getHeight();
    }

    if (cursorComponent.selection) {
      lineBoxComponent.setColor(Color.WHITE);

      actorComponent.size.set(Math.max(minDim.x, cursorComponent.getWidth()),Math.max(minDim.y, 0.1f),Math.max(minDim.z, cursorComponent.getHeight()));
      actorComponent.position.set(cursorComponent.getBottomRightPosition());
    } else {
      lineBoxComponent.setColor(Color.GRAY);
      actorComponent.size.set(minDim);
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
        LevelEditorSystem.this.listener.levelEditorSystemCursorMove(LevelEditorSystem.this);
        return false;
      }

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == 0) {
          getCursorComponent().startSelection();
          LevelEditorSystem.this.listener.levelEditorSystemStartSelection(LevelEditorSystem.this);
          return true;
        } else {
          return false;
        }
      }

      @Override
      public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (getCursorComponent().selection) {
          LevelEditorSystem.this.updateCursor(LevelEditorSystem.this.level.getCursor(), true);
          LevelEditorSystem.this.listener.levelEditorSystemCursorMove(LevelEditorSystem.this);
        }
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (button == 0) {
          LevelEditorSystem.this.listener.levelEditorSystemEndSelectionOrClick(LevelEditorSystem.this);
          getCursorComponent().endSelection();
        }
      }

    });
  }

  public BoundingBox getSelectionBoundingBox() {
    return getActorComponent().getBoundingBox();
  }
}
