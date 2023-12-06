package org.fzt;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Point2D;
import javafx.scene.text.Text;
import org.fzt.entities.Entities;
import org.fzt.entities.EntityType;
import org.fzt.entities.items.ItemGenerator;
import org.fzt.entities.items.weapons.Projectile;
import org.fzt.entities.npc.NPC;
import org.fzt.entities.npc.RatkinMob;
import org.fzt.entities.player.Player;
import org.fzt.entities.player.PlayerController;
import org.fzt.level.SpawnerComponent;

/**
 * Main class with entry point and game logic
 */
public class GameApp extends GameApplication {
    public static final String version = "0.0.1";
    public static final ItemGenerator itemGenerator = new ItemGenerator();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Forgotten Ziggurat of Tutel");
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
    private PlayerController playerController;

    @Override
    protected void initGame() {
        var world = FXGL.getGameWorld();
        var scene = FXGL.getGameScene();
        var viewport = scene.getViewport();

        player = (Player) Entities.spawnPlayer(new Point2D(0, 0));
        playerController = player.getComponent(PlayerController.class);
        viewport.bindToEntity(player, viewport.getWidth()/2, viewport.getHeight()/2);

        for(int x = -10240; x<10240; x+=512)
            for(int y = -10240; y<10240; y+=512)
                Entities.spawnFloor(new Point2D(x, y), "floor-512.png");
        for(int x=0; x<viewport.getWidth(); x+=128){
            Entities.spawnWall(new Point2D(x, 192), "wall.png");
        }

        FXGL.entityBuilder()
                .at(new Point2D(400, -640))
                .with(new SpawnerComponent(4, 400, 1,
                        (Point2D pos) -> Entities.spawnEntity(pos, new RatkinMob())))
                .buildAndAttach();

        FXGL.entityBuilder()
                .at(new Point2D(-400, 640))
                .with(new SpawnerComponent(4, 400, 1,
                        (Point2D pos) -> Entities.spawnEntity(pos, new RatkinMob())))
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
                Projectile prj = (Projectile)_prj;
                NPC npc = (NPC)_npc;
                npc.dealDamage(prj.getBaseDamage());
                prj.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {
        Text t = new Text("aaa");
        FXGL.addUINode(t);
    }

    public static void main(String[] args) {
        launch(args);
    }
}