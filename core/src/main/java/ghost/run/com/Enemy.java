package ghost.run.com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {
    private final String type;
    private int health;
    private float directionChangeTimer;
    private int currentDirectionIndex;
    private final List<Float> movementPattern;

    private final float startX; // Position de départ
    private final float range; // Plage de mouvement

    private final Texture textureWalkRight;
    private final Texture textureWalkLeft;

    public Enemy(float startX, float startY, String texturePath, String type, int health) {
        super(startX, startY, texturePath);
        this.type = type;
        this.health = health;
        this.startX = startX;
        this.range = 200; // Par défaut

        // Précharger les textures
        if (type.equals("Tortue")) {
            textureWalkRight = new Texture("tortueMarcheDroite.png");
            textureWalkLeft = new Texture("tortueMarcheGauche.png");
        } else if (type.equals("Champignon")) {
            textureWalkRight = new Texture("champMarcheDroite.png");
            textureWalkLeft = new Texture("champMarcheGauche.png");
        } else {
            textureWalkRight = new Texture(texturePath);
            textureWalkLeft = new Texture(texturePath);
        }

        this.movementPattern = new ArrayList<>();
        movementPattern.add(3f); // Droite 3s
        movementPattern.add(2f); // Gauche 2s
        movementPattern.add(4f); // Droite 4s

        this.directionChangeTimer = movementPattern.get(0);
        this.velocityX = 50; // Vitesse initiale
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            System.out.println(type + " éliminé !");
            dispose();
        }
    }

    public void movePattern(float deltaTime) {
        directionChangeTimer -= deltaTime;

        if (directionChangeTimer <= 0) {
            currentDirectionIndex = (currentDirectionIndex + 1) % movementPattern.size();
            directionChangeTimer = movementPattern.get(currentDirectionIndex);

            velocityX = -velocityX; // Inverser la direction
        }

        x += velocityX * deltaTime;

        if (x < startX - range) {
            x = startX - range;
            velocityX = Math.abs(velocityX); // Droite
        } else if (x > startX + range) {
            x = startX + range;
            velocityX = -Math.abs(velocityX); // Gauche
        }

        texture = velocityX > 0 ? textureWalkRight : textureWalkLeft;
    }

    // Nouvelle méthode pour gérer les collisions avec les tuyaux
    public void checkCollisionWithPipe(Rectangle pipeBounds) {
        Rectangle enemyBounds = getBoundingRectangle();

        if (enemyBounds.overlaps(pipeBounds)) {
            // Si l'ennemi touche un tuyau, il change de direction
            if (x < pipeBounds.x) {
                x = pipeBounds.x - getWidth(); // Bloque à gauche
                velocityX = Math.abs(velocityX); // Change la direction vers la droite
            } else if (x > pipeBounds.x) {
                x = pipeBounds.x + pipeBounds.width; // Bloque à droite
                velocityX = -Math.abs(velocityX); // Change la direction vers la gauche
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        movePattern(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        textureWalkRight.dispose();
        textureWalkLeft.dispose();
    }
}
