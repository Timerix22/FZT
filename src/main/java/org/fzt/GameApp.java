package org.fzt;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.fzt.entities.Entities;
import org.fzt.entities.player.PlayerController;

/**
 * Main class with entry point and game logic
 */
public class GameApp extends GameApplication {
    public static final String version = "0.0.1";

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Forgotten Ziggurat of Tutel");
        settings.setVersion(version);
        settings.setWidth(1600);
        settings.setHeight(900);
        settings.setScaleAffectedOnResize(false);
        settings.setManualResizeEnabled(true);
        settings.setFullScreenAllowed(true);
        settings.setDefaultCursor(new CursorInfo("breeze-cursor-32.png", 10, 5));
        settings.setIntroEnabled(false);

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
        settings.setProfilingEnabled(true);
    }

    @Override
    protected void initInput() {

        FXGL.onKey (KeyCode.A, () -> {
            playerController.moveLeft();
        });
        FXGL.onKey(KeyCode.D, () -> {
            playerController.moveRight();
        });
        FXGL.onKey(KeyCode.W, () -> {
            playerController.moveUp();
        });
        FXGL.onKey(KeyCode.S, () -> {
            playerController.moveDown();
        });

    }

    private Entity player;
    private PlayerController playerController;

    @Override
    protected void initGame() {
        var world = FXGL.getGameWorld();
        var scene = FXGL.getGameScene();
        var viewport = scene.getViewport();

        scene.setBackgroundColor(Color.BLACK);

        player = Entities.spawnPlayer(new Point2D(0, 0));
        playerController = player.getComponent(PlayerController.class);

        viewport.bindToEntity(player, viewport.getWidth()/2, viewport.getHeight()/2);

        for(int x=0; x<viewport.getWidth(); x+=128){
            Entities.spawnWall(new Point2D(x, 200), "wall.png");
        }
    }

    @Override
    protected void initPhysics() {
        var physics = FXGL.getPhysicsWorld();
        physics.setGravity(0, 0);
    }
    @Override
    protected void initUI() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}