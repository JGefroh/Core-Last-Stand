package com.jgefroh.systems;


import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Sprite;
import com.jgefroh.data.Texture;
import com.jgefroh.infopacks.RenderInfoPack;


/**
 * Handles the rendering and drawing of entities on the screen.
 * 
 * 
 * Date: 12JUN13
 * @author Joseph Gefroh
 */
public class RenderSystem implements ISystem
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
	
	/**Holds the texture metadata.*/
	private HashMap<Integer, Texture> textures;
	
	/**Holds texture IDs associated with an image name.*/
	private HashMap<String, Integer> idMan;
	
	/**Flag that indicates whether wireframe mode is enabled.*/
	private boolean wireframeEnabled = false;
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public RenderSystem(final Core core)
	{		
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	/**
	 * Initialize OpenGL settings.
	 */
	private void initOpenGL()
	{
		LOGGER.log(Level.FINE, "Setting default OpenGL values.");

		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
 		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, 1366, 768);
		GL11.glOrtho(0, 1366, 768, 0, -1, 1);
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		LOGGER.log(Level.FINE, "Setting system values to default.");
		initOpenGL();
		textures = new HashMap<Integer, Texture>();
		idMan = new HashMap<String, Integer>();
		isRunning = true;
		core.setInterested(this, "WINDOW_RESIZED");
		core.setInterested(this, "WINDOW_WIDTH");
		core.setInterested(this, "WINDOW_HEIGHT");
		core.setInterested(this, "TOGGLE_WIREFRAME");
	}
	
	@Override
	public void start() 
	{
		LOGGER.log(Level.INFO, "System started.");
		core.send("REQUEST_WINDOW_WIDTH", "");
		core.send("REQUEST_WINDOW_HEIGHT", "");
		isRunning = true;
	}

	@Override
	public void work(final long now)
	{
		if(isRunning)
		{	
			render();
		}
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
		LOGGER.log(Level.FINE, "Wait interval set to: " + waitTime + " ms");
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

		if(id.equals("WINDOW_RESIZED"))
		{
			resizeDrawableArea(Display.getWidth(), Display.getHeight());
		}
		else if(id.equals("WINDOW_WIDTH"))
		{
			resizeDrawableArea(Display.getWidth(), Display.getHeight());
		}
		else if(id.equals("WINDOW_HEIGHT"))
		{
			resizeDrawableArea(Display.getWidth(), Display.getHeight());
		}
		else if(id.equals("TOGGLE_WIREFRAME"))
		{
			toggleWireframeMode();
		}
	}
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the uMin texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the uMin texture coordinate of the sprite, 0 if error
	 */
	private float getUMin(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getUMin(spriteIndex);
		}
		return 0.0f;
	}
	
	/**
	 * Get the uMax texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the uMax texture coordinate of the sprite, 0 if error
	 */
	private float getUMax(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getUMax(spriteIndex);
		}
		return 0.0f;
	}
	
	/**
	 * Get the vMin texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the vMin texture coordinate of the sprite, 0 if error
	 */
	private float getVMin(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getVMin(spriteIndex);
		}
		return 0;
	}
	
	/**
	 * Get the vMax texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the vMax texture coordinate of the sprite, 0 if error
	 */
	private float getVMax(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getVMax(spriteIndex);
		}
		return 0.0f;
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Render the entities that have render components.
	 */
	public void render()
	{
		newFrame();
		Iterator<RenderInfoPack> packs = 
				core.getInfoPacksOfType(RenderInfoPack.class);
		
		while(packs.hasNext())
		{
			RenderInfoPack pack = packs.next();
			
			if(pack.isDirty()==false)
			{
				if(pack.getTextureID()==-1)
				{//If the component doesn't know its textureID...
					Integer id = idMan.get(pack.getPath());
					if(id!=null)
					{
						pack.setTextureID(id);
					}
					else
					{	
						//If ID doesn't exist, texture isn't loaded.
						//Ask resource loader to load texture here to enable
						//"streaming".
						LOGGER.log(Level.WARNING, 
								"Draw requested with unloaded texture: " 
								+ pack.getPath());
						idMan.put(pack.getPath(), -1);	//Avoid repeat log msgs.
					}
				}

				drawQuadAt(pack.getTextureID(), 
						pack.getXPos()-pack.getWidth()/2, pack.getYPos()-pack.getHeight()/2, pack.getZPos(),
						pack.getWidth(), pack.getHeight(),
						pack.getBearing(),
						getUMin(pack.getTextureID(), pack.getSpriteID()),
						getUMax(pack.getTextureID(), pack.getSpriteID()),
						getVMin(pack.getTextureID(), pack.getSpriteID()),
						getVMax(pack.getTextureID(), pack.getSpriteID()));
			}
		}
	}

	/**
	 * Clear the previous frame.
	 */
	private void newFrame()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	/**
	 * Draw a textured quad.
	 * @param x			the x-position on screen to draw.
	 * @param y			the y-position on screen to draw.
	 * @param z			the z-position on screen to draw. (NOT USED)
	 * @param width		the width of the quad
	 * @param height	the height of the quad
	 * @param uMin		the u-texture coordinate minimum
	 * @param uMax		the u-texture coordinate maximum
	 * @param vMin		the v-texture coordinate minimum
	 * @param vMax		the v-texture coordinate maximum
	 */
	private void drawQuadAt(final int textureID, 
							final double x, final double y, final double z, 
							final double width, final double height,
							final int rotation,
							final float uMin, final float uMax, final float vMin, final float vMax)
	{
		//GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		//(Bottom up translations)
		GL11.glPushMatrix();

		GL11.glTranslated(x, y, 0);	//Move to specified draw location
		GL11.glTranslated(width/2, height/2, -1);	//Center
		GL11.glRotatef(rotation, 0, 0, 1);			//Rotate
		GL11.glTranslated(-width/2, -height/2, 1);	//Center
		GL11.glBegin(GL11.GL_QUADS);
		{

			GL11.glTexCoord3d(uMin, vMax, z);	//Bottom Left
			GL11.glVertex3d(0, 0, z);		//Top Left

			GL11.glTexCoord3d(uMin, vMin, z);	//Top Left
			GL11.glVertex3d(width, 0, z);	//Top Right

			GL11.glTexCoord3d(uMax, vMin, z);	//Top Right
			GL11.glVertex3d(width, height, z);	//Bottom Right

			GL11.glTexCoord3d(uMax, vMax, z);	//Bottom Right
			GL11.glVertex3d(0, height, z);	//Bottom Left
		}

		GL11.glEnd();
		GL11.glPopMatrix();
	}

	
	/**
	 * Load a texture and its metadata into the Render system.
	 * @param buffer	the buffer containing the pixel data of the texture
	 * @param meta		the metadata related to the texture
	 */
	public void createTexture(final ByteBuffer buffer, final Texture meta)
	{
		//Convert texture to string? BASE64?
		LOGGER.log(Level.FINE, "Creating OpenGL texture for " + meta.getPath());

		int textureID = GL11.glGenTextures();
        meta.setTextureID(textureID);
        
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        
        int width = meta.getImageWidth();
        int height = meta.getImageHeight();
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        
        calcTextureCoordinates(meta);
        textures.put(textureID, meta);
        idMan.put(meta.getPath(), textureID);
        LOGGER.log(Level.FINE, "Texture " + meta.getPath() + " loaded (ID: "
        				+ meta.getTextureID() + ").");
	}
	
	/**
	 * Go through a texture's sprites and calculate their texture coordinates.
	 * @param meta	the Texture you want to go through
	 */
	public void calcTextureCoordinates(final Texture meta)
	{
		LOGGER.log(Level.FINE, "Calculating UV coordinates for " 
						+ meta.getPath());
		Iterator<Sprite> sprites = meta.getSpriteIterator();
		while(sprites.hasNext())
		{
			int id = sprites.next().getSpriteID();
			
			float uMin = ((float)meta.getXMin(id))/meta.getImageWidth();
			float uMax = ((float)meta.getXMax(id))/meta.getImageWidth();
			float vMin = ((float)meta.getYMin(id))/meta.getImageHeight();
			float vMax = ((float)meta.getYMax(id))/meta.getImageHeight();
			meta.setSpriteUMin(id, uMin);
			meta.setSpriteUMax(id, uMax);
			meta.setSpriteVMin(id, vMin);
			meta.setSpriteVMax(id, vMax);
		}
	}
	
	/**
	 * Resizes the render area and viewport to maintain compatibility with
	 * all window sizes.
	 * @param width		the width of the window
	 * @param height	the height of the window
	 */
	private void resizeDrawableArea(final int width, final int height)
	{
		GL11.glMatrixMode(GL11.GL_VIEWPORT);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1366, 768, 0, -1, 100);
	}
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
	
	/**
	 * Toggles the display of wireframes.
	 */
	private void toggleWireframeMode()
	{
		if(this.wireframeEnabled==false)
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			this.wireframeEnabled = true;
		}
		else
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			this.wireframeEnabled = false;
		}
	}
}
