package com.jgefroh.tests;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.LoggerFactory;

public class Benchmark
{
	
	private double avgRunTime;
	private double peakRunTime;
	private double lowestRunTime;
	private double totalRunTime;
	
	private int numTimesRun;

	private int avgNumEntities;
	private int lowestNumEntities;
	private int highestNumEntities;
	private int totalNumEntities;
	
	private long benchmarkInterval;
	private long benchmarkStartTime;
    private DecimalFormat df;
    private static final Logger LOGGER = LoggerFactory.getLogger(Benchmark.class, Level.ALL);
    private String name;
    
    public Benchmark(final String targetName, final Level level)
    {
    	this.name = targetName;
    	LOGGER.setLevel(level);
    	this.benchmarkInterval = 1000000000;
    	this.df = new DecimalFormat("#.###");
    	
    	resetValues();

    }
    
	public void benchmark(final long timeTaken, final int numEntities)
	{
		//Calculate total execution time
		this.totalRunTime += timeTaken;
		this.totalNumEntities += numEntities;
		
		//Calculate peak time
		if(timeTaken>this.peakRunTime)
		{
			this.peakRunTime = timeTaken;
		}
		
		//Calculate lowest time
		if(timeTaken<this.lowestRunTime)
		{
			this.lowestRunTime = timeTaken;
		}
		
		//Calculate highest num entities
		if(numEntities>this.highestNumEntities)
		{
			this.highestNumEntities = numEntities;
		}
		
		//Calculate lowest num entities
		if(numEntities<this.lowestNumEntities)
		{
			this.lowestNumEntities = numEntities;
		}
		

		//If time interval has passed
		if(System.nanoTime()-this.benchmarkStartTime>=this.benchmarkInterval)
		{
			//Calculate average time
			if(this.numTimesRun>0)
			{				
				this.avgRunTime = this.totalRunTime/this.numTimesRun;
				this.avgNumEntities = this.totalNumEntities/this.numTimesRun;
			}
			LOGGER.log(Level.FINER, "Benchmark for: " + this.name + "\n"
								+"Total runs this interval: " 
								+ this.numTimesRun +"\n" 
								+ "Average run time: " 
								+ df.format(this.avgRunTime/1000000) + " ms.\n"
								+"Total run time: " 
								+ df.format(this.totalRunTime/1000000) + " ms.\n"				
								+"Peak run time: " 
								+ df.format(this.peakRunTime/1000000) + " ms.\n"
								+"Lowest run time: " 
								+ df.format(this.lowestRunTime/1000000) + " ms.\n"								
								+ "Average # Entities: " 
								+ this.avgNumEntities + "\n"
								+"Total # Entities: " 
								+ this.totalNumEntities + "\n"				
								+"Highest # Entities: " 
								+ this.highestNumEntities + "\n"
								+"Lowest # Entities: " 
								+ this.lowestNumEntities + "\n");
								
			//Reset benchmark
			resetValues();
		}
		
		this.numTimesRun++;
	}
	
	private void resetValues()
	{
		this.avgRunTime = 0;
		this.totalRunTime = 0;
    	this.peakRunTime = Double.MIN_VALUE;
    	this.lowestRunTime = Double.MAX_VALUE;
		this.numTimesRun = 0;
		this.benchmarkStartTime = System.nanoTime();
		
    	this.avgNumEntities = 0;
    	this.lowestNumEntities = Integer.MAX_VALUE;
    	this.highestNumEntities = Integer.MIN_VALUE;
    	this.totalNumEntities = 0;
	}
}
