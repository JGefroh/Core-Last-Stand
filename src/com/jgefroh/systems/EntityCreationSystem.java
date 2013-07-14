package com.jgefroh.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.DamageComponent;
import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.GUIComponent;
import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.InputComponent;
import com.jgefroh.components.KeepInBoundsComponent;
import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.MouseTrackComponent;
import com.jgefroh.components.OutOfBoundsComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.ShieldComponent;
import com.jgefroh.components.TargetComponent;
import com.jgefroh.components.TargetTrackComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.Entity;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Vector;
import com.jgefroh.data.Weapon;
import com.jgefroh.systems.WeaponSystem.FireMode;



/**
 * This is a temporary system used to create specific entities.
 * The eventual goal is to have this information read from an external file
 * and the entities created from that (like loading a level).
 * @author Joseph Gefroh
 */
public class EntityCreationSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	private double bearing;
	//////////
	// INIT
	//////////
	/**
	 * Create a new EntityCreationSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public EntityCreationSystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		setDebugLevel(this.debugLevel);

		core.setInterested(this, "BEARING_TO_MOUSE");
	}
	
	@Override
	public void start() 
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}
	
	@Override
	public void work(final long now)
	{

	}

	@Override
	public void stop()
	{	
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	@Override
	public long getWait()
	{
		return this.waitTime;
	}

	@Override
	public long	getLast()
	{
		return this.last;
	}
	
	@Override
	public void setWait(final long waitTime)
	{
		this.waitTime = waitTime;
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
	}
	//////////
	// SYSTEM METHODS
	//////////
	public void createBG()
	{
		IEntity bg = new Entity();
		TransformComponent tc = new TransformComponent(bg);
			tc.setXPos(1366/2);
			tc.setYPos(768/2);
			tc.setWidth(768);
			tc.setHeight(1366);
			tc.setBearing(90);
			bg.add(tc);
		RenderComponent rc = new RenderComponent(bg);
			rc.setSpriteID(0);
			rc.setTexturePath("res/bg.png");
			bg.add(rc);
		core.add(bg);
	}
	/**
	 * Create a player entity.
	 * @param x	the X Position to create the entity
	 * @param y	the Y Position to create the entity
	 */
	public void createPlayer(final int x, final int y)
	{	
		IEntity player = new Entity();
		player.setName("player");
		
		TransformComponent tc = new TransformComponent(player);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			player.add(tc);
		
		RenderComponent rc = new RenderComponent(player);
			rc.setSpriteID(0);
			rc.setTexturePath("res/player.png");
			player.add(rc);
		
		VelocityComponent vc = new VelocityComponent(player);
			vc.setInterval(4);
			vc.setContinuous(false);
			player.add(vc);
			
		InputComponent ic = new InputComponent(player);
			ic.setInterested("MOVE_UP");	
			ic.setInterested("MOVE_DOWN");	
			ic.setInterested("MOVE_LEFT");	
			ic.setInterested("MOVE_RIGHT");	
			ic.setInterested("+FIRE1");	
			ic.setInterested("-FIRE1");	
			ic.setInterested("+SHIELD");
			ic.setInterested("-SHIELD");
			ic.setInterested("SWITCH_WEAPON_1");
			ic.setInterested("SWITCH_WEAPON_2");
			ic.setInterested("SWITCH_WEAPON_3");
			ic.setInterested("SWITCH_WEAPON_4");
			ic.setInterested("SWITCH_WEAPON_5");
			ic.setInterested("SWITCH_WEAPON_6");
			ic.setInterested("SWITCH_WEAPON_7");
			ic.setInterested("SWITCH_WEAPON_8");
			ic.setInterested("SWITCH_WEAPON_9");
			ic.setInterested("SWITCH_WEAPON_0");
			player.add(ic);
		
		CollisionComponent cc = new CollisionComponent(player);
			cc.setCollisionGroup(0);
			player.add(cc);
		
		ForceGeneratorComponent fgc = new ForceGeneratorComponent(player);
			Vector v = new Vector();
			fgc.setMagnitude(5);	//Movement speed
			fgc.setVector(v);
			player.add(fgc);
		
		WeaponComponent wc = new WeaponComponent(player);
			Weapon weapon = new Weapon();
			weapon.setConsecutiveShotDelay(300);
			weapon.setFireMode(FireMode.AUTO.ordinal());
			weapon.setName("gun1");
			weapon.setDamage(10);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(3);
			weapon.setBurstDelay(500);
			weapon.setShotType(0);
		
			wc.addWeapon(weapon);

			wc.setCurrentWeapon("gun1");
			
			
			player.add(wc);
			
		MouseTrackComponent mc = new MouseTrackComponent(player);
			player.add(mc);
		
		HealthComponent hc = new HealthComponent(player);
			hc.setCurHealth(100);
			player.add(hc);
			
		TargetComponent tarc = new TargetComponent(player);
			player.add(tarc);
		
		ShieldComponent sc = new ShieldComponent(player);
			sc.setShieldCur(100);
			sc.setShieldInc(2);
			sc.setShieldDec(2);
			sc.setShieldRechargeInterval(200);
			sc.setShieldRechargeDelay(1000);
			sc.setShieldMax(100);
			sc.setShieldMin(30);
			sc.setShieldDrainInterval(200);
			player.add(sc);
			
		KeepInBoundsComponent kibc = new KeepInBoundsComponent();
			player.add(kibc);
		core.add(player);
		
		core.send("PLAYER_CREATED", player.getID());
	}
	
	public void createEnemy0_0(final int x, final int y)
	{
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(180);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(0);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(30);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);

		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(800);
			weapon.setDamage(15);
			weapon.setMaxRange(1500);
			weapon.setShotType(1);

			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			

		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			

		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			vc.setContinuous(true);
			enemy.add(vc);
		

			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			ai.setInRangeOfTarget(true);
			enemy.add(ai);
			
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
			enemy.add(oobc);
		core.add(enemy);
	}
	
	public void createEnemy1_0(final int x, final int y)
	{	
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(90);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(1);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(30);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
	
		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(800);
			weapon.setDamage(15);
			weapon.setMaxRange(1500);
			weapon.setShotType(1);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			
	
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
		TargetTrackComponent ttc = new TargetTrackComponent(enemy);
			ttc.setTargetRange(1500);
			ttc.setTurnSpeed(2.5);
			ttc.setTurnInterval(50);
			enemy.add(ttc);
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
			
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
			enemy.add(oobc);
		core.add(enemy);
	}


	public void createEnemy2_0(final int x, final int y)
	{	
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(180);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(5);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(20);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
	
		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(100);
			weapon.setDamage(3);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(3);
			weapon.setBurstDelay(1000);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setShotType(2);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			
	
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);

		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			ai.setInRangeOfTarget(true);
			enemy.add(ai);
		
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
		enemy.add(oobc);
		core.add(enemy);
	}

	public void createEnemy3_0(final int x, final int y)
	{	
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(180);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(6);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(20);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
	
		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(100);
			weapon.setDamage(3);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(3);
			weapon.setBurstDelay(1000);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setShotType(2);

			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			
	
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);

		TargetTrackComponent ttc = new TargetTrackComponent(enemy);
			ttc.setTargetRange(1500);
			enemy.add(ttc);
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
			
			OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
			enemy.add(oobc);
			
		core.add(enemy);
	}


	public void createEnemy2_1(final int x, final int y)
	{	
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(90);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(7);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(30);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
	
		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(1500);
			weapon.setDamage(2);
			weapon.setMaxRange(1500);
			weapon.setRecoilCur(90);
			weapon.setShotType(2);
			weapon.setNumShots(5);
	
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
		
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
		TargetTrackComponent ttc = new TargetTrackComponent(enemy);
			ttc.setTargetRange(500);
			enemy.add(ttc);
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
			
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
			enemy.add(oobc);	
		core.add(enemy);
	}


	public void createEnemy1_1(final int x, final int y)
	{	
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(90);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(3);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(30);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
	
		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(200);
			weapon.setDamage(2);
			weapon.setMaxRange(1500);
			weapon.setRecoilCur(360);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setBurstDelay(3000);
			weapon.setBurstSize(4);
			weapon.setNumShots(1);
			weapon.setShotType(3);
	
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
		
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
		TargetTrackComponent ttc = new TargetTrackComponent(enemy);
			ttc.setTargetRange(1500);
			enemy.add(ttc);
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
			
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
			enemy.add(oobc);	
		core.add(enemy);
	}
	public void createEnemy0_2(final int x, final int y)
	{	
		IEntity enemy = new Entity();
		enemy.setName("ENEMY");
		
		TransformComponent tc = new TransformComponent(enemy);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(-90);
			enemy.add(tc);
		
		RenderComponent rc = new RenderComponent(enemy);
			rc.setSpriteID(4);
			rc.setTexturePath("res/enemy.png");
			enemy.add(rc);
		
		CollisionComponent cc = new CollisionComponent(enemy);
			cc.setCollisionGroup(1);
			enemy.add(cc);
		
		HealthComponent hc = new HealthComponent(enemy);
			hc.setCurHealth(100);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
		
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
			
		AIComponent ai = new AIComponent(enemy);
			enemy.add(ai);
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(enemy);
			enemy.add(oobc);	
		core.add(enemy);
	}


	public void createFormation(final int formation, final int xOffset)
	{
		int x = 1366+xOffset;
		int y = 0;
		
		switch(formation)
		{
			case 0:
				createEnemy0_0(x+100, y+128);
				createEnemy0_0(x+100, y+256);
				createEnemy0_0(x+100, y+384);
				createEnemy0_0(x+100, y+512);
				createEnemy0_0(x+100, y+640);
				break;
			case 1:
				createEnemy1_0(x+100, y+128);
				createEnemy0_0(x+100, y+256);
				createEnemy0_0(x+100, y+384);
				createEnemy0_0(x+100, y+512);
				createEnemy1_0(x+100, y+640);
				break;
			case 2:
				createEnemy1_0(x+100, y+128);
				createEnemy0_2(x+50, y+128);
				createEnemy0_0(x+100, y+256);
				createEnemy2_1(x+100, y+384);
				createEnemy0_2(x+50, y+384);
				createEnemy0_0(x+100, y+512);
				createEnemy1_0(x+100, y+640);
				createEnemy0_2(x+50, y+640);
				break;
			case 3:
				createEnemy3_0(x+100, y+128);
				createEnemy0_2(x+50, y+128);
				createEnemy1_0(x+100, y+256);
				createEnemy2_1(x+100, y+384);
				createEnemy0_2(x+50, y+384);
				createEnemy1_0(x+100, y+512);
				createEnemy3_0(x+100, y+640);
				createEnemy0_2(x+50, y+640);
				break;
			case 4:
				createEnemy2_0(x+100, y+128);
				createEnemy2_0(x+50, y+128);
				createEnemy2_0(x+100, y+192);
				createEnemy2_0(x+100, y+256);
				createEnemy2_0(x+100, y+384);
				createEnemy2_0(x+50, y+384);
				createEnemy2_0(x+100, y+512);
				createEnemy2_0(x+100, y+576);
				createEnemy2_0(x+100, y+640);
				createEnemy2_0(x+50, y+640);
				break;
		}
		

	}

	public void createBullet(final IEntity owner, final int type,
							final int damage, final int maxRange,
							final double recoil)
	{
		TransformComponent otc = owner.getComponent(TransformComponent.class);
		int x = 0;
		int y = 0;
		double angle = Math.atan2(y, x);
		double distance = Math.sqrt(x*x+y*y);
		angle+=otc.getBearing();
		double rotatedX = (Math.sin(angle)*distance);
		double rotatedY = (Math.cos(angle)*distance);
		
//		double ang = owner.getComponent(TransformComponent.class).getBearing();
	//	double mag = owner.getComponent(VelocityComponent.class).getTotalMovementVector().getMagnitude();
		
		
		IEntity bullet = new Entity();
		bullet.setName("BULLET");
		
		TransformComponent tc = new TransformComponent(bullet);
			tc.setXPos(otc.getXPos()+rotatedX);
			tc.setYPos(otc.getYPos()+rotatedY);
			tc.setWidth(8);
			tc.setHeight(10);
			tc.setBearing(otc.getBearing());
			bullet.add(tc);
		

		RenderComponent rc = new RenderComponent(bullet);
			rc.setTexturePath("res/bullet.png");
			bullet.add(rc);

		VelocityComponent vc = new VelocityComponent(bullet);
			vc.setInterval(4);
			


		double recoil1 = Math.random()*(recoil/2);
		double recoil2 = Math.random()*(recoil/2);
		Vector v = new Vector();
			v.setAngle(otc.getBearing()+recoil1-recoil2);
			v.setMagnitude(20);
			vc.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			bullet.add(vc);
		
		CollisionComponent cc = new CollisionComponent(bullet);
			bullet.add(cc);
		
		MaxRangeComponent mc = new MaxRangeComponent(bullet);
			mc.setMaxRange(maxRange);
			mc.setLastXPos(tc.getXPos()+rotatedX);
			mc.setLastYPos(tc.getYPos()+rotatedY);
			bullet.add(mc);
		
		DamageComponent dc = new DamageComponent(bullet);
			dc.setDamage(damage);
			bullet.add(dc);
			
		OutOfBoundsComponent oobc = new OutOfBoundsComponent(bullet);
			bullet.add(oobc);
			
		switch(type)
		{
			case 0:
				tc.setWidth(8);
				tc.setHeight(10);
				rc.setSpriteID(2);
				break;
			case 1:
				tc.setWidth(16);
				tc.setHeight(16);
				rc.setSpriteID(0);
				break;
			case 2:
				tc.setWidth(8);
				tc.setHeight(8);
				rc.setSpriteID(1);
				break;
			case 3: //Missile
				
				TargetTrackComponent ttc = new TargetTrackComponent(bullet);
					ttc.setTargetRange(1500);
					ttc.setTurnInterval(100);
					ttc.setTurnSpeed(6);
				bullet.add(ttc);
				
				//It works...but WHAT AM I DOING HERE?
				ForceGeneratorComponent fgc = new ForceGeneratorComponent(bullet);
					fgc.setContinuous(true);	//WHAT?
					fgc.setRelative(true);		//HUH?
					Vector vector = new Vector();
						vector.setMagnitude(5);	//Does this affect speed?
					fgc.setVector(vector);	
					v.setMaxMagnitude(5);	//OR DOES THIS?
					
					
				MaxRangeComponent mrc = new MaxRangeComponent(bullet);
					mrc.setLastXPos(tc.getXPos());
					mrc.setLastYPos(tc.getYPos());
					mrc.setMaxRange(1500);
				bullet.add(mrc);
				bullet.add(fgc);
				
				tc.setBearing(v.getAngle());	//This affects turning, definitely.
				
				rc.setSpriteID(3);
				break;
				
				//Todo; encapsulate
		}
		
		if(owner.getName().equalsIgnoreCase("PLAYER"))
		{
			cc.setCollisionGroup(2);
		}
		else
		{
			cc.setCollisionGroup(3);
		}
		core.addEntity(bullet);
		
		
	}
	
	public void createQuad(final double x, final double y, final double width, final double height, final double rot)
	{
		
		IEntity bullet = new Entity();
		bullet.setName("QUAD");
		
		TransformComponent tc = new TransformComponent(bullet);
			tc.setXPos(x);
			tc.setYPos(y);
			tc.setWidth(width);
			tc.setHeight(height);
			tc.setBearing(rot);
			bullet.add(tc);
		
		RenderComponent rc = new RenderComponent(bullet);
			rc.setSpriteID(0);
			//rc.setTexturePath("res\\bullet.png");
			bullet.add(rc);
			
		MaxRangeComponent mc = new MaxRangeComponent(bullet);
			mc.setMaxRange(-1);
			mc.setLastXPos(x);
			mc.setLastYPos(y);
			bullet.add(mc);
		core.add(bullet);

	}
	public IEntity createHealthBar(final IEntity owner)
	{
		IEntity healthBar = new Entity();
		healthBar.setName("HEALTHBAR");
		TransformComponent otc = owner.getComponent(TransformComponent.class);
		
		TransformComponent htc = new TransformComponent(healthBar);
			htc.setXPos(otc.getXPos());
			htc.setYPos(otc.getYPos()+15);
			htc.setWidth(64);
			htc.setHeight(5);
			healthBar.add(htc);
			
		RenderComponent rc = new RenderComponent(healthBar);
			rc.setSpriteID(0);
			healthBar.add(rc);
			
		core.add(healthBar);
		return healthBar;
	}
	
	public IEntity createShield(final IEntity owner)
	{
		IEntity shield = new Entity();
		shield.setName("shield");
		TransformComponent otc = owner.getComponent(TransformComponent.class);
		CollisionComponent occ = owner.getComponent(CollisionComponent.class);
		
		TransformComponent stc = new TransformComponent(shield);
			stc.setXPos(otc.getXPos());
			stc.setYPos(otc.getYPos());
			stc.setWidth(128);
			stc.setHeight(128);
			shield.add(stc);
			
		RenderComponent rc = new RenderComponent(shield);
			rc.setSpriteID(0);
			rc.setTexturePath("res/fx.png");
			rc.setRGB(0, 125, 255);
			shield.add(rc);
		
		CollisionComponent cc = new CollisionComponent(shield);
			cc.setCollisionGroup(occ.getCollisionGroup());
			shield.add(cc);
		core.add(shield);
		return shield;
	}
	public void createEnemy(final int xOffset, final int y, final int type)
	{
		int x = 1366 + xOffset;
		switch(type)
		{
		
			case 0:
				createEnemy0_0(x, y);
				break;
			case 1:
				createEnemy1_0(x, y);
				break;
			case 2:
				createEnemy2_0(x, y);
				break;
			case 3:
				createEnemy3_0(x, y);
				break;
			case 4:
				createEnemy1_0(x, y);
				break;
			case 5:
				createEnemy2_1(x, y);
				break;
			case 6:
				createEnemy0_2(x, y);
				break;
			case 7:
				createEnemy1_0(x, y);
				break;
			case 8:
				createEnemy1_1(x, y);
				break;
			case 9:
				createEnemy1_0(x, y);
				break;
			case 10:
				createEnemy1_1(x, y);
				break;
			
		}
	}
	
	public String createGUIBar(final double xPos, final double yPos,
								final double width, final double height,
								final float r, final float g, final float b)
	{
		IEntity entity = new Entity();
		entity.setName("GUI_BAR");
		
		TransformComponent tc = new TransformComponent(entity);
		RenderComponent rc = new RenderComponent(entity);
		GUIComponent gc = new GUIComponent();
		
		tc.setHeight(16);
		tc.setXPos(xPos);
		tc.setYPos(yPos);

		rc.setRGB(r, g, b);
		
		entity.add(tc);
		entity.add(rc);
		entity.add(gc);
		
		core.add(entity);
		
		return entity.getID();
	}

	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}


	
}
