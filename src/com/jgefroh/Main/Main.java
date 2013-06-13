package com.jgefroh.Main;

import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.infopacks.AnimationInfoPackFactory;
import com.jgefroh.infopacks.CollisionInfoPackFactory;
import com.jgefroh.infopacks.DamageInfoPackFactory;
import com.jgefroh.infopacks.ForceInfoPackFactory;
import com.jgefroh.infopacks.HealthBarInfoPackFactory;
import com.jgefroh.infopacks.HealthInfoPackFactory;
import com.jgefroh.infopacks.InputInfoPackFactory;
import com.jgefroh.infopacks.MaxRangeInfoPackFactory;
import com.jgefroh.infopacks.MouseTrackInfoPackFactory;
import com.jgefroh.infopacks.MovementInfoPackFactory;
import com.jgefroh.infopacks.RenderInfoPackFactory;
import com.jgefroh.infopacks.WeaponInfoPackFactory;
import com.jgefroh.input.InputSystem;
import com.jgefroh.systems.CollisionSystem;
import com.jgefroh.systems.DamageSystem;
import com.jgefroh.systems.EntityCreationSystem;
import com.jgefroh.systems.ForceSystem;
import com.jgefroh.systems.GUIHealthBarSystem;
import com.jgefroh.systems.HealthMonitorSystem;
import com.jgefroh.systems.MaxRangeSystem;
import com.jgefroh.systems.MouseTrackingSystem;
import com.jgefroh.systems.RenderSystem;
import com.jgefroh.systems.ResourceLoader;
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
		initSystems();
		initFactories();
		loadTexture();
	}
	
	/**
	 * Initialize all of the various systems of the game.
	 */
	private void initSystems()
	{
		core = new Core();
		core.addSystem(new WindowSystem(core, 1680, 1050, "Last Stand"), true);
		core.addSystem(new RenderSystem(core), true);
		core.addSystem(new ForceSystem(core));
		TransformSystem tranSys = new TransformSystem(core);
		tranSys.setWait(30);
		core.addSystem(tranSys);
		core.addSystem(new InputSystem(core), true);
		
		core.addSystem(new CollisionSystem(core));
		core.addSystem(new EntityCreationSystem(core), true);
		
		core.addSystem(new MouseTrackingSystem(core));
		core.addSystem(new WeaponSystem(core));
		MaxRangeSystem mrs = new MaxRangeSystem(core);
		mrs.setWait(100);
		core.addSystem(mrs);
		core.addSystem(new DamageSystem(core));
		core.addSystem(new HealthMonitorSystem(core));
		core.addSystem(new GUIHealthBarSystem(core));
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
		core.getSystem(EntityCreationSystem.class).createPlayer(800, 950);
		ecs.createAlien(100, 150, 30);
		ecs.createAlien(150, 250, 100);
		ecs.createAlien(200, 350, 59);
		ecs.createAlien(250, 450, 20);
		ecs.createAlien(300, 550, 41);
		ecs.createAlien(350, 650, 5);
		ecs.createAlien(400, 750, 65);
		ecs.createAlien(450, 850, 76);
		
		core.getSystem(CollisionSystem.class).setCollision(1, 2, true);
	}
	
	public void loadTexture()
	{
		rl.loadTexture("res/player.png");
		rl.loadTexture("res/bullet.png");
		rl.loadTexture("res/asteroid.png");
	}
}
