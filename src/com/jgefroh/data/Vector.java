package com.jgefroh.data;

/**
 * Represents a vector.
 * @author Joseph Gefroh
 */
public class Vector
{
	//////////
	// DATA
	//////////
	/**The unique ID of this vector.*/
	private int id;
	
	/**Flag that indicates whether this vector should be removed after use.*/
	private boolean isContinuous;

	/**The X component of this vector.*/
	private double vx;
	
	/**The Y component of this vector.*/
	private double vy;
	
	/**The magnitude of this vector.*/
	private double magnitude;
	
	/**The maximum magnitude of this vector.*/
	private double maxMagnitude = -1;
	
	/**The angle, in degrees, of this vector.*/
	private double angle;
	
	//////////
	// GETTERS
	//////////
	/**
	 * Gets the unique ID of this vector.
	 * @return	the unique ID of this vector
	 */
	public int getID()
	{
		return this.id;
	}

	/**
	 * Gets the magnitude of this vector.
	 * @return	the magnitude of this vector
	 */
	public double getMagnitude()
	{
		return this.magnitude;
	}
	
	/**
	 * Gets the angle of this vector, in degrees.
	 * @return	the angle of this vector, in degrees
	 */
	public double getAngle()
	{
		return this.angle;
	}
	
	/**
	 * Gets the X component of this vector.
	 * @return	the X component of this vector
	 */
	public double getVX()
	{
		return this.vx;
	}
	
	/**
	 * Gets the Y component of this vector.
	 * @return	the Y component of this vector
	 */
	public double getVY()
	{
		return this.vy;
	}
	
	/**
	 * Returns the flag that indicates whether this vector is multi-use.
	 * @return	true if intended to be used multiple times; false otherwise
	 */
	public boolean isContinuous()
	{
		return this.isContinuous;
	}
	//////////
	// SETTERS
	//////////
	/**
	 * Sets the unique ID of this vector.
	 * @param id	the unique ID to assign to this vector
	 */
	public void setID(final int id)
	{
		this.id = id;
	}
	/**
	 * Sets the X component of this vector.
	 * @param vx	the X component of this vector
	 */
	public void setVX(final double vx)
	{
		this.vx = vx;
	}
	
	/**
	 * Sets the Y component of this vector.
	 * @param vy	the Y component of this vector
	 */
	public void setVY(final double vy)
	{
		this.vy = vy;
	}
	
	/**
	 * Sets the magnitude of this vector.
	 * @param magnitude	the magnitude of this vector
	 */
	public void setMagnitude(final double magnitude)
	{
		if(this.maxMagnitude>=0&&magnitude<=this.maxMagnitude)
		{//If the passed magnitude is within bounds...
			this.magnitude = magnitude;
		}
		else if(this.maxMagnitude<0)
		{//If the max magnitude has not been set
			this.magnitude = magnitude;
			this.maxMagnitude = magnitude;
		}
		else
		{//If above bounds...
			this.magnitude = maxMagnitude;
		}
	}
	
	/**
	 * Sets the angle of this vector, in degrees.
	 * @param angle	the angle of this vector, in degrees
	 */
	public void setAngle(final double angle)
	{
		this.angle = angle;
	}
	
	public void setMaxMagnitude(final double maxMagnitude)
	{
		this.maxMagnitude = maxMagnitude;
	}
	/**
	 * Sets the flag that indicates whether this vector is multi-use.
	 * @param isContinuous	true if multi use; false otherwise
	 */
	public void setContinuous(final boolean isContinuous)
	{
		this.isContinuous = isContinuous;
	}
	
	
	//////////
	// METHODS
	//////////
	/**
	 * Calculates the components of this vector from its angle and magnitude.
	 */
	public void calcComponents()
	{
		this.vx = magnitude * Math.cos(Math.toRadians(angle));
		this.vy = magnitude * Math.sin(Math.toRadians(angle));
	}
	
	/**
	 * Calculates the magnitude and angle of this vector from its components.
	 */
	public void calcFromComponents()
	{
		setMagnitude(Math.sqrt(vx*vx+vy*vy));
		setAngle(Math.toDegrees(Math.atan2(this.vy, this.vx)));
	}
	
	/**
	 * Adds the passed vector to this vector.
	 * @param v1	the vector to add to this vector
	 */
	public void add(final Vector v1)
	{
		if(v1!=null)
		{
			//Get components of first vector.
			this.calcComponents();
			
			//Get components of second vector.
			v1.calcComponents();
			
			//Add the vectors.
			setVX(this.vx+v1.getVX());
			setVY(this.vy+v1.getVY());
			
			//Calculate the new angle and magnitude.
			this.calcFromComponents();
		}
	}
	
	@Override
	public String toString()
	{
		String result = "angle=" + this.angle 
					+ ", mag=" + this.magnitude
					+ ",(" + this.vx +","+ this.vy +")"; 
		return result;
	}
}
