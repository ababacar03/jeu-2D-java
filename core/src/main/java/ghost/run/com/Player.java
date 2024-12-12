package ghost.run.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
    private final BitmapFont font = new BitmapFont(); // Pour afficher le texte

    // Attributs constants
    private final float speed = 200f;
    private final float jumpStrength = 430f;
    private final float gravity = -800f;

    // États du joueur
    private boolean onGround = true;
    private boolean movingRight = true;
    private boolean isDead = false;

    // Statistiques du joueur
    private int coins = 0;
    private int health = 3;
    private int score = 0;

    // Textures préchargées
    private final Texture idleRightTexture = new Texture("marioArretDroite.png");
    private final Texture idleLeftTexture = new Texture("marioArretGauche.png");
    private final Texture walkRightTexture = new Texture("marioMarcheDroite.png");
    private final Texture walkLeftTexture = new Texture("marioMarcheGauche.png");
    private final Texture jumpRightTexture = new Texture("marioSautDroite.png");
    private final Texture jumpLeftTexture = new Texture("marioSautGauche.png");
    private final Texture deathTexture = new Texture("marioMeurt.png");


    public Player(float startX, float startY) {
        super(startX, startY, "marioArretDroite.png");
    }

    @Override
    public void update(float deltaTime) {
        if (isDead) return; // Ne pas mettre à jour si Mario est mort

        // Initialiser la vitesse horizontale à 0 (immobile par défaut)
        velocityX = 0;

        // Autoriser les mouvements horizontaux, même en l'air
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = speed; // Déplacer à droite
            movingRight = true; // Mario regarde à droite

            // Choisir la texture en fonction de l'état
            if (onGround) {
                texture = walkRightTexture; // Texture de marche au sol
            } else {
                texture = jumpRightTexture; // Texture de saut
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -speed; // Déplacer à gauche
            movingRight = false; // Mario regarde à gauche

            // Choisir la texture en fonction de l'état
            if (onGround) {
                texture = walkLeftTexture; // Texture de marche au sol
            } else {
                texture = jumpLeftTexture; // Texture de saut
            }
        } else {
            // Si Mario ne bouge pas horizontalement
            if (onGround) {
                texture = movingRight ? idleRightTexture : idleLeftTexture; // Texture d'arrêt
            }
        }

        // Gestion du saut
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround) {
            velocityY = jumpStrength; // Appliquer la force du saut
            onGround = false; // Mario n'est plus au sol
            texture = movingRight ? jumpRightTexture : jumpLeftTexture; // Texture de saut
        }

        // Appliquer la gravité
        velocityY += gravity * deltaTime;

        // Mettre à jour la position
        x += velocityX * deltaTime; // Met à jour la position horizontale
        y += velocityY * deltaTime; // Met à jour la position verticale

        // Vérifier si Mario touche un bloc ou le sol (hypothèse : sol à y = 95)
        if (y <= 95) {
            y = 95; // Corriger la position sur le sol
            velocityY = 0; // Arrêter le mouvement vertical
            onGround = true; // Confirmer que Mario est au sol
        } else {
            onGround = false; // Mario est en l'air
        }
    }



    public void checkCollisionWithItem(Item item) {
        if (this.collidesWith(item)) {
            item.applyEffect(this);
        }
    }

    public void checkCollisionWithEnemy(Enemy enemy) {
        if (this.collidesWith(enemy)) {
            adjustHealth(-1);
        }
    }
    // Gérer les collisions avec un bloc
    public void checkCollisionWithBloc(Bloc bloc) {
        Rectangle playerBounds = getBoundingRectangle();
        Rectangle blocBounds = bloc.getBoundingRectangle();

        if (playerBounds.overlaps(blocBounds)) {
            // Collision par-dessus
            if (y > bloc.getY() + bloc.getHeight() - 5 && velocityY < 0) {
                y = bloc.getY() + bloc.getHeight(); // Positionner Mario sur le bloc
                velocityY = 0;
                onGround = true; // Mario est sur le bloc
            }
            // Collision par-dessous
            else if (y + getHeight() < bloc.getY() + 5 && velocityY > 0) {
                y = bloc.getY() - getHeight(); // Bloquer Mario sous le bloc
                velocityY = 0;
            }
            // Collision latérale
            else if (x < bloc.getX() && x + getWidth() > bloc.getX()) {
                x = bloc.getX() - getWidth(); // Bloquer à gauche
            } else if (x > bloc.getX() && x < bloc.getX() + bloc.getWidth()) {
                x = bloc.getX() + bloc.getWidth(); // Bloquer à droite
            }
        }
    }

    public void checkCollisionWithPipe(Rectangle pipeBounds) {
        Rectangle playerBounds = getBoundingRectangle();

        if (playerBounds.overlaps(pipeBounds)) {
            // Si Mario est au-dessus du tuyau
            if (y > pipeBounds.y + pipeBounds.height - 5 && velocityY < 0) {
                y = pipeBounds.y + pipeBounds.height; // Positionner Mario sur le tuyau
                velocityY = 0;
                onGround = true; // Mario est sur le tuyau
            }
            // Bloquer le déplacement horizontal
            else if (this.x < pipeBounds.x) {
                this.x = pipeBounds.x - this.getWidth(); // Empêche Mario d'aller à droite
                velocityX = 0; // Arrêter le mouvement horizontal
            } else if (this.x > pipeBounds.x) {
                this.x = pipeBounds.x + pipeBounds.width; // Empêche Mario d'aller à gauche
                velocityX = 0; // Arrêter le mouvement horizontal
            }
        }
    }



    public void addCoin(int amount) {
        coins += amount;
        System.out.println("Nombre de pièces : " + coins);
    }

    public void adjustHealth(int amount) {
        health += amount;

        // Limiter les vies entre 0 et 3
        if (health > 3) {
            health = 3;
        } else if (health <= 0) {
            health = 0;
            die(); // Gérer la mort de Mario
        } else {
            System.out.println("Vies restantes : " + health);
        }
    }

    private void die() {
        isDead = true;
        texture = deathTexture;
        velocityX = 0;
        velocityY = 0;
        System.out.println("Mario est mort. Game Over!");
    }

    public void increaseScore(int points) {
        score += points;
        System.out.println("Score : " + score);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        idleRightTexture.dispose();
        idleLeftTexture.dispose();
        walkRightTexture.dispose();
        walkLeftTexture.dispose();
        jumpRightTexture.dispose();
        jumpLeftTexture.dispose();
        deathTexture.dispose();
    }

    // Getters pour les statistiques
    public int getCoins() {
        return coins;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean isDead() {
        return isDead;
    }
}
