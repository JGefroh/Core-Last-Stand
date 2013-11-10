package com.jgefroh.main;

import java.util.logging.Level;

import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.core.Entity;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.infopacks.AbilityInfoPack;
import com.jgefroh.infopacks.AnimationInfoPack;
import com.jgefroh.infopacks.CollisionInfoPack;
import com.jgefroh.infopacks.DamageInfoPack;
import com.jgefroh.infopacks.DecayInfoPack;
import com.jgefroh.infopacks.ExplosionInfoPack;
import com.jgefroh.infopacks.ForceInfoPack;
import com.jgefroh.infopacks.GUIBarInfoPack;
import com.jgefroh.infopacks.GUICharSlotInfoPack;
import com.jgefroh.infopacks.GUICounterInfoPack;
import com.jgefroh.infopacks.GUIInfoPack;
import com.jgefroh.infopacks.GUITextInfoPack;
import com.jgefroh.infopacks.HealthBarInfoPack;
import com.jgefroh.infopacks.HealthInfoPack;
import com.jgefroh.infopacks.InputInfoPack;
import com.jgefroh.infopacks.KeepInBoundsInfoPack;
import com.jgefroh.infopacks.MaxRangeInfoPack;
import com.jgefroh.infopacks.MouseTrackInfoPack;
import com.jgefroh.infopacks.MovementInfoPack;
import com.jgefroh.infopacks.OutOfBoundsInfoPack;
import com.jgefroh.infopacks.RenderInfoPack;
import com.jgefroh.infopacks.ScoreInfoPack;
import com.jgefroh.infopacks.ShieldInfoPack;
import com.jgefroh.infopacks.TargetInfoPack;
import com.jgefroh.infopacks.TargetTrackInfoPack;
import com.jgefroh.infopacks.WeaponInfoPack;
import com.jgefroh.input.InputSystem;
import com.jgefroh.systems.AISystem;
import com.jgefroh.systems.AbilitySystem;
import com.jgefroh.systems.CollisionSystem;
import com.jgefroh.systems.DamageSystem;
import com.jgefroh.systems.DecaySystem;
import com.jgefroh.systems.EnemySpawnSystem;
import com.jgefroh.systems.EntityCreationSystem;
import com.jgefroh.systems.ExplosionSystem;
import com.jgefroh.systems.ForceSystem;
import com.jgefroh.systems.GUIHealthBarSystem;
import com.jgefroh.systems.GUISystem;
import com.jgefroh.systems.HealthMonitorSystem;
import com.jgefroh.systems.KeepInBoundsSystem;
import com.jgefroh.systems.MaxRangeSystem;
import com.jgefroh.systems.OutOfBoundsSystem;
import com.jgefroh.systems.RenderSystem;
import com.jgefroh.systems.ResourceLoader;
import com.jgefroh.systems.ScoreSystem;
import com.jgefroh.systems.ShieldSystem;
import com.jgefroh.systems.TargetTrackSystem;
import com.jgefroh.systems.TransformSystem;
import com.jgefroh.systems.UpgradeSystem;
import com.jgefroh.systems.WeaponSystem;
import com.jgefroh.systems.WindowSystem;


/**
 * @author Joseph Gefroh
 */
public class Main {

    /**A reference to the Core that powers the game.*/
    private Core core;

    /***/
    private boolean continueGame = true; //Continue
    private ResourceLoader rl;

    public static void main(String[] args) {
        Main ts = new Main();
        ts.loop();
        System.exit(0);
    }

    public Main() {
        init();
    }


    private void init() {
        core = new Core();
        core.setDebugLevel(Level.OFF);
        initFactories();
        initSystems();
        loadTexture();
    }

