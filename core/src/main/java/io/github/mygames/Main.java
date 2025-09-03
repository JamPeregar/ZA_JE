package io.github.mygames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements Screen {
    final ZAFW game;
    protected OrthographicCamera camera;
    protected Vector3 touchPos;
    protected Texture image, bucketImage, dropImage;
    protected Sound dropSound;
    protected Music rainMusic;

    protected Array<Rectangle> raindrops;
    protected long lastDropTime;
    protected int dropsGathered = 0;

    //entity
    Rectangle bucket;

    public Main(final ZAFW dropGame) {
        game = dropGame;

        // load and configure resources
        image = new Texture("libgdx.png");
        //bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        //dropImage = new Texture(Gdx.files.internal("droplet.png")); //very-very simple

        dropImage = create_texture_scaled("droplet.png",40,50);
        bucketImage = create_texture_scaled("bucket.png",200,50);

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);
        rainMusic.setVolume(0.5f);
        rainMusic.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ZAFW.MAIN_WIDH, ZAFW.MAIN_HEIGHT);
        touchPos = new Vector3();
        //figure (dynamic?)
        bucket = new Rectangle();
        // центрируем ведро по горизонтали
        bucket.x = (float) ZAFW.MAIN_HEIGHT / 2 - (float) 64 / 2;
        // размещаем на 20 пикселей выше нижней границы экрана.
        bucket.y = 20;
        bucket.width = bucketImage.getWidth();
        bucket.height = bucketImage.getHeight();

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        ScreenUtils.clear(0.15f, 0.15f, 0.1f, 1f);

        // сообщаем SpriteBatch о системе координат
        // визуализации указанной для камеры.
        game.batch.setProjectionMatrix(camera.combined);

        //draw created drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(image, 40, 10); //logo
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        //controls
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 50;
            //bucket.y = touchPos.y - 64 / 2; // move y axis too
        }
        // убедитесь что ведро остается в пределах экрана
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;

        //spawn drops
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        //move and clean drops at out of bounds or catched in bucket
        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 128 * Gdx.graphics.getDeltaTime();
            if(raindrop.overlaps(bucket)) {
                dropSound.play(1.0f);
                iter.remove();
                dropsGathered++;
            }
            if(raindrop.y + 64 < 0) iter.remove();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        image.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    //functions
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = ZAFW.MAIN_HEIGHT - 20;
        //raindrop.width = 1f;
        //raindrop.height = 1f;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    public Texture create_texture_scaled(String file_path,int scale_w, int scale_h) {
        Pixmap pixmap_bucket = new Pixmap(Gdx.files.internal(file_path));

        Pixmap pixmap_rescale_bucket = new Pixmap(scale_w, scale_h, pixmap_bucket.getFormat());
        pixmap_rescale_bucket.drawPixmap(pixmap_bucket,
            0, 0, pixmap_bucket.getWidth(), pixmap_bucket.getHeight(),
            0, 0, pixmap_rescale_bucket.getWidth(), pixmap_rescale_bucket.getHeight()
        );
        return new Texture(pixmap_rescale_bucket);
    }
}
