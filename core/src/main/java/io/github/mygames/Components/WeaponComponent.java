/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.mygames.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.mygames.Components.ProjectileComponent.ProjectileType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author Admin
 */
public class WeaponComponent implements Component{
    static final String filepath = "cfg/weapons.ini";
    
    public String weaponName;
    public int fireRate;          // Выстрелов в секунду
    public float lastShotTime;      // Время последнего выстрела
    public int damage;              // Урон за выстрел
    public float range;             // Дальность стрельбы
    //public float aimangle;          // угол направления оружия
    public WeaponType weaponType;   // Тип оружия
    public ProjectileType projectileType = ProjectileType.BULLET; // Тип снаряда
   // public int modelid;
    public TextureRegion texture;
    public boolean isAutomatic;     // Автоматическое оружие?
    public boolean isTargeting; //in case when char run and shoot
    /**Flag should weapon shoot now**/
    public boolean make_shoot;
    
    /**Point weapon is shooting from**/
    public Vector2 firePoint = new Vector2(); // Точка выстрела относительно центра entity
    /**Point weapon aimed at**/
    public Vector2 aimPoint = new Vector2(); // 
    public Vector2 aim_vec = new Vector2(); //
    
    //public String projectileType;   // Тип снаряда (пуля, ракета)
    public float projectileSpeed;
    
    public enum WeaponType {
    UNARMED,
    PISTOL,
    ASSAULT_RIFLE,
    RIFLE,
    THROWABLE,
    RPG
}
    
    public WeaponComponent() {weaponType = WeaponType.UNARMED;}

    public void init_weapon(WeaponType weapon) {
        switch (weapon) {
            case UNARMED:
                weaponType = WeaponType.UNARMED;
                projectileType = ProjectileType.MELEE;
                break;
            case PISTOL:
                weaponType = WeaponType.PISTOL;
                projectileType = ProjectileType.BULLET;
                fireRate = 1;
                damage = 10;
                range = 300;
                texture = TextureComponent.getSimpleModelFromSource("models/test_pistol.png");
                break;
            case ASSAULT_RIFLE:
                weaponType = WeaponType.ASSAULT_RIFLE;
                projectileType = ProjectileType.BULLET;
                fireRate = 8;
                damage = 5;
                range = 600;
                texture = TextureComponent.getSimpleModelFromSource("models/test_rifle.png");
                break;
            default:
                weaponType = WeaponType.UNARMED;
                fireRate = 1;
                damage = 10;
                range = 100;
        }
    }
    /**Get weapon's data from xml
     * @return Weapon component with data**/
    public static WeaponComponent getWeaponDataXML(String weaponname) {
        try {
            // Создаем парсер
            //DocumentBuilderFactory factory = 
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            
            // Загружаем XML файл
            Document document = builder.parse(new File("weapons.xml"));
            document.getDocumentElement().normalize();
            
            // Получаем все элементы weapon
            NodeList weaponList = document.getElementsByTagName("weapon");
            
            // Создаем список для хранения оружия и возвращаем нужный
            //List<WeaponComponent> weapons = new ArrayList<>();
            
            for (int i = 0; i < weaponList.getLength(); i++) {
                Node node = weaponList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (    !weaponname.equals(getElementValue(element, "name")) ) {return new WeaponComponent();}
                    
                    // Создаем объект Weapon и заполняем его
                    WeaponComponent weapon = new WeaponComponent();
                    weapon.weaponName = getElementValue(element, "name");
                    weapon.fireRate = Integer.parseInt(getElementValue(element, "fireRate"));
                    weapon.damage = Integer.parseInt(getElementValue(element, "damage"));
                    weapon.range = Integer.parseInt(getElementValue(element, "range"));
                    weapon.weaponType = WeaponType.valueOf(getElementValue(element, "weaponType"));
                    weapon.projectileType = ProjectileType.valueOf(getElementValue(element, "projectileType"));
                    weapon.isAutomatic = getElementValue(element, "isAutomatic").equals("1");
                    
                    //weapons.add(weapon);
                    return weapon;
                }
            }
            
        } catch (Exception e) {
            
        }
        return new WeaponComponent();
    }
    
    private static String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return "";
    }

}
