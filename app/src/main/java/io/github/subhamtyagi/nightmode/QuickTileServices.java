package io.github.subhamtyagi.nightmode;

import android.annotation.TargetApi;
import android.app.UiModeManager;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.N)
public class QuickTileServices extends TileService {
    private static final String TAG = "DarkModeQuickTile";

    @Override
    public void onClick() {
        super.onClick();
        toggleSetting();
        syncTile();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        syncTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        if (BuildConfig.DEBUG) Log.d(TAG, "onStartListening");
        syncTile();
    }

    private boolean isNightModeOn(UiModeManager uiModeManager) {
        final boolean isNightMode = uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES;
        if (BuildConfig.DEBUG) Log.d(TAG, "isNightModeOn " + isNightMode);
        return isNightMode;
    }

    private void toggleSetting() {
        final UiModeManager uiModeManager = getSystemService(UiModeManager.class);
        if (isNightModeOn(uiModeManager)) {
            //switch off the night mode
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
            if (BuildConfig.DEBUG) Log.d(TAG, "toggleSetting nightMode = off");
        } else {
            // enable night mode
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
            if (BuildConfig.DEBUG) Log.d(TAG, "toggleSetting nightMode = on");
        }
    }

    private void syncTile() {
        final UiModeManager uiModeManager = getSystemService(UiModeManager.class);
        final Tile tile = getQsTile();
        tile.setState(isNightModeOn(uiModeManager) ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
        if (BuildConfig.DEBUG) Log.d(TAG, "syncTile");
    }


}
