package ghost.run.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected Texture texture;
    protected float x, y;
    protected float velocityX, velocityY;
    protected float width, height;
    protected boolean isActive;

    private float rotation = 0; // Gestion de la rotation

    public Entity(float startX, float startY, String texturePath) {
        this.x = startX;
        this.y = startY;
        this.texture = new Texture(texturePath);
        this.velocityX = 0;
        this.velocityY = 0;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.isActive = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, width, height); // x, y, width, et height sont des attributs de Entity
    }


    public void update(float deltaTime) {
        if (!isActive) return;

        // Mise à jour de la position
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
    }

    public void render(SpriteBatch batch) {
        if (!isActive || batch == null) return;
        batch.draw(texture, x, y, width / 2, height / 2, width, height, 1, 1, rotation, 0, 0, (int) width, (int) height, false, false);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    public void reset(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isActive = true;
        this.rotation = 0;
    }

    public void applyAcceleration(float accelerationX, float accelerationY, float deltaTime) {
        this.velocityX += accelerationX * deltaTime;
        this.velocityY += accelerationY * deltaTime;
    }

    public boolean collidesWith(Entity other) {
        return getBounds().overlaps(other.getBounds());
    }

    public boolean isNear(Entity other, float threshold) {
        float distanceX = Math.abs(this.x - other.getX());
        float distanceY = Math.abs(this.y - other.getY());
        return distanceX <= threshold && distanceY <= threshold;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    // Getters et setters
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public float getVelocityX() { return velocityX; }
    public void setVelocityX(float velocityX) { this.velocityX = velocityX; }

    public float getVelocityY() { return velocityY; }
    public void setVelocityY(float velocityY) { this.velocityY = velocityY; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}
