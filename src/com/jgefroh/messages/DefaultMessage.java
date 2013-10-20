package com.jgefroh.messages;

import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;

/**
 * Enum that contains all of the message IDs used by the systems.
 * 
 * @author Joseph Gefroh
 *
 */
public enum DefaultMessage implements IMessage {
	
	COMMAND_ADJUST_SCORE,
	COMMAND_ADVANCE_FRAME,
	COMMAND_BUY,
	COMMAND_CHANGE_HEALTH,
	COMMAND_CHANGE_HEALTH_MAX,
	COMMAND_CHANGE_SHIELD_MAX,
	COMMAND_CREATE,
	COMMAND_DAMAGE,
	COMMAND_DETONATE,
	COMMAND_FIRE,
	COMMAND_GENERATE_FORCE,
	COMMAND_INQUIRE,
	COMMAND_RESET_GAME,
	COMMAND_SHIELD_ACTIVE,
	COMMAND_TOGGLE_WIREFRAME,
	COMMAND_USE_ABILITY,
	COMMAND_MOVE_UP,
	COMMAND_MOVE_DOWN,
	COMMAND_MOVE_LEFT,
	COMMAND_MOVE_RIGHT,
	INPUT_FIRE1_PRESSED,
	INPUT_FIRE1_RELEASED,
	INPUT_SHIELD_PRESSED,
	INPUT_SHIELD_RELEASED,
	INPUT_SPECIAL_RELEASED,
	DATA_HEALTH,
	DATA_INPUT_CURSOR_POSITION,
	DATA_NATIVE_RESOLUTION,
	DATA_SCORE,
	DATA_SHIELD,
	DATA_UPGRADE_DESC,
	DATA_WINDOW_RESOLUTION,
	EVENT_DESTROYING_ENTITY,
	EVENT_ENTITY_WITHIN_BOUNDS,
	EVENT_EXPLOSION_CONTACT,
	EVENT_IN_RANGE_OF_TARGET,
	EVENT_PLAYER_CREATED,
	INPUT_MOUSE0_PRESSED,
	INPUT_MOUSE0_RELEASED,
	REQUEST_CURSOR_POSITION,
	REQUEST_DESTROY,
	REQUEST_HEALTH,
	REQUEST_NATIVE_RESOLUTION,
	REQUEST_SCORE,
	REQUEST_SHIELD,
	REQUEST_WINDOW_RESOLUTION
	;

	public enum COMMAND_ADJUST_SCORE implements IPayload {
		AMOUNT
	}

	public enum COMMAND_CHANGE_SHIELD_MAX implements IPayload {
		ENTITY_ID,
		AMOUNT
	}
	public enum COMMAND_SHIELD_ACTIVE implements IPayload {
		ENTITY_ID,
		IS_ACTIVE
	}

	public enum DATA_WINDOW_RESOLUTION implements IPayload {
		WINDOW_WIDTH, WINDOW_HEIGHT
	}

	public enum EVENT_WINDOW_RESIZED implements IPayload {
		WINDOW_WIDTH, WINDOW_HEIGHT
	}

	public enum COMMAND_CHANGE_HEALTH_MAX implements IPayload {
		ENTITY_ID, AMOUNT
	}

	public enum COMMAND_CHANGE_HEALTH implements IPayload {
		ENTITY_ID, AMOUNT
	}

	public enum COMMAND_INQUIRE implements IPayload {
		INQUIRY_ID
	}

	public enum COMMAND_BUY implements IPayload {
		PRODUCT_ID
	}

	public enum EVENT_PLAYER_CREATED implements IPayload {
		PLAYER_ENTITY_ID
	}

	public enum DATA_INPUT_CURSOR_POSITION implements IPayload {
		MOUSE_X, MOUSE_Y
	}

	public enum DATA_SCORE implements IPayload {
		SCORE_CUR
	}

	public enum DATA_UPGRADE_DESC implements IPayload {
		DESC
	}

	public enum DATA_HEALTH implements IPayload {
		ENTITY_ID, HEALTH_CUR, HEALTH_MAX
	}

	public enum DATA_SHIELD implements IPayload {
		ENTITY_ID, SHIELD_CUR, SHIELD_MAX
	}

	public enum REQUEST_HEALTH implements IPayload {
		ENTITY_ID
	}

	public enum REQUEST_SHIELD implements IPayload {
		ENTITY_ID
	}

	public enum COMMAND_GENERATE_FORCE implements IPayload {
		ENTITY_ID, VECTOR_MAG, VECTOR_ANG
	}

	public enum EVENT_EXPLOSION_CONTACT implements IPayload {
		SOURCE_ID, VICTIM_ID
	}

	public enum COMMAND_DETONATE implements IPayload {
		ENTITY_ID
	}

	public enum REQUEST_DESTROY implements IPayload {
		ENTITY_ID
	}

	public enum COMMAND_DAMAGE implements IPayload {
		SOURCE_ENTITY_ID, VICTIM_ENTITY_ID
	}

	public enum COMMAND_ADVANCE_FRAME implements IPayload {
		ENTITY_ID
	}

	public enum COMMAND_FIRE implements IPayload {
		ENTITY_ID, IS_FIRE_REQUESTED
	}

	public enum EVENT_IN_RANGE_OF_TARGET implements IPayload {
		SOURCE_ENTITY_ID, IS_IN_RANGE
	}

	public enum COMMAND_CREATE implements IPayload {
		TYPE_TO_CREATE, OWNER_ID, ANGLE
	}

	public enum COMMAND_USE_ABILITY implements IPayload {
		ENTITY_ID
	}

	public enum EVENT_ENTITY_WITHIN_BOUNDS implements IPayload {
		ENTITY_ID, IS_WITHIN_BOUNDS
	}

	public enum EVENT_DESTROYING_ENTITY implements IPayload {
		ENTITY_ID, REASON
	}

	public enum DATA_NATIVE_RESOLUTION implements IPayload {
		NATIVE_WIDTH, NATIVE_HEIGHT
	}
}
