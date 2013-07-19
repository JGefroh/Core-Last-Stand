package com.jgefroh.main;

import java.util.logging.Level;

import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.infopacks.AIInfoPackFactory;
import com.jgefroh.infopacks.AnimationInfoPackFactory;
import com.jgefroh.infopacks.CollisionInfoPackFactory;
import com.jgefroh.infopacks.DamageInfoPackFactory;
import com.jgefroh.infopacks.ForceInfoPackFactory;
import com.jgefroh.infopacks.GUIInfoPackFactory;
import com.jgefroh.infopacks.HealthBarInfoPackFactory;
import com.jgefroh.infopacks.HealthInfoPackFactory;
import com.jgefroh.infopacks.InputInfoPackFactory;
import com.jgefroh.infopacks.KeepInBoundsInfoPackFactory;
import com.jgefroh.infopacks.MaxRangeInfoPackFactory;
import com.jgefroh.infopacks.MouseTrackInfoPackFactory;
import com.jgefroh.infopacks.MovementInfoPackFactory;
import com.jgefroh.infopacks.OutOfBoundsInfoPackFactory;
import com.jgefroh.infopacks.RenderInfoPackFactory;
import com.jgefroh.infopacks.ShieldInfoPackFactory;
import com.jgefroh.infopacks.TargetInfoPackFactory;
import com.jgefroh.infopacks.TargetTrackInfoPackFactory;
import com.jgefroh.infopacks.WeaponInfoPackFactory;
import com.jgefroh.input.InputSystem;
import com.jgefroh.systems.AISystem;
import com.jgefroh.systems.CollisionSystem;
import com.jgefroh.systems.DamageSystem;
import com.jgefroh.systems.EnemySpawnSystem;
import com.jgefroh.systems.EntityCreationSystem;
import com.jgefroh.systems.ForceSystem;
import com.jgefroh.systems.GUIHealthBarSystem;
import com.jgefroh.systems.GUISystem;
import com.jgefroh.systems.HealthMonitorSystem;
import com.jgefroh.systems.KeepInBoundsSystem;
import com.jgefroh.systems.MaxRangeSystem;
import com.jgefroh.systems.OutOfBoundsSystem;
import com.jgefroh.systems.RenderSystem;
import com.jgefroh.systems.ResourceLoader;
import com.jgefroh.systems.ShieldSystem;
import com.jgefroh.systems.TargetTrackSystem;
import com.jgefroh.systems.TransformSystem;
import com.jgefroh.systems.WeaponSystem;
import com.jgefroh.systems.WindowSystem;


/**
 * @author Joseph Gefroh
 */
public class Main
{
	/**A reference to the Core that powers the game.*/
	private Core core;
	
	/***/
	private boolean continueGame = true;		//Continue
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		System.out.println("Starting Last Stand");
		Main ts = new Main();
		ts.loop();
		System.out.println("END");
		System.exit(0);
	}
	
	public Main()
	{
		init();
	}
	

	private void init()
	{
		core = new Core();
		initFactories();
		initSystems();
		loadTexture();
	}
	
	/**
	 * Initialize all of the various systems of the game.
	 */
	private void initSystems()
	{
		core.setDebugLevel(Level.INFO);
		core.addSystem(new WindowSystem(core, 1366, 768, "Last Stand"), true);
		core.addSystem(new RenderSystem(core), true);
		core.addSystem(new ForceSystem(core));
		TransformSystem tranSys = new TransformSystem(core);
		tranSys.setWait(30);
		core.addSystem(tranSys);
		core.addSystem(new InputSystem(core), true);
		
		core.addSystem(new CollisionSystem(core));
		core.addSystem(new EntityCreationSystem(core), true);
		
		//core.addSystem(new MouseTrackingSystem(core));
		core.addSystem(new WeaponSystem(core));
		MaxRangeSystem mrs = new MaxRangeSystem(core);
			mrs.setWait(100);
		core.addSystem(mrs);
		core.addSystem(new DamageSystem(core));
		core.addSystem(new HealthMonitorSystem(core));
		core.addSystem(new GUIHealthBarSystem(core));
		AISystem aiSys = new AISystem(core);
			//aiSys.setWait(1000);
		core.addSystem(aiSys);
		core.addSystem(new TargetTrackSystem(core));
		OutOfBoundsSystem oobSys = new OutOfBoundsSystem(core);
		core.addSystem(oobSys);
		EnemySpawnSystem eSpawn = new EnemySpawnSystem(core);
		eSpawn.setWait(30000);
		eSpawn.setLast(core.now()-31000);
		core.addSystem(eSpawn); 
		core.addSystem(new ShieldSystem(core));
		core.addSystem(new KeepInBoundsSystem(core));
		core.addSystem(new GUISystem(core));
		rl = new ResourceLoader(core);
	}
	
	private void initFactories()
	{
		core.addFactory(new RenderInfoPackFactory());
		core.addFactory(new MovementInfoPackFactory());
		core.addFactory(new InputInfoPackFactory());
		core.addFactory(new CollisionInfoPackFactory());
		core.addFactory(new ForceInfoPackFactory());
		core.addFactory(new WeaponInfoPackFactory());
		core.addFactory(new AnimationInfoPackFactory());
		core.addFactory(new MouseTrackInfoPackFactory());
		core.addFactory(new MaxRangeInfoPackFactory());
		core.addFactory(new HealthInfoPackFactory());
		core.addFactory(new HealthBarInfoPackFactory());
		core.addFactory(new DamageInfoPackFactory());
		core.addFactory(new AIInfoPackFactory());
		core.addFactory(new TargetInfoPackFactory());
		core.addFactory(new TargetTrackInfoPackFactory());
		core.addFactory(new OutOfBoundsInfoPackFactory());
		core.addFactory(new ShieldInfoPackFactory());
		core.addFactory(new KeepInBoundsInfoPackFactory());
		core.addFactory(new GUIInfoPackFactory());
	}
	
	/**
	 * Loop through game functions (main game loop).
	 */
	public void loop()
	{
		newGame();
		while(!Display.isCloseRequested())
		{
			Display.update();
			core.work();
			Display.sync(240);
		}
	}
	
	////////////
	public void newGame()
	{

		EntityCreationSystem ecs = 
				core.getSystem(EntityCreationSystem.class);
		ecs.createPlayer(32, 384);

		//ecs.createEnemy1_0(128, 128);
	/*	
	 * 
		ecs.createFormation(0, 100);	
		ecs.createFormation(1, 500);
		ecs.createFormation(2, 900);
		ecs.createFormation(3, 1300);
		ecs.createFormation(4, 1700);
	*/

		core.getSystem(CollisionSystem.class).setCollision(1, 2, true);	//alien, pbullet
		core.getSystem(CollisionSystem.class).setCollision(0, 3, true);	//player, abullet
	}
	
	public void loadTexture()
	{
		rl.loadTexture("res/player.png");
		rl.loadTexture("res/bullet.png");
		rl.loadTexture("res/enemy.png");
		rl.loadTexture("res/bg.png");
		rl.loadTexture("res/fx.png");
		rl.loadTexture("res/alphabet.png");
	}
}
