package com.jgefroh.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.DamageComponent;
import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.InputComponent;
import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.MouseTrackComponent;
import com.jgefroh.components.RenderComponent;
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
			weapon.setFiringRate(500);
			weapon.setName("pistol");
			weapon.setAmmo(7);
			weapon.setDamage(1);
			weapon.setMaxRange(700);
			weapon.setSlot(1);
			weapon.setMaxSpread(5);
			weapon.setCurSpread(0);
			weapon.setIncSpread(1);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setName("rifle");
			weapon.setFiringRate(150);
			weapon.setAmmo(30);
			weapon.setDamage(5);
			weapon.setMaxRange(1000);
			weapon.setSlot(2);
			weapon.setMaxSpread(15);
			weapon.setCurSpread(0);
			weapon.setIncSpread(1);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setName("machine_gun");
			weapon.setFiringRate(150);
			weapon.setAmmo(100);
			weapon.setDamage(5);
			weapon.setMaxRange(1000);
			weapon.setSlot(3);
			weapon.setMaxSpread(20);
			weapon.setCurSpread(0);
			weapon.setIncSpread(1);
			weapon.setNumShots(1);
			
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setName("sniper");
			weapon.setAmmo(5);
			weapon.setFiringRate(1000);
			weapon.setDamage(100);
			weapon.setMaxRange(1500);
			weapon.setSlot(4);
			weapon.setMaxSpread(5);
			weapon.setCurSpread(0);
			weapon.setIncSpread(5);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setName("shotgun");
			weapon.setAmmo(7);
			weapon.setFiringRate(1000);
			weapon.setDamage(3);
			weapon.setMaxRange(750);
			weapon.setSlot(5);
			weapon.setMaxSpread(20);
			weapon.setCurSpread(20);
			weapon.setIncSpread(0);
			weapon.setNumShots(7);
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setName("rocket");
			weapon.setAmmo(4);
			weapon.setFiringRate(3000);
			weapon.setDamage(50);
			weapon.setMaxRange(1000);
			weapon.setSlot(6);
			weapon.setMaxSpread(1);
			weapon.setCurSpread(1);
			weapon.setIncSpread(1);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);

			weapon = new Weapon();
			weapon.setName("cheat");
			weapon.setAmmo(10000);
			weapon.setFiringRate(50);
			weapon.setDamage(1);
			weapon.setMaxRange(1500);
			weapon.setSlot(7);
			weapon.setMaxSpread(1);
			weapon.setCurSpread(1);
			weapon.setIncSpread(1);
			weapon.setNumShots(1);
			wc.addWeapon(weapon);

			wc.setCurrentWeapon("pistol");
			
			
			player.add(wc);
			
		MouseTrackComponent mc = new MouseTrackComponent(player);
			player.add(mc);
		
		HealthComponent hc = new HealthComponent(player);
			hc.setCurHealth(100);
			player.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(player);
			hbc.setHealthBar(createHealthBar(player));
			player.add(hbc);
			
		core.add(player);
	}
	
	public void createAlien(final int x, final int y, final int health)
	{	
		IEntity alien = new Entity();
		alien.setName("ALIEN");
		
		TransformComponent tc = new TransformComponent(alien);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			tc.setBearing(90);
			alien.add(tc);
		
		RenderComponent rc = new RenderComponent(alien);
			rc.setSpriteID(0);
			rc.setTexturePath("res/enemy.png");
			alien.add(rc);
		
		CollisionComponent cc = new CollisionComponent(alien);
			cc.setCollisionGroup(1);
			alien.add(cc);
		
		HealthComponent hc = new HealthComponent(alien);
			hc.setCurHealth(health);
			alien.add(hc);
			
		HealthBarComponent hbc = new HealthBarComponent(alien);
			hbc.setHealthBar(createHealthBar(alien));
			alien.add(hbc);

		core.add(alien);
	}
	
	public void createHostage(final int x, final int y, final int health)
	{
		IEntity hostage = new Entity();
		hostage.setName("HOSTAGE");
		
		TransformComponent tc = new TransformComponent(hostage);
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(8);
			tc.setHeight(8);
			tc.setBearing(90);
			hostage.add(tc);
		
		RenderComponent rc = new RenderComponent(hostage);
			rc.setSpriteID(0);
			rc.setTexturePath("res/hostage.png");
			hostage.add(rc);
		
		CollisionComponent cc = new CollisionComponent(hostage);
			cc.setCollisionGroup(1);
			hostage.add(cc);
		
		HealthComponent hc = new HealthComponent(hostage);
			hc.setCurHealth(health);
			hostage.add(hc);
		core.add(hostage);
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
		
		double ang = owner.getComponent(TransformComponent.class).getBearing();
		double mag = owner.getComponent(VelocityComponent.class).getTotalMovementVector().getMagnitude();
		
		
		IEntity bullet = new Entity();
		bullet.setName("BULLET");
		
		TransformComponent tc = new TransformComponent(bullet);
			tc.setXPos(otc.getXPos()+rotatedX);
			tc.setYPos(otc.getYPos()+rotatedY);
			tc.setWidth(5);
			tc.setHeight(5);
			tc.setBearing(0);
			bullet.add(tc);
		
		RenderComponent rc = new RenderComponent(bullet);
			rc.setSpriteID(0);
			//rc.setTexturePath("res\\bullet.png");
			bullet.add(rc);

		VelocityComponent vc = new VelocityComponent(bullet);
			vc.setInterval(4);
			

		Vector v = new Vector();
			v.setAngle(otc.getBearing()+Math.random()*spread-Math.random()*spread);
			v.setMaxMagnitude(900);
			v.setMagnitude(30);
			v.setContinuous(true);
			v.calcComponents();
			vc.setTotalMovementVector(v);
			bullet.add(vc);
		
		CollisionComponent cc = new CollisionComponent(bullet);
			cc.setCollisionGroup(2);
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




	
}
