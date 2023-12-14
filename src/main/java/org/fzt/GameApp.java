package org.fzt;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.fzt.entities.Entities;
import org.fzt.entities.EntityType;
import org.fzt.entities.buildings.Wall;
import org.fzt.entities.items.ItemGenerator;
import org.fzt.entities.items.weapons.Projectile;
import org.fzt.entities.npc.BatMob;
import org.fzt.entities.npc.NPC;
import org.fzt.entities.npc.RatkinMob;
import org.fzt.entities.npc.SlimeMob;
import org.fzt.entities.player.Player;
import org.fzt.level.LevelBuilder;
import org.fzt.level.SpawnerComponent;

/**
 * Main class with entry point and game logic
 */
public class GameApp extends GameApplication {
    public static final String version = "0.2.0";
    public static final ItemGenerator itemGenerator = new ItemGenerator();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Forgotten Ziggurat of Tutel");
        settings.setAppIcon("tutel.png");
        settings.setVersion(version);
        settings.setWidth(1600);
        settings.setHeight(900);
        settings.setFullScreenAllowed(true);
        settings.setDefaultCursor(new CursorInfo("breeze-cursor-32.png", 10, 5));
        settings.setIntroEnabled(false);

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
        settings.setProfilingEnabled(true);
    }

    private Player player;

    @Override
    protected void initGame() {
        var scene = FXGL.getGameScene();
        var viewport = scene.getViewport();

        player = (Player) Entities.spawnPlayer(new Point2D(0, 0));
        viewport.bindToEntity(player, viewport.getWidth() / 2, viewport.getHeight() / 2);

        final int SCENE_SIZE_TILES = 40;
        final int SCENE_HALFSIZE_PX = SCENE_SIZE_TILES * 64 / 2;
        viewport.setBounds(-SCENE_HALFSIZE_PX, -SCENE_HALFSIZE_PX, SCENE_HALFSIZE_PX, SCENE_HALFSIZE_PX);

        var levelBuilder = new LevelBuilder();
        // floor
        scene.setBackgroundColor(Color.BLACK);
        for (int x = -SCENE_HALFSIZE_PX; x < SCENE_HALFSIZE_PX; x += 512)
            for (int y = -SCENE_HALFSIZE_PX; y < SCENE_HALFSIZE_PX; y += 512)
                Entities.spawnFloor(new Point2D(x, y), "floor-512.png");
        // top wall
        levelBuilder.lineX(()-> new Wall(Assets.loadTexture64("wall.jpg")),
                -SCENE_HALFSIZE_PX, -SCENE_HALFSIZE_PX, SCENE_SIZE_TILES);
        // bottom wall
        levelBuilder.lineX(()-> new Wall(Assets.loadTexture64("wall.jpg")),
                -SCENE_HALFSIZE_PX, SCENE_HALFSIZE_PX-64, SCENE_SIZE_TILES);
        // left wall
        levelBuilder.lineY(()-> new Wall(Assets.loadTexture64("wall.jpg")),
                -SCENE_HALFSIZE_PX, -SCENE_HALFSIZE_PX+64, SCENE_SIZE_TILES-2);
        // right wall
        levelBuilder.lineY(()-> new Wall(Assets.loadTexture64("wall.jpg")),
                SCENE_HALFSIZE_PX-64, -SCENE_HALFSIZE_PX+64, SCENE_SIZE_TILES-2);

        // columns
        Point2D[] columnPoints = {
                new Point2D(-768, -256),
                new Point2D(-256, -256),
                new Point2D(256, -256),
                new Point2D(768, -256),
                new Point2D(-768, 256),
                new Point2D(-256, 256),
                new Point2D(256, 256),
                new Point2D(768, 256),
        };
        for(Point2D pos : columnPoints)
            Entities.spawnWall(pos, Assets. loadTexture("column.png", 64, 128));

        // spawners
        FXGL.entityBuilder()
                .at(new Point2D(512, -512))
                .with(new SpawnerComponent(4, 400, 1,
                        (Point2D pos) -> Entities.spawnEntity(pos, new RatkinMob())))
                .buildAndAttach();
        FXGL.entityBuilder()
                .at(new Point2D(-512, 512))
                .with(new SpawnerComponent(4, 400, 1,
                        (Point2D pos) -> Entities.spawnEntity(pos, new SlimeMob())))
                .buildAndAttach();
        FXGL.entityBuilder()
                .at(new Point2D(512, 512))
                .with(new SpawnerComponent(4, 400, 1,
                        (Point2D pos) -> Entities.spawnEntity(pos, new BatMob())))
                .buildAndAttach();
    }

    @Override
    protected void initPhysics() {
        var physics = FXGL.getPhysicsWorld();
        physics.setGravity(0, 0);
        physics.addCollisionHandler(new CollisionHandler(EntityType.PROJECTILE, EntityType.WALL) {
            @Override
            protected void onCollisionBegin(Entity _prj, Entity _wall) {
                _prj.removeFromWorld();
            }
        });
        physics.addCollisionHandler(new CollisionHandler(EntityType.PROJECTILE, EntityType.NPC) {
            @Override
            protected void onCollisionBegin(Entity _prj, Entity _npc) {
                Projectile prj = (Projectile) _prj;
                NPC npc = (NPC) _npc;
                npc.dealDamage(prj.getBaseDamage());
                prj.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {
//        var container = new FlowPane(Orientation.VERTICAL);
//        container.setTranslateX(400);
//        container.setTranslateY(400);
//        container.setBackground(new BackgroundFill(Color.rgb(30, 30, 50), 0, Insets.EMPTY));
//
//        FXGL.addUINode(container);
    }

    public static void main(String[] args) {
        launch(args);
    }
}