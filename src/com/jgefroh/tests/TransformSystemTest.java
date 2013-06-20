package com.jgefroh.tests;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.Entity;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Vector;
import com.jgefroh.infopacks.MovementInfoPack;
import com.jgefroh.infopacks.MovementInfoPackFactory;
import com.jgefroh.systems.TransformSystem;


public class TransformSystemTest extends TestCase{

	private static TransformSystem ts;
	private static Core core;

	@Before
	public void setUp() throws Exception
	{
		core = new Core();
		ts = new TransformSystem(core);
		core.add(ts);
		core.add(new MovementInfoPackFactory());
		
		IEntity entity = new Entity();
		TransformComponent tc = new TransformComponent(entity);
		VelocityComponent vc = new VelocityComponent(entity);
			entity.add(tc);
			entity.add(vc);
		core.add(entity);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testMoveEntity_10right_move()
	{
		Vector moveVec = new Vector();
			moveVec.setAngle(0);
			moveVec.setMaxMagnitude(10);
			moveVec.setMagnitude(10);
			moveVec.setContinuous(true);
			moveVec.calcComponents();
		
		MovementInfoPack pack 
			= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();

		assertTrue(xPos==10);
		assertTrue(Math.round(yPos)==0);
	}
	
	@Test
	public void testMoveEntity_10left_move()
	{
		Vector moveVec = new Vector();
		moveVec.setAngle(180);
		moveVec.setMaxMagnitude(10);
		moveVec.setMagnitude(10);
		moveVec.setContinuous(true);
		moveVec.calcComponents();
	
		MovementInfoPack pack 
				= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();

		assertTrue(xPos==-10);
		assertTrue(Math.round(yPos)==0);
	}
	
	@Test
	public void testMoveEntity_10up_move()
	{
		Vector moveVec = new Vector();
		moveVec.setAngle(270);
		moveVec.setMaxMagnitude(10);
		moveVec.setMagnitude(10);
		moveVec.setContinuous(true);
		moveVec.calcComponents();
	
		MovementInfoPack pack 
				= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();

		assertTrue(Math.round(xPos)==0);
		assertTrue(Math.round(yPos)==-10);
	}
	
	@Test
	public void testMoveEntity_10down_move()
	{
		Vector moveVec = new Vector();
		moveVec.setAngle(90);
		moveVec.setMaxMagnitude(10);
		moveVec.setMagnitude(10);
		moveVec.setContinuous(true);
		moveVec.calcComponents();
	
		MovementInfoPack pack 
				= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();

		assertTrue(Math.round(xPos)==0);
		assertTrue(Math.round(yPos)==10);
	}
	
	@Test
	public void testMoveEntity_4right4up_move()
	{
		Vector moveVec = new Vector();
		moveVec.setAngle(45);
		moveVec.setMaxMagnitude(5);
		moveVec.setMagnitude(5);
		moveVec.setContinuous(true);
		moveVec.calcComponents();
	
		MovementInfoPack pack 
				= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();


		assertTrue(Math.round(xPos)==4);
		assertTrue(Math.round(yPos)==4);
	}
	
	@Test
	public void testMoveEntity_4right3up_move()
	{
		Vector moveVec = new Vector();
		moveVec.setAngle(36.86);	//3:4:5 triangle
		moveVec.setMaxMagnitude(5);
		moveVec.setMagnitude(5);
		moveVec.setContinuous(true);
		moveVec.calcComponents();
	
		MovementInfoPack pack 
				= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();


		assertTrue(Math.round(xPos)==4);
		assertTrue(Math.round(yPos)==3);
	}
	
	@Test
	public void testMoveEntity_4000right_move()
	{
		Vector moveVec = new Vector();
		moveVec.setAngle(0);	//3:4:5 triangle
		moveVec.setMaxMagnitude(4000);
		moveVec.setMagnitude(4000);
		moveVec.setContinuous(true);
		moveVec.calcComponents();
	
		MovementInfoPack pack 
				= core.getInfoPackFrom("1", MovementInfoPack.class);
		pack.setTotalMovementVector(moveVec);
		core.work();
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();


		assertTrue(Math.round(xPos)==4000);
		assertTrue(Math.round(yPos)==0);
	}
	
	@Test
	public void testNoVector_donothing()
	{
		MovementInfoPack pack 
		= core.getInfoPackFrom("1", MovementInfoPack.class);

		core.work();
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();
		
		
		assertTrue(Math.round(xPos)==0);
		assertTrue(Math.round(yPos)==0);
	}
	
	
	@Test
	public void test_notc_dontcrash()
	{
		MovementInfoPack pack 
		= core.getInfoPackFrom("1", MovementInfoPack.class);
		
		core.getEntityWithID("1").removeComponent(VelocityComponent.class);
		core.work();
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();
		
		
		assertTrue(Math.round(xPos)==0);
		assertTrue(Math.round(yPos)==0);
	}
}
