/**
 * InfoPacks control access between systems and components.
 * 
 * InfoPacks act as middle-men between systems and components.
 * This accomplishes several things:
 * It easily allows for entities to change their component composition between
 * systems.
 * It reduces the number of checks necessary to determine if an entity should
 * be processed by a system or not.
 * Without InfoPacks, a check would have to be performed for every entity
 * every time a system needed to process something
 * in order to determine if the system should process the entity.
 * 	
 * @author Joseph Gefroh
 */
package com.jgefroh.infopacks;