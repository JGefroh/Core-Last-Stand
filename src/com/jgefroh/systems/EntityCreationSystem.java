package com.jgefroh.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.DamageComponent;
import com.jgefroh.components.DecayComponent;
import com.jgefroh.components.ExplosionComponent;
import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.GUIBarComponent;
import com.jgefroh.components.GUICharSlotComponent;
import com.jgefroh.components.GUIComponent;
import com.jgefroh.components.GUICounterComponent;
import com.jgefroh.components.GUITextComponent;
import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.InputComponent;
import com.jgefroh.components.KeepInBoundsComponent;
import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.MouseTrackComponent;
import com.jgefroh.components.OutOfBoundsComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.ScoreComponent;
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
	
	HashMap<String, ArrayList<IEntity>> entityPool;
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

		entityPool = new HashMap<String, ArrayList<IEntity>>();
		core.setInterested(this, "BEARING_TO_MOUSE");
		core.setInterested(this, "CREATE");
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
		if(id.equals("CREATE"))
		{
			create(message);
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	private void create(final String[] message)
	{
		if(message.length>=2)
		{
			String makeThis = message[0];
			String forThis= message[1];
			
			if(makeThis.equals("BULLET"))
			{
				createBullet(core.getEntityWithID(forThis));
			}
			else if(makeThis.equals("EXPLOSION"))
			{
				createExplosion(core.getEntityWithID(forThis));
			}
		}
	}
	public void addToPool(final IEntity entity)
	{
		ArrayList<IEntity> entities = entityPool.get(entity.getName());
		
		if(entities!=null)
		{			
			entities.add(entity);
		}
		else
		{
			ArrayList<IEntity> newEntities = new ArrayList<IEntity>();
			newEntities.add(entity);
			entityPool.put(entity.getName(), newEntities);		
		}
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
		
		TransformComponent tc = new TransformComponent();
			tc.setYPos(y);
			tc.setXPos(x);
			tc.setWidth(32);
			tc.setHeight(32);
			player.add(tc);
		
		RenderComponent rc = new RenderComponent();
			rc.setSpriteID(0);
			rc.setTexturePath("res/player.png");
			player.add(rc);
		
		VelocityComponent vc = new VelocityComponent();
			vc.setInterval(24);
			vc.setContinuous(false);
			player.add(vc);
			
		InputComponent ic = new InputComponent();
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
		
		CollisionComponent cc = new CollisionComponent();
			cc.setCollisionGroup(0);
			player.add(cc);
		
		ForceGeneratorComponent fgc = new ForceGeneratorComponent();
			Vector v = new Vector();
			fgc.setMagnitude(5);	//Movement speed
			fgc.setVector(v);
			player.add(fgc);
		
		WeaponComponent wc = new WeaponComponent();
		
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

			weapon = new Weapon();
			weapon.setConsecutiveShotDelay(200);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setName("gun2");
			weapon.setDamage(8);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(3);
			weapon.setBurstDelay(300);
			weapon.setShotType(0);
			wc.addWeapon(weapon);

			weapon = new Weapon();
			weapon.setConsecutiveShotDelay(200);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setName("gun2");
			weapon.setDamage(8);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(3);
			weapon.setBurstDelay(300);
			weapon.setShotType(0);
			weapon.setRecoilMin(3);
			weapon.setRecoilMax(15);
			weapon.setRecoilInc(3);
			weapon.setRecoilDec(1);
			weapon.setShotType(0);
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setConsecutiveShotDelay(100);
			weapon.setFireMode(FireMode.AUTO.ordinal());
			weapon.setName("gun3");
			weapon.setDamage(8);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(0);
			weapon.setBurstDelay(500);
			weapon.setShotType(0);
			wc.addWeapon(weapon);
			
			weapon = new Weapon();
			weapon.setConsecutiveShotDelay(500);
			weapon.setFireMode(FireMode.SEMI.ordinal());
			weapon.setName("boom_cannon");
			weapon.setDamage(25);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(0);
			weapon.setBurstDelay(500);
			weapon.setShotType(4);
			wc.addWeapon(weapon);

			
			
			wc.setCurrentWeapon("boom_cannon");
			
			
			player.add(wc);
			
		MouseTrackComponent mc = new MouseTrackComponent();
			player.add(mc);
		
		HealthComponent hc = new HealthComponent();
			hc.setCurHealth(200);
			hc.setMaxHealth(200);
			player.add(hc);
			
		TargetComponent tarc = new TargetComponent();
			player.add(tarc);
		
		ShieldComponent sc = new ShieldComponent();
			sc.setShieldInc(1);
			sc.setShieldDec(1);
			sc.setShieldRechargeInterval(100);
			sc.setShieldRechargeDelay(5000);
			sc.setShieldCur(50);
			sc.setShieldMax(50);
			sc.setShieldMin(10);
			sc.setShieldDrainInterval(100);
			player.add(sc);
			
		KeepInBoundsComponent kibc = new KeepInBoundsComponent();
			player.add(kibc);
		core.add(player);
		
		core.send("PLAYER_CREATED", player.getID());
	}
	
	public void createEnemy(final int xPos, final int yPos, final String type)
	{
		IEntity entity = new Entity("ENEMY");
		
		TransformComponent tc 		= new TransformComponent();
		RenderComponent rc			= new RenderComponent();
		CollisionComponent cc 		= new CollisionComponent();
		HealthComponent hc 			= new HealthComponent();			
		HealthBarComponent hbc 		= new HealthBarComponent();
		WeaponComponent wc 			= new WeaponComponent();
		VelocityComponent vc 		= new VelocityComponent();
		AIComponent ai 				= new AIComponent();
		OutOfBoundsComponent oobc 	= new OutOfBoundsComponent();
		ScoreComponent scc 			= new ScoreComponent();
		
		/////
		
		
		entity.add(tc);
		entity.add(rc);
		entity.add(cc);
		entity.add(hc);
		entity.add(hbc);
		entity.add(wc);
		entity.add(vc);
		entity.add(ai);
		entity.add(oobc);
		entity.add(scc);
		
		int spriteID = 0;
		int health = 0;

		Weapon weapon = new Weapon();
		weapon.setName("enemy_Weapon");
		wc.addWeapon(weapon);
		tc.setBearing(180);

		if(type.equalsIgnoreCase("BASIC"))
		{
			spriteID = 0;
			health = 30;
			weapon.setConsecutiveShotDelay(800);
			weapon.setDamage(15);
			weapon.setMaxRange(1500);
			weapon.setShotType(1);
		}
		else if(type.equalsIgnoreCase("TURNY"))
		{
			spriteID = 1;
			health = 30;
			weapon.setConsecutiveShotDelay(800);
			weapon.setDamage(15);
			weapon.setMaxRange(1500);
			weapon.setShotType(1);
			TargetTrackComponent ttc = new TargetTrackComponent();
				ttc.setTargetRange(1500);
				ttc.setTurnSpeed(2.5);
				ttc.setTurnInterval(50);
				entity.add(ttc);
		}
		else if(type.equalsIgnoreCase("BURSTY"))
		{
			spriteID = 2;
			health = 20;
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(100);
			weapon.setDamage(3);
			weapon.setMaxRange(1500);
			weapon.setBurstSize(3);
			weapon.setBurstDelay(1000);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setShotType(2);
		}
		else if(type.equalsIgnoreCase("TURNYBURSTY"))
		{
			spriteID = 3;
			health = 20;
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
			TargetTrackComponent ttc = new TargetTrackComponent();
				ttc.setTargetRange(1500);
				ttc.setTurnSpeed(2.5);
				ttc.setTurnInterval(50);
				entity.add(ttc);
		}
		else if(type.equalsIgnoreCase("MISSILEY"))
		{
			spriteID = 5;
			health = 20;
			weapon.setConsecutiveShotDelay(200);
			weapon.setDamage(3);
			weapon.setMaxRange(1500);
			weapon.setRecoilCur(360);
			weapon.setFireMode(FireMode.BURST.ordinal());
			weapon.setBurstDelay(3000);
			weapon.setShotType(3);
			weapon.setNumShots(1);
			weapon.setBurstSize(4);
			
			TargetTrackComponent ttc = new TargetTrackComponent();
				ttc.setTargetRange(1500);
				ttc.setTurnSpeed(2.5);
				ttc.setTurnInterval(50);
				entity.add(ttc);
		}
		else if(type.equalsIgnoreCase("SHOTGUNNY"))
		{
			health = 20;
			spriteID = 6;
			weapon.setName("enemy_Weapon");
			weapon.setConsecutiveShotDelay(1500);
			weapon.setDamage(2);
			weapon.setMaxRange(1500);
			weapon.setRecoilCur(90);
			weapon.setShotType(2);
			weapon.setNumShots(5);
			TargetTrackComponent ttc = new TargetTrackComponent();
				ttc.setTargetRange(500);
				entity.add(ttc);
		}
		else if(type.equalsIgnoreCase("SHIELDY"))
		{
			spriteID = 8;
			health = 100;
			tc.setBearing(270);
			entity.removeComponent(wc.getClass());
		}
		
		tc.setXPos(xPos);
		tc.setYPos(yPos);
		tc.setWidth(32);
		tc.setHeight(32);
		
		rc.setTexturePath("res/enemy.png");
		rc.setSpriteID(spriteID);
		
		cc.setCollisionGroup(1);
		
		hc.setCurHealth(health);	
		hbc.setHealthBar(createHealthBar(entity));

		vc.setInterval(24);

		Vector v = new Vector();
		v.setAngle(180);
		v.setMaxMagnitude(900);
		v.setMagnitude(0.60);
		v.calcComponents();
		vc.setTotalMovementVector(v);
		vc.setContinuous(true);
		
		ai.setAttackChance(1);
		ai.setInRangeOfTarget(true);
		
		scc.setScore(10);
		wc.setCurrentWeapon("enemy_Weapon");


		///////

		
		core.add(entity);
	}

	private void createExplosion(final IEntity source)
	{
		IEntity entity = source;

		TransformComponent tc = null;
		RenderComponent rc = null;
		CollisionComponent cc = null;
		DamageComponent dc = null;
		DecayComponent dec = null;
		ExplosionComponent ec = null;
		
		if(entity==null)
		{
			entity = new Entity();
			core.removeEntity(source);
			core.add(entity);
		}
		else
		{
			tc = entity.getComponent(TransformComponent.class);
			rc = entity.getComponent(RenderComponent.class);
			cc = entity.getComponent(CollisionComponent.class);
			dc = entity.getComponent(DamageComponent.class);
			dec = entity.getComponent(DecayComponent.class);
			ec = entity.getComponent(ExplosionComponent.class);
		}
		
		if(tc==null)
		{
			tc = new TransformComponent();
		}
		if(rc==null)
		{
			rc = new RenderComponent();
		}
		if(cc==null)
		{
			cc = new CollisionComponent();
		}
		if(dc==null)
		{
			dc = new DamageComponent();
		}
		if(dec==null)
		{
			dec = new DecayComponent();
		}
		if(ec==null)
		{
			ec = new ExplosionComponent();
		}
		entity.setName("EXPLOSION");

		entity.removeAllComponents();
		
		entity.add(tc);
		entity.add(rc);
		entity.add(cc);
		entity.add(dc);
		entity.add(dec);
		entity.add(ec);
		
		tc.setWidth(32);
		tc.setHeight(32);
		
		rc.setTexturePath("res/fx.png");
		rc.setTextureID(-1);
		rc.setSpriteID(1);
		
		dec.setTimeUntilDecay(1000);
				
		ec.setHeightMax(512);
		ec.setHeightInc(32);
		ec.setWidthMax(512);
		ec.setWidthInc(32);
		ec.setUpdateInterval(50);
		ec.setPulse(100);
		
		cc.setCollisionGroup(2);
		entity.setChanged(true);
	}
	
	public void createBullet(final IEntity owner)
	{
		if(owner==null)
		{
			return;
		}
		
		TransformComponent otc = owner.getComponent(TransformComponent.class);
		
		if(otc==null)
		{
			return;
		}
		
		IEntity bullet = new Entity("BULLET");
		WeaponComponent owc = owner.getComponent(WeaponComponent.class);
		
		TransformComponent tc = new TransformComponent();
		RenderComponent rc = new RenderComponent();
		VelocityComponent vc = new VelocityComponent();
		CollisionComponent cc = new CollisionComponent();
		MaxRangeComponent mc = new MaxRangeComponent();
		DamageComponent dc = new DamageComponent();
		OutOfBoundsComponent oobc = new OutOfBoundsComponent();
		TargetTrackComponent ttc = new TargetTrackComponent();
		ForceGeneratorComponent fgc = new ForceGeneratorComponent();
		
		double angle = otc.getBearing();
		double rotatedX = Math.sin(angle);
		double rotatedY = Math.cos(angle);
		
		double recoil = owc.getRecoilCur();
		double recoil1 = Math.random()*(recoil/2);
		double recoil2 = Math.random()*(recoil/2);
		
		tc.setXPos(otc.getXPos()+rotatedX);
		tc.setYPos(otc.getYPos()+rotatedY);
		tc.setWidth(8);
		tc.setHeight(10);
		tc.setBearing(otc.getBearing());
		
		rc.setTexturePath("res/bullet.png");
		bullet.add(tc);
		bullet.add(rc);
		bullet.add(vc);
		bullet.add(cc);
		bullet.add(mc);
		bullet.add(dc);
		bullet.add(oobc);

		Vector v = new Vector();
		v.setAngle(otc.getBearing()+recoil1-recoil2);
		v.setMagnitude(15);
		v.calcComponents();
		vc.setTotalMovementVector(v);
		vc.setContinuous(true);
		vc.setInterval(24);

		mc.setMaxRange(owc.getMaxRange());
		mc.setLastXPos(tc.getXPos());
		mc.setLastYPos(tc.getYPos());
		
		dc.setDamage(owc.getDamage());
		
		oobc.setChecking(true);

		switch(owc.getShotType())
		{
			case 0:	//PLAYER SHOT
				tc.setWidth(8);
				tc.setHeight(10);
				rc.setSpriteID(2);
				break;
			case 1:	//RED BALL
				tc.setWidth(16);
				tc.setHeight(16);
				rc.setSpriteID(0);
				break;
			case 2:	//BLUE PELLET
				tc.setWidth(8);
				tc.setHeight(8);
				rc.setSpriteID(1);
				break;
			case 3:	//FOLLOWER
				tc.setBearing(v.getAngle());
				rc.setSpriteID(3);
				ttc.setTargetRange(1500);
				ttc.setTurnInterval(100);
				ttc.setTurnSpeed(6);
			
				fgc.setContinuous(true);	//WHAT?
				fgc.setRelative(true);	//HUH?
				Vector vector = new Vector();
				vector.setMagnitude(5);	//Does this affect speed?
				fgc.setVector(vector);	
				v.setMaxMagnitude(5);	//OR DOES THIS?

				bullet.add(ttc);
				bullet.add(fgc);
				break;
			case 4: //EXPLOSIVE
				bullet.setName("EXPLOSIVE");
				rc.setSpriteID(3);	
				break;
		}
		
		if(owner.getName().equalsIgnoreCase("PLAYER"))
		{
			cc.setCollisionGroup(2);
		}
		else
		{
			cc.setCollisionGroup(3);
		}
		
		core.add(bullet);
	}
	
	
	public void createQuad(final double x, final double y, final double width, final double height, final double rot)
	{
		
		IEntity bullet = new Entity();
		bullet.setName("QUAD");
		
		TransformComponent tc = new TransformComponent();
			tc.setXPos(x);
			tc.setYPos(y);
			tc.setWidth(width);
			tc.setHeight(height);
			tc.setBearing(rot);
			bullet.add(tc);
		
		RenderComponent rc = new RenderComponent();
			rc.setSpriteID(0);
			//rc.setTexturePath("res\\bullet.png");
			bullet.add(rc);
			
		MaxRangeComponent mc = new MaxRangeComponent();
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
		if(otc==null)
		{
			return null;
		}
		TransformComponent htc = new TransformComponent();
			htc.setXPos(otc.getXPos());
			htc.setYPos(otc.getYPos()+15);
			htc.setWidth(64);
			htc.setHeight(5);
			healthBar.add(htc);
			
		RenderComponent rc = new RenderComponent();
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
		
		TransformComponent stc = new TransformComponent();
			stc.setXPos(otc.getXPos());
			stc.setYPos(otc.getYPos());
			stc.setWidth(128);
			stc.setHeight(128);
			shield.add(stc);
			
		RenderComponent rc = new RenderComponent();
			rc.setSpriteID(0);
			rc.setTexturePath("res/fx.png");
			rc.setRGB(0, 125, 255);
			shield.add(rc);
		
		CollisionComponent cc = new CollisionComponent();
			cc.setCollisionGroup(occ.getCollisionGroup());
			shield.add(cc);
		core.add(shield);
		return shield;
	}
	
	public void createEnemy(final int xOffset, final int y, final int type)
	{
		int x = 1366 + xOffset;
		String name = "";
		switch(type)
		{
			case 0:
				name = "BASIC";
				break;
			case 1:
				name = "TURNY";
				break;
			case 2:
				name = "BURSTY";
				break;
			case 3:
				name = "TURNYBURSTY";
				break;
			case 4:
				name = "SHOTGUNNY";
				break;
			case 5:
				name = "MISSILEY";
				break;
			case 6:
				name = "PELLETY";
				break;
			case 7:
				name = "SHIELDY";
				break;
			
		}
		createEnemy(x, y,  name);
	}
	
	/**
	 * Creates a GUI bar.
	 * @param xPos
	 * @param yPos
	 * @param width
	 * @param height
	 * @param shrinkDir
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public String createGUIBar(final double xPos, final double yPos,
								final double width, final double height,
								final int shrinkDir,
								final float r, final float g, final float b)
	{
		IEntity entity = new Entity();
		entity.setName("GUI_BAR");
		
		TransformComponent tc = new TransformComponent();
		RenderComponent rc = new RenderComponent();
		GUIComponent gc = new GUIComponent();
		GUIBarComponent gbc = new GUIBarComponent();
		
		tc.setWidth(width);
		tc.setHeight(height);
		tc.setXPos(xPos);
		tc.setYPos(yPos);

		rc.setRGB(r, g, b);
		
		
		gbc.setShrinkDir(shrinkDir);
		gbc.setDefWidth(width);
		gbc.setDefHeight(height);
		gbc.setDefXPos(xPos);
		gbc.setDefYPos(yPos);
		gbc.setMaxWidth(width);
		gbc.setMaxHeight(height);
		
		if(gbc.right())
		{
			gbc.setDefXPos(xPos+width);
		}
		else if(gbc.collapseMiddleH() || gbc.collapseMiddleV())
		{
			tc.setXPos(xPos+width/2);
		}

		entity.add(tc);
		entity.add(rc);
		entity.add(gc);
		entity.add(gbc);
		
		core.add(entity);
		
		return entity.getID();
	}
	
	/**
	 * 
	 * @param xPos
	 * @param yPos
	 * @param width
	 * @param height
	 * @param defChar
	 * @param slotNum
	 * @return
	 */
	public String createGUICharSlot(final double xPos, final double yPos,
										final double width, final double height,
										final char defChar, final int slotNum)
	{
		IEntity entity = new Entity();
		entity.setName("CHAR_SLOT");
		
		TransformComponent tc = new TransformComponent();
		RenderComponent rc = new RenderComponent();
		GUICharSlotComponent gcsc = new GUICharSlotComponent();
		
		
		tc.setHeight(height);
		tc.setWidth(width);
		tc.setXPos(xPos);
		tc.setYPos(yPos);
		tc.setBearing(-90);
		
		rc.setTexturePath("res/alphabet.png");
		rc.setSpriteID(-1);
		rc.setVisible(false);
		gcsc.setSlotNum(slotNum);
		
		entity.add(tc);
		entity.add(rc);
		entity.add(gcsc);
		
		core.add(entity);
		return entity.getID();
	}


	/**
	 * 
	 * @param xPos
	 * @param yPos
	 * @param charWidth
	 * @param charHeight
	 * @param numSlots
	 * @param defaultChar
	 * @return
	 */
	public String createGUICounter(final double xPos, final double yPos,
									final int charWidth, final int charHeight,
									final int numSlots, final char defaultChar)
	{
		IEntity entity = new Entity();
		entity.setName("GUI_BAR");
		
		TransformComponent tc = new TransformComponent();
		RenderComponent rc = new RenderComponent();
		GUIComponent gc = new GUIComponent();
		GUICounterComponent gcc = new GUICounterComponent();
		
		tc.setXPos(xPos);
		tc.setYPos(yPos);
		
		gcc.setCharHeight(charHeight);
		gcc.setCharWidth(charWidth);
		gcc.setNumSlots(numSlots);
		gcc.setDefaultChar(defaultChar);

		
		rc.setVisible(false);
		ArrayList<String> children = new ArrayList<String>(numSlots);

		for(int index=0;index<numSlots;index++)
		{
			children.add(createGUICharSlot(xPos+charWidth*index, yPos, charWidth, charHeight, defaultChar, index));
		}
		
		gcc.setChildren(children);
		entity.add(tc);
		entity.add(rc);
		entity.add(gc);
		entity.add(gcc);

		core.add(entity);
		
		return entity.getID();
	}

	public String createGUIIcon(final double xPos, final double yPos, 
			final double width, final double height, final int spriteID,
			final String command, final String value, final int id)
	{
		IEntity entity = new Entity();
		entity.setName("ICON");
		
		TransformComponent tc = new TransformComponent();
		RenderComponent rc = new RenderComponent();
		GUIComponent gc = new GUIComponent();
		
		tc.setHeight(height);
		tc.setWidth(width);
		tc.setXPos(xPos);
		tc.setYPos(yPos);
		tc.setBearing(-90);
		
		rc.setTexturePath("res/icons.png");
		rc.setSpriteID(spriteID);
		

		gc.setID(id);
		entity.add(tc);
		entity.add(rc);
		entity.add(gc);
		
		core.add(entity);
		
		return entity.getID();
	}
	
	public String createGUITextArea(final double xPos, final double yPos, 
										final int numLines, final int numCharsPerLine, 
										final double charWidth, final double charHeight,
										final int ySpacing,
										final char defaultChar)
	{
		System.out.println("MAKING TEXT AREA");
		IEntity entity = new Entity();
		entity.setName("ICON");
		
		TransformComponent tc = new TransformComponent();
		RenderComponent rc = new RenderComponent();
		GUITextComponent gtc = new GUITextComponent();
		
		tc.setXPos(xPos);
		tc.setYPos(yPos);
		
		rc.setVisible(false);
		
		gtc.setNumCharsPerLine(numCharsPerLine);
		gtc.setNumLines(numLines);
		ArrayList<String> childrenIDs = new ArrayList<String>(numLines*numCharsPerLine);
		
		int curSlot = 0;

		for(int line = 0;line<numLines;line++)
		{
			for(int slot = 0;slot<numCharsPerLine;slot++)
			{
				childrenIDs.add(this.createGUICharSlot(xPos+(charWidth*slot), yPos+(charHeight*line)+(ySpacing*line), charWidth, charHeight, defaultChar, curSlot));
				curSlot++;
			}
		}
		gtc.setChildren(childrenIDs);
		gtc.setDefaultChar(defaultChar);
		
		entity.add(tc);
		entity.add(rc);
		entity.add(gtc);
		
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
