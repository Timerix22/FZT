package org.fzt;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.fzt.entities.Entities;
import org.fzt.entities.EntityType;
import org.fzt.entities.items.weapons.Projectile;
import org.fzt.entities.npc.AIComponent;
import org.fzt.entities.npc.Mob;
import org.fzt.entities.npc.NPC;
import org.fzt.entities.player.Player;
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
        settings.setWidth(800);
        settings.setHeight(600);
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
    }

    private Player player;
    private PlayerController playerController;

    @Override
    protected void initGame() {
        var world = FXGL.getGameWorld();
        var scene = FXGL.getGameScene();
        var viewport = scene.getViewport();

        scene.setBackgroundColor(Color.BLACK);

        player = (Player) Entities.spawnPlayer(new Point2D(0, 0));
        playerController = player.getComponent(PlayerController.class);

        viewport.bindToEntity(player, viewport.getWidth()/2, viewport.getHeight()/2);

        for(int x=0; x<viewport.getWidth(); x+=128){
            Entities.spawnWall(new Point2D(x, 200), "wall.png");
        }
        Entities.spawnEntity(new Point2D(-40, 40), new Mob("tutel.png", new HitBox(BoundingShape.box(64, 64))) {
            @Override
            public float getMaxHP() { return 200; }

            @Override
            public AIComponent getAI() {
                return new AIComponent() {
                    @Override
                    public void onUpdate(double tpf) {
                        var physics = getPhysics();
                    }
                };
            }
        });
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
                npc.applyDamage(prj.getBaseDamage());
                prj.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}