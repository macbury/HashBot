package de.macbury.hashbot.core.graphics.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 25.04.14.
 */
public class FilePickerDialog extends UIDialog {
  private static final float H_PADDING = 10;
  private static final float V_PADDING = 29;
  private static final float MIN_HEIGHT = 320;
  private static final float MIN_WIDTH = 480;
  private static final String TAG = "FilePickerDialog";
  private FileHandle currentDir;
  private ScrollPane listViewScrollPane;
  private List filesListView;

  public FilePickerDialog(FileHandle baseDir, WindowStyle style) {
    super("Open file", style);

    this.filesListView      = HashBot.ui.list();
    this.listViewScrollPane = HashBot.ui.scrollPane(filesListView);
    listViewScrollPane.setFadeScrollBars(false);

    filesListView.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (getTapCount() > 1)
          actWithSelection();
      }
    });

    openDirectory(baseDir);

    Table contentTable = getContentTable();
    contentTable.row(); {
      contentTable.add(listViewScrollPane).height(MIN_HEIGHT).width(MIN_WIDTH).fillX().pad(V_PADDING, H_PADDING, V_PADDING, H_PADDING).expand();
    }
    
    button(HashBot.ui.textI18nButton("file_picker.save"), true);
    button(HashBot.ui.textI18nButton("file_picker.cancel"), null);
  }

  private void actWithSelection() {
    FileHandle targetHandle = Gdx.files.absolute(currentDir.file().getAbsolutePath() + "/" + (String) filesListView.getSelected());
    if (targetHandle.isDirectory()) {
      openDirectory(targetHandle);
    } else {
      Gdx.app.log(TAG, "FILE: " + targetHandle.path());
    } 
  }

  private void openDirectory(FileHandle nextDir) {
    Gdx.app.log(TAG, "Opening: " + nextDir.path());
    this.currentDir = nextDir;
    Array items             = new Array();
    items.add("..");
    for(FileHandle handle : currentDir.list()) {
      if (handle.isDirectory()) {
        items.add(handle.name()+"/");
      } else {
        items.add(handle.name());
      }
    }
    filesListView.setItems(items);
  }
}
