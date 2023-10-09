package org.fzt;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameApp extends GameApplication {
    public static final String version = "0.0.1";

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Forgotten Ziggurat of Tutel");
        settings.setVersion(version);
        settings.setWidth(400);
        settings.setHeight(300);
        settings.setDefaultCursor(new CursorInfo("breeze-cursor-32.png", 10, 5));
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
        });

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
        });
    }

    private Entity player;

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(100, 100)
                .view(FXGL.getAssetLoader().loadTexture("tutel.png", 128, 128))
                .buildAndAttach();
        FXGL.getGameScene().setBackgroundColor(Color.MIDNIGHTBLUE);
    }

    @Override
    protected void initUI() {
        var name = new Text(150, 30, "тутәл");
        name.setFont(new Font(22));
        name.setFill(Color.LIGHTGREEN);
        FXGL.getGameScene().addUINode(name);
        var question = new Text( 90, 240,"зиккурат қайда???");
        question.setFont(new Font(25));
        question.setFill(Color.RED);
        FXGL.getGameScene().addUINode(question);
    }

    public static void main(String[] args) {
        launch(args);
    }
}