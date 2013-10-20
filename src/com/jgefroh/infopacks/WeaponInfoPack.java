package com.jgefroh.infopacks;

import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Weapon;


/**
 * @author Joseph Gefroh
 */
public class WeaponInfoPack extends AbstractInfoPack {

	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private WeaponComponent wc;
	

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public WeaponInfoPack() {
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(WeaponComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.wc = entity.getComponent(WeaponComponent.class);
		
		if (wc == null) {
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}


	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////
	
	public Weapon getCurrentWeapon() {
		return wc.getCurrentWeapon();
	}

	public long getConsecutiveShotDelay() {
		return wc.getConsecutiveShotDelay();
	}

	public long getLastFired() {
		return wc.getLastFired();
	}

	public boolean isFireRequested() {
		return wc.isFireRequested();
	}

	public int getFireMode() {
		return wc.getFireMode();
	}

	public int getDamage() {
		return wc.getDamage();
	}

	public int getMaxRange() {
		return wc.getMaxRange();
	}

	public int getBurstSize() {
		return wc.getBurstSize();
	}

	public int getShotsThisBurst() {
		return wc.getShotsThisBurst();
	}

	public boolean isInBurst() {
		return wc.isInBurst();
	}

	public long getBurstDelay() {
		return wc.getBurstDelay();
	}

	public int getShotType() {
		return wc.getShotType();
	}

	public int getNumShots() {
		return wc.getNumShots();
	}

	public double getRecoilCur() {
		return wc.getRecoilCur();
	}

	public double getRecoilInc() {
		return wc.getRecoilInc();
	}

	public double getRecoilDec() {
		return wc.getRecoilDec();
	}

	public double getRecoilMax() {
		return wc.getRecoilMax();
	}

	public double getRecoilMin() {
		return wc.getRecoilMin();
	}


	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setInterval(final long interval) {
		wc.setConsecutiveShotDelay(interval);
	}

	public void setLastFired(final long lastFired) {
		wc.setLastFired(lastFired);
	}

	public void setFireRequested(final boolean fireRequested) {
		wc.setFireRequested(fireRequested);
	}

	public void setCurrentWeapon(final String weaponName) {
		wc.setCurrentWeapon(weaponName);
	}

	public void setDamage(final int damage) {
		wc.setDamage(damage);
	}

	public void setMaxRange(final int maxRange) {
		wc.setMaxRange(maxRange);
	}

	public void setFireMode(final int fireMode) {
		wc.setFireMode(fireMode);
	}

	public void setBurstSize(final int burstSize) {
		wc.setBurstSize(burstSize);
	}

	public void setShotsThisBurst(final int shotsThisBurst) {
		wc.setShotsThisBurst(shotsThisBurst);
	}

	public void setInBurst(final boolean isInBurst) {
		wc.setInBurst(isInBurst);
	}

	public void setBurstDelay(final long burstDelay) {
		wc.setBurstDelay(burstDelay);
	}

	public void setShotType(final int shotType) {
		wc.setShotType(shotType);
	}
}
