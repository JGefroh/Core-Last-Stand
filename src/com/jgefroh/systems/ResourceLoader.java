package com.jgefroh.systems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Sprite;
import com.jgefroh.data.Texture;


/**
 * This class handles the loading of resources.
 * It will call appropriate resource loaders and send the data to
 * the proper systems.
 * @author Joseph Gefroh
 *
 */
public class ResourceLoader implements ISystem
{
	//TODO: Finish.
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
	private Level debugLevel = Level.ALL;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
	//////////
	// INIT
	//////////	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	a reference to the core Controlling this system
	 */
	public ResourceLoader(final Core core)
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
		isRunning = true;
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
	 * Check to see if a file is valid.
	 * @param path	the path of the file to load.
	 */
	public boolean isValidFile(final String path)
	{
		LOGGER.log(Level.INFO, "Checking validity of: " + path);

		File loadThis = new File(path);
		if(loadThis.isFile())
		{
			if(loadThis.getName().endsWith(".png")
					||loadThis.getName().endsWith(".meta"))
			{
				return true;
			}
			else
			{
				LOGGER.log(Level.WARNING, "Unrecognized file type: " + path);
				return false;
			}
		}
		else
		{
			LOGGER.log(Level.SEVERE, "File not found: " + path);
			return false;
		}
	}
	
	/**
	 * Loads metadata for a texture, stored in JSON with extension .meta
	 * @param path	the path of the file to load
	 * @return the texture meta data
	 */
	private Texture loadTextureMeta(final String path)
	{
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(path); 
		if(is!=null)
		{
			return convertStreamToMeta(is, path);
		}
		return null;
	}
	
	/**
	 * Load a texture into memory.
	 * @param path the path of the file to load
	 */
	public void loadTexture(final String path)
	{
		InputStream is = this.getClass().getClassLoader()
							.getResourceAsStream(path);  
		if(is!=null)
		{
			ByteBuffer imageData = convertStreamToBuffer(is, path);
			Texture meta = loadTextureMeta(path.replace(".png", ".meta"));
			if(imageData!=null&&meta!=null)
			{				
				core.getSystem(RenderSystem.class).createTexture(imageData, meta);					
			}
		}
		else
		{
			LOGGER.log(Level.WARNING, "Unable to locate resource: " + path);			
		}
	}
	
	public IntBuffer loadCursorFromImage(final String path)
	{
		InputStream is = this.getClass().getClassLoader()
								.getResourceAsStream(path);  
		if(is!=null)
		{
			ByteBuffer byteBuff = convertStreamToBuffer(is, path);
			IntBuffer intBuff = byteBuff.asIntBuffer();
			return intBuff;
		}
		else
		{
			LOGGER.log(Level.WARNING, "Unable to locate resource: " + path);			
		}
		return null;
	}
	
	/**
	 * Convert an input stream into a ByteBuffer
	 * @param file the image file
	 * @return	a ByteBuffer with the pixel color data of the image
	 */
	private ByteBuffer convertStreamToBuffer(final InputStream is, final String path)
	{

		try
		{
			//Explanation: http://hrboyceiii.blogspot.com/2007/02/bit-shifting-what-is-it-and-why.html
			int bpp = 4;
			//Convert the file into a buffered image.
			BufferedImage bImage = ImageIO.read(is);	
			//Turn the buffered image into an RGBArray.
			int[] rgbArray = bImage.getRGB(0, 0, bImage.getWidth(), bImage.getHeight(), null, 0, bImage.getWidth());
			//Create a buffer to hold the pixel data.
			ByteBuffer buffer = BufferUtils.createByteBuffer(bImage.getWidth()*bImage.getHeight()*bpp);
			//Load the buffer with the RGBArray data.
			for(int y=0;y<bImage.getHeight();y++)
			{
				for(int x=0;x<bImage.getWidth();x++)
				{
					//Get color data for the pixel at y
					int pixel = rgbArray[y*bImage.getWidth()+x];
					//Place data separately in buffer, in order of RGBA.
					byte r = (byte)((pixel >> 16) & 0xFF);	//Red
					byte g = (byte)((pixel >> 8) & 0xFF);	//Green
					byte b = (byte)((pixel & 0xFF));		//Blue (last)
					byte a = (byte)((pixel >> 24) & 0xFF);	//Alpha
					buffer.put(r);
					buffer.put(g);
					buffer.put(b);
					buffer.put(a);
				}
			}
			
			buffer.flip();
			LOGGER.log(Level.INFO, "Converted " + path + " to ByteBuffer.");			
			return buffer;
		}
		catch(IOException e)
		{
			LOGGER.log(Level.SEVERE, "Error creating: " + path);
			e.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			LOGGER.log(Level.SEVERE, "Error creating: " + path);
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, "Failed to convert " + path + " to ByteBuffer.");			

		return null;
	}
	
	private Texture convertStreamToMeta(final InputStream is, final String path)
	{
		Texture meta = new Texture();
		meta.setPath(path.replace(".meta", ".png"));
		
		try
		{
			JsonFactory jFact = new JsonFactory();
			JsonParser jParser = jFact.createJsonParser(is);	//TODO: Dep?
			
			HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();
			Sprite sprite = new Sprite();
			String field;
			String value;
			
			jParser.nextToken();
			while(jParser.nextToken()!=null)
			{	
				field = jParser.getCurrentName();
				value = jParser.getValueAsString();
				if(field!=null&&value!=null)
				{
					//if one of the regular fields...
					if(field.equals("height"))
					{
						meta.setImageHeight(jParser.getValueAsInt());
					}
					else if(field.equals("width"))
					{
						meta.setImageWidth(jParser.getValueAsInt());
					}
				}
				else if(jParser.getCurrentToken()==JsonToken.START_ARRAY)
				{//If it is the beginning of the sprite definitions...
					while(jParser.nextToken()!=null)
					{//While there are objects to parse in the array...
						if(jParser.getCurrentToken()==JsonToken.START_OBJECT)
						{
							sprite = new Sprite();
						}
						else if(jParser.getCurrentToken()==JsonToken.END_OBJECT)
						{
							sprites.put(sprite.getSpriteID(), sprite);
						}
						else if(jParser.getCurrentToken()==JsonToken.END_ARRAY)
						{
							meta.setSprites(sprites);
						}
						else
						{
							field = jParser.getCurrentName();
							value = jParser.getValueAsString();
							if(field!=null&&value!=null)
							{
								if(field.equals("id"))
								{
									sprite = new Sprite();
									sprite.setSpriteID(jParser.getValueAsInt());
								}
								else if(field.equals("name"))
								{
									sprite.setName(jParser.getValueAsString());
								}
								else if(field.equals("xmin"))
								{
									sprite.setXMin(jParser.getValueAsInt());
								}
								else if(field.equals("xmax"))
								{
									sprite.setXMax(jParser.getValueAsInt());
								}
								else if(field.equals("ymin"))
								{
									sprite.setYMin(jParser.getValueAsInt());
								}
								else if(field.equals("ymax"))
								{
									sprite.setYMax(jParser.getValueAsInt());
								}
							}
						}
					}
				}
			}//while loop
			return meta;
		}
		catch(JsonParseException e)
		{
			LOGGER.log(Level.SEVERE, "Error parsing " + path);
			e.printStackTrace();
			System.exit(-1);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
