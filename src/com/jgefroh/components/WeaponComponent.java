package com.jgefroh.components;

import java.util.ArrayList;
import java.util.List;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.data.Weapon;

/**
 * Contains data allowing an entity to have and use weapons.
 * @author Joseph Gefroh
 */
public class WeaponComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**The currently equipped weapon*/
    private Weapon currentWeapon;

    /**The weapons this entity owns.*/
    private List<Weapon> weapons = new ArrayList<Weapon>();

    /**The amount of time to wait in-between shots, in milliseconds.*/
    private long consecutiveShotDelay;

    /**The time a weapon was last fired.*/
    private long lastFired;

    /**Flag that indicates a weapon fire request.*/
    private boolean isFireRequested;

    /**Flag that indicates the weapon is in the middle of a burst.*/
    private boolean isInBurst;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    
    /**
     * Creates a new instance of this {@code Component}.
     */
    public WeaponComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the update interval of the component.
     * @return	the time, in ms, to wait before attempting an update
     */
    public long getConsecutiveShotDelay() {
        return this.consecutiveShotDelay;
    }

    /**
     * Gets the time a weapon was last fired
     * @return	the time, in ms, the component was last updated
     */
    public long getLastFired() {
        return this.lastFired;
    }

    /**
     * Gets the currently equipped weapon.
     * @return	the currently equipped weapon
     */
    public Weapon getCurrentWeapon() {
        return this.currentWeapon;
    }

    /**
     * Gets whether there was a request to fire the weapon.
     * @return	true if firing was requested; false otherwise
     */
    public boolean isFireRequested() {
        return this.isFireRequested;
    }


    /**
     * Gets the amount of damage the current weapon does.
     * @return	the amount of damage the current weapon does
     */
    public int getDamage() {
        if (currentWeapon != null) {
            return currentWeapon.getDamage();
        }
        return 0;
    }

    /**
     * Gets the maximum range of the weapon
     * @return	the maximum range of the weapon
     */
    public int getMaxRange() {
        if (currentWeapon != null) {
            return currentWeapon.getMaxRange();
        }
        return 0;
    }

    /**
     * Gets the fire mode of the current weapon.
     * @return	the fire mode
     */
    public int getFireMode() {
        if (currentWeapon != null) {
            return currentWeapon.getFireMode();
        }
        return 0;
    }

    /**
     * Gets the number of shots fired in a single burst by this {@code Weapon}.
     * @return	the burst size
     */
    public int getBurstSize() {
        if (currentWeapon != null) {
            return currentWeapon.getBurstSize();
        }
        return 0;
    }

    /**
     * Gets the number of shots fired so far in the burst.
     * @return	the number of shots fired within the last burst
     */
    public int getShotsThisBurst() {
        if (currentWeapon != null) {
            return currentWeapon.getShotsThisBurst();
        }
        return 0;
    }

    /**
     * Gets the flag that indicates the weapon is in a burst.
     * @return	true if in a burst; false otherwise
     */
    public boolean isInBurst() {
        return this.isInBurst;
    }

    /**
     * Gets the amount of time the weapon must wait after a burst to fire.
     * @return	the amount of time, in ms
     */
    public long getBurstDelay() {
        if (currentWeapon != null) {
            return currentWeapon.getBurstDelay();
        }
        return 0;
    }

    /**
     * Gets the type of shot that is fired.
     * @return	the shot type
     */
    public int getShotType() {
        if (currentWeapon != null) {
            return currentWeapon.getShotType();
        }
        return 0;
    }

    /**
     * Gets the number of shots fired per trigger pull.
     * @return	the number of shots
     */
    public int getNumShots() {
        if (currentWeapon != null) {
            return currentWeapon.getNumShots();
        }
        return 0;
    }

    /**
     * Gets the current amount of recoil this {@code Weapon} has.
     * @return	the current amount of spread
     */
    public double getRecoilCur() {
        if (currentWeapon != null) {
            return currentWeapon.getRecoilCur();
        }
        return 0;
    }

    /**
     * Gets the amount the recoil is incremented by, per shot.
     * @return	the increment amount
     */
    public double getRecoilInc() {
        if (currentWeapon != null) {
            return currentWeapon.getRecoilInc();
        }
        return 0;
    }

    /**
     * Gets the amount the recoil is decremented by, per resting period.
     * @return	the decrement amount
     */
    public double getRecoilDec() {
        if (currentWeapon != null) {
            return currentWeapon.getRecoilDec();
        }
        return 0;
    }

    /**
     * Gets the maximum amount of recoil.
     * @return	the max recoil amount
     */
    public double getRecoilMax() {
        if (currentWeapon != null) {
            return currentWeapon.getRecoilMax();
        }
        return 0;
    }

    /**
     * Gets the minimum amount of recoil.
     * @return	the minimum recoil amount
     */
    public double getRecoilMin() {
        if (currentWeapon != null) {
            return currentWeapon.getRecoilMin();
        }
        return 0;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Set the update interval of the component.
     * @param interval	the time, in ms, to wait before attempting an update
     */
    public void setConsecutiveShotDelay(final long consecutiveShotDelay) {
        this.consecutiveShotDelay = consecutiveShotDelay;
    }

    /**
     * Sets the time a weapon was last fired.
     * @param lastUpdated	the time, in ms, the component was last updated
     */
    public void setLastFired(final long lastFired) {
        this.lastFired = lastFired;
    }

    /**
     * Sets the flag that indicates whether there was a request to fire.
     * @param	isFireRequested	true if there is a request to fire; false if not
     */
    public void setFireRequested(final boolean isFireRequested) {
        this.isFireRequested = isFireRequested;
    }

    /**
     * Sets the currently used weapon of the entity.
     * @param weapon	the name of the weapon
     */
    public void setCurrentWeapon(final String weapon) {
        for (Weapon each : weapons) {
            if (each.getName().equals(weapon)) {
                this.currentWeapon = each;
                setConsecutiveShotDelay(currentWeapon.getConsecutiveShotDelay());
                setFireRequested(false);
            }
        }
    }

    /**
     * Sets the amount of damage the currently equipped weapon does
     * @param damage	the amount of damage
     */
    public void setDamage(final int damage) {
        if (currentWeapon != null) {
            this.currentWeapon.setDamage(damage);
        }
    }

    /**
     * Sets the maximum range of the currently equipped weapon
     * @param maxRange	the maximum range
     */
    public void setMaxRange(final int maxRange) {
        if (currentWeapon != null) {
            this.currentWeapon.setMaxRange(maxRange);
        }
    }

    /**
     * Sets the fire mode of the currently equipped weapon.
     * @param fireMode	the fire mode to set
     */
    public void setFireMode(final int fireMode) {
        if (currentWeapon != null) {
            this.currentWeapon.setFireMode(fireMode);
        }
    }

    /**
     * Sets the number of shots fired in a single burst by this {@code Weapon}.
     * @param burstSize	the burst size
     */
    public void setBurstSize(final int burstSize) {
        if (currentWeapon != null) {
            this.currentWeapon.setBurstSize(burstSize);
        }
    }

    /**
     * Sets the number of shots fired so far in the burst.
     * @param shotsThisBurst	the number of shots fired so far
     */
    public void setShotsThisBurst(final int shotsThisBurst) {
        if (currentWeapon != null) {
            this.currentWeapon.setShotsThisBurst(shotsThisBurst);
        }
    }

    /**
     * Sets the flag that indicates the weapon is in the middle of a burst.
     * @param isInBurst	true if in a burst; false otherwise
     */
    public void setInBurst(final boolean isInBurst) {
        this.isInBurst = isInBurst;
    }

    /**
     * Sets the amount of time the weapon must wait after a burst to fire.
     * @param burstDelay	the amount of time, in ms
     */
    public void setBurstDelay(final long burstDelay) {
        if (currentWeapon != null) {
            this.currentWeapon.setBurstDelay(burstDelay);
        }
    }

    /**
     * Sets the type of shot this weapon fires.
     * @param shotType	the type of shot
     */
    public void setShotType(final int shotType) {
        if (currentWeapon != null) {
            this.currentWeapon.setShotType(shotType);
        }
    }


    //////////////////////////////////////////////////
    // Methods
    //////////////////////////////////////////////////
    
    /**
     * Adds a weapon to the inventory.
     * @param weapon the weapon to add
     */
    public void addWeapon(final Weapon weapon) {
        weapons.add(weapon);
    }
}
