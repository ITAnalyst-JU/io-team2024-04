package main.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {

    private Vector2 currentVelocity = new Vector2(0, 0);

    private float velocityLimit = 60 * 8;
    private float gravity = 60 * 1.8f;

    public Player(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    private void update(float timeDelta) {
        currentVelocity.y -= gravity * timeDelta;

        if (currentVelocity.y < -velocityLimit) {
            currentVelocity.y = -velocityLimit;
        }
        if (currentVelocity.y > velocityLimit) {
            currentVelocity.y = velocityLimit;
        }

        setX(getX() + currentVelocity.x * timeDelta);
        setY(getY() + currentVelocity.y * timeDelta);
    }
}
