package com.jgefroh.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.DamageComponent;
import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.InputComponent;
import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.MouseTrackComponent;
import com.jgefroh.components.RenderComponent;
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
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
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
		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
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
			tc.setWidth(1366);
			tc.setHeight(768);
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
			vc.getTotalMovementVector().setMaxMagnitude(10);
			player.add(vc);
			
		InputComponent ic = new InputComponent(player);
			ic.setInterested("MOVE_UP");	
			ic.setInterested("MOVE_DOWN");	
			ic.setInterested("MOVE_LEFT");	
			ic.setInterested("MOVE_RIGHT");	
			ic.setInterested("FIRE");	
			
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
			fgc.setIncrement(1);
			fgc.setMaxMagnitude(10);
			fgc.setDecrement(0.5);
			fgc.setInterval(200);
			player.add(fgc);
		
		WeaponComponent wc = new WeaponComponent(player);
		
			Weapon weapon = new Weapon();
			weapon.setFiringRate(100);
			weapon.setName("gun1");
			weapon.setAmmo(999999);
			weapon.setDamage(5);
			weapon.setMaxRange(2000);
			weapon.setSlot(1);
			weapon.setMaxSpread(5);
			weapon.setCurSpread(0);
			weapon.setIncSpread(0.25);
			weapon.setNumShots(1);
			weapon.setBurstSize(3);
			weapon.setDelayAfterBurst(900);
			wc.addWeapon(weapon);

			wc.setCurrentWeapon("gun1");
			
			
			player.add(wc);
			
		MouseTrackComponent mc = new MouseTrackComponent(player);
			player.add(mc);
		
		HealthComponent hc = new HealthComponent(player);
			hc.setCurHealth(100);
			player.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(player);
			hbc.setHealthBar(createHealthBar(player));
			player.add(hbc);
			
		TargetComponent tarc = new TargetComponent(player);
			player.add(tarc);
			
		core.add(player);
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
			hc.setCurHealth(20);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);

		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setAmmo(100000);
			weapon.setFiringRate(500);
			weapon.setDamage(10);
			weapon.setMaxRange(2000);
			weapon.setSlot(1);
			weapon.setMaxSpread(0);
			weapon.setCurSpread(0);
			weapon.setIncSpread(0);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			

		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			

		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		

			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
			
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
			hc.setCurHealth(20);
			enemy.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(enemy);
			hbc.setHealthBar(createHealthBar(enemy));
			enemy.add(hbc);
	
		WeaponComponent wc = new WeaponComponent(enemy);
			Weapon weapon = new Weapon();
			weapon.setName("enemy_Weapon");
			weapon.setAmmo(100000);
			weapon.setFiringRate(500);
			weapon.setDamage(10);
			weapon.setMaxRange(2000);
			weapon.setSlot(1);
			weapon.setMaxSpread(0);
			weapon.setCurSpread(0);
			weapon.setIncSpread(0);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			
	
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
		TargetTrackComponent ttc = new TargetTrackComponent(enemy);
			enemy.add(ttc);
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
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
			weapon.setAmmo(100000);
			weapon.setFiringRate(100);
			weapon.setDamage(10);
			weapon.setMaxRange(2000);
			weapon.setSlot(1);
			weapon.setMaxSpread(0);
			weapon.setCurSpread(0);
			weapon.setIncSpread(0);
			weapon.setNumShots(1);
			weapon.setBurstSize(2);
			weapon.setDelayAfterBurst(200);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			
	
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);

		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
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
			weapon.setAmmo(100000);
			weapon.setFiringRate(100);
			weapon.setDamage(10);
			weapon.setMaxRange(2000);
			weapon.setSlot(1);
			weapon.setMaxSpread(0);
			weapon.setCurSpread(0);
			weapon.setIncSpread(0);
			weapon.setNumShots(1);
			weapon.setBurstSize(2);
			weapon.setDelayAfterBurst(200);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
			
	
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);

		TargetTrackComponent ttc = new TargetTrackComponent(enemy);
			enemy.add(ttc);
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
			enemy.add(ai);
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
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
			
		AIComponent ai = new AIComponent(enemy);
			enemy.add(ai);
			
		core.add(enemy);
	}


	public void createEnemy0_1(final int x, final int y)
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
			rc.setSpriteID(2);
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
			weapon.setAmmo(100000);
			weapon.setFiringRate(2000);
			weapon.setDamage(5);
			weapon.setMaxRange(2000);
			weapon.setSlot(1);
			weapon.setMaxSpread(360);
			weapon.setCurSpread(360);
			weapon.setIncSpread(0);
			weapon.setNumShots(20);
			wc.addWeapon(weapon);
			wc.setCurrentWeapon("enemy_Weapon");
			enemy.add(wc);
		
		VelocityComponent vc = new VelocityComponent(enemy);
			vc.setInterval(4);
			
	
		Vector v = new Vector();
			v.setAngle(180);
			v.setMaxMagnitude(900);
			v.setMagnitude(1);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			enemy.add(vc);
		
			
		AIComponent ai = new AIComponent(enemy);
			ai.setAttackChance(1);
	
			enemy.add(ai);
			
		core.add(enemy);
	}


	public void createBullet(final IEntity owner,
							final int damage, final int maxRange, final double spread)
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
			tc.setWidth(16);
			tc.setHeight(16);
			tc.setBearing(0);
			bullet.add(tc);
		

		RenderComponent rc = new RenderComponent(bullet);
			if(owner.getName().equalsIgnoreCase("PLAYER"))
			{
				rc.setSpriteID(1);
			}
			else
			{
				rc.setSpriteID(0);
			}
			rc.setTexturePath("res/bullet.png");
			bullet.add(rc);

		VelocityComponent vc = new VelocityComponent(bullet);
			vc.setInterval(4);
			

		Vector v = new Vector();
			v.setAngle(otc.getBearing()+Math.random()*spread-Math.random()*spread);
			v.setMaxMagnitude(900);
			v.setMagnitude(15);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			bullet.add(vc);
		
		CollisionComponent cc = new CollisionComponent(bullet);
		
		if(owner.getName().equalsIgnoreCase("player"))
		{
			cc.setCollisionGroup(2);
		}
		else
		{
			cc.setCollisionGroup(3);			
		}
			bullet.add(cc);
		
		MaxRangeComponent mc = new MaxRangeComponent(bullet);
			mc.setMaxRange(maxRange);
			mc.setInitialXPos(tc.getXPos()+rotatedX);
			mc.setInitialYPos(tc.getYPos()+rotatedY);
			bullet.add(mc);
		
		DamageComponent dc = new DamageComponent(bullet);
			dc.setDamage(damage);
			bullet.add(dc);
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
			mc.setInitialXPos(x);
			mc.setInitialYPos(y);
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
	
	public void createFormation(final int x, final int y, final int form)
	{


	}





	
}
