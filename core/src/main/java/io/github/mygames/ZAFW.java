package io.github.mygames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ZAFW extends Game {

    public static int MAIN_WIDH = 800;
    public static int MAIN_HEIGHT = 480;
    SpriteBatch batch;
    ShapeDrawer shaper;
    BitmapFont font;
    public World world;
    public OrthographicCamera camera;
    
    /** Get game instance to get stuff from here **/
    public static ZAFW self() {
        return (ZAFW) Gdx.app.getApplicationListener();
    }

    public void create() {
        batch = new SpriteBatch();
        shaper = new ShapeDrawer(batch);
        // libGDX по умолчанию использует Arial шрифт.
        font = new BitmapFont();
        camera = new OrthographicCamera();
        world = new World(Vector2.Zero, true);
        
        camera.setToOrtho(false, ZAFW.MAIN_WIDH, MAIN_HEIGHT);

        this.setScreen(new MainMenuScreen(this));

        Box2D.init(); //not necessary but for backwards compatibility
    }

    public void render() {
        super.render(); // важно!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
