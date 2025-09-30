/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import io.github.mygames.Components.enums.ProjectileType;
import io.github.mygames.Components.enums.WeaponType;
import static io.github.mygames.Components.enums.WeaponType.AK47;
import static io.github.mygames.Components.enums.WeaponType.UNARMMED;

/**
 *
 * @author Admin
 */
public class WeaponComponent implements Component{
    public int fireRate;          // Выстрелов в секунду
    public float lastShotTime;      // Время последнего выстрела
    public int damage;              // Урон за выстрел
    public float range;             // Дальность стрельбы
    public WeaponType weaponType;   // Тип оружия
    public ProjectileType projectileType = ProjectileType.BULLET; // Тип снаряда
    public boolean isAutomatic;     // Автоматическое оружие?
    
    // Для hitscan оружия
    public Vector2 firePoint = new Vector2(); // Точка выстрела относительно центра entity
    
    // Для projectile оружия
    //public String projectileType;   // Тип снаряда (пуля, ракета)
    public float projectileSpeed;
    
    public WeaponComponent() {weaponType = UNARMMED;}

    public void init_weapon(WeaponType weapon) {
        switch (weapon) {
            case UNARMMED:
                weaponType = WeaponType.UNARMMED;
                projectileType = ProjectileType.MELEE;
                break;
            case COLT45:
                weaponType = WeaponType.COLT45;
                projectileType = ProjectileType.BULLET;
                fireRate = 1;
                damage = 10;
                range = 100;
                break;
            case AK47:
                weaponType = WeaponType.AK47;
                projectileType = ProjectileType.BULLET;
                fireRate = 4;
                damage = 5;
                range = 300;
                break;
            default:
                weaponType = WeaponType.UNARMMED;
                fireRate = 1;
                damage = 10;
                range = 100;
        }
    }
    
    

}
