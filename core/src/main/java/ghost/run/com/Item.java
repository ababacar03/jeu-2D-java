package ghost.run.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Item extends Entity {
    private final ItemType type; // Type d'objet : "coin", "health", etc.
    private boolean disposed = false; // Indique si l'objet est collecté

    private float animationTimer = 0f;
    private final float frameDuration = 0.1f; // Durée de chaque frame en secondes
    private Texture[] animationFrames; // Les textures pour l'animation

    public Item(float startX, float startY, String texturePath, ItemType type) {
        super(startX, startY, texturePath);
        this.type = type;

        // Charger les textures pour l'animation (exemple pour une pièce)
        if (type == ItemType.COIN) {
            animationFrames = new Texture[] {
                new Texture("piece2.png"),
                new Texture("piece2.png"),
                new Texture("piece2.png"),
                new Texture("piece2.png")
            };
        }
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public void applyEffect(Player player) {
        switch (type) {
            case COIN:
                player.addCoin(10); // Ajouter 10 pièces
                break;
            case HEALTH:
                // Ajouter une vie si implémenté
                break;
            case ENEMY:
                player.adjustHealth(-1); // Mario perd une vie
                break;
        }
    }

    public void checkCollisionWithPlayer(Player player) {
        if (getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
            applyEffect(player);
            dispose();
        }
    }

    @Override
    public void update(float deltaTime) {
        if (type == ItemType.COIN && animationFrames != null) {
            animationTimer += deltaTime;
            int frameIndex = (int) (animationTimer / frameDuration) % animationFrames.length;
            texture = animationFrames[frameIndex];
        }
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void dispose() {
        disposed = true;
        if (animationFrames != null) {
            for (Texture frame : animationFrames) {
                frame.dispose();
            }
        }
        super.dispose();
    }

    public boolean isDisposed() {
        return disposed;
    }
}