    /**
     * Initialize all of the various systems of the game.
     */
    private void initSystems() {
        core.addSystem(new WindowSystem(core, 1366, 768, "Last Stand - 29JUL13"));
        core.addSystem(new RenderSystem(core));

        core.addSystem(new ForceSystem(core));
        TransformSystem tranSys = new TransformSystem(core);
        //tranSys.setWait(30);
        core.addSystem(tranSys);
        core.addSystem(new InputSystem(core));

        core.addSystem(new CollisionSystem(core));
        core.addSystem(new EntityCreationSystem(core));

        //core.addSystem(new MouseTrackingSystem(core));
        core.addSystem(new WeaponSystem(core));
        MaxRangeSystem mrs = new MaxRangeSystem(core);
        mrs.setWait(100);
        core.addSystem(mrs);
        core.addSystem(new DamageSystem(core));
        core.addSystem(new HealthMonitorSystem(core));
        core.addSystem(new GUIHealthBarSystem(core));
        AISystem aiSys = new AISystem(core);
        aiSys.setWait(1000);
        core.addSystem(aiSys);
        core.addSystem(new TargetTrackSystem(core));
        OutOfBoundsSystem oobSys = new OutOfBoundsSystem(core);
        core.addSystem(oobSys);
        EnemySpawnSystem eSpawn = new EnemySpawnSystem(core);
        eSpawn.setWait(30000);
        eSpawn.setLast(core.now() - 31000);
        core.addSystem(eSpawn);
        core.addSystem(new ShieldSystem(core));
        core.addSystem(new KeepInBoundsSystem(core));
        core.addSystem(new GUISystem(core));
        core.addSystem(new ScoreSystem(core));
        core.addSystem(new UpgradeSystem(core));
        core.addSystem(new DecaySystem(core));
        core.addSystem(new ExplosionSystem(core));
        core.addSystem(new AbilitySystem(core));
        rl = new ResourceLoader(core);
    }

    private void initFactories() {
        core.add(new RenderInfoPack());
        core.add(new MovementInfoPack());
        core.add(new InputInfoPack());
        core.add(new CollisionInfoPack());
        core.add(new ForceInfoPack());
        core.add(new WeaponInfoPack());
        core.add(new AnimationInfoPack());
        core.add(new MouseTrackInfoPack());
        core.add(new MaxRangeInfoPack());
        core.add(new HealthInfoPack());
        core.add(new HealthBarInfoPack());
        core.add(new DamageInfoPack());
        core.add(new AIInfoPack());
        core.add(new TargetInfoPack());
        core.add(new TargetTrackInfoPack());
        core.add(new OutOfBoundsInfoPack());
        core.add(new ShieldInfoPack());
        core.add(new KeepInBoundsInfoPack());
        core.add(new GUIInfoPack());
        core.add(new GUIBarInfoPack());
        core.add(new GUICharSlotInfoPack());
        core.add(new GUICounterInfoPack());
        core.add(new GUITextInfoPack());
        core.add(new ScoreInfoPack());
        core.add(new DecayInfoPack());
        core.add(new ExplosionInfoPack());
        core.add(new AbilityInfoPack());
    }

    /**
     * Loop through game functions (main game loop).
     */
    public void loop() {
        newGame();
        while (!Display.isCloseRequested()) {
            Display.update();
            core.work();
        }
    }

    ////////////
    public void newGame() {

        EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
        if (ecs != null) {
            ecs.createPlayer(32, 384);
        }
        CollisionSystem cs = core.getSystem(CollisionSystem.class);

        if (cs != null) {
            cs.setCollision(1, 2, true); //alien, pbullet
            cs.setCollision(0, 3, true); //player, abullet
        }
    }

    public void loadTexture() {
        if (rl != null) {
            rl.loadTexture("res/player.png");
            rl.loadTexture("res/bullet.png");
            rl.loadTexture("res/enemy.png");
            rl.loadTexture("res/bg.png");
            rl.loadTexture("res/fx.png");
            rl.loadTexture("res/alphabet.png");
            rl.loadTexture("res/icons.png");
        }
    }
}
