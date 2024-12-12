package ghost.run.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    private Player player;
    private BitmapFont font;
    private Texture backgroundImage;
    private Texture pipeImage;
    private Texture blocImage;
    private Texture chateauImage;
    private Texture departImage;
    private Texture marioArretDroiteImage;
    private Texture piece1Image;
    private Texture tortueArretDroiteImage;
    private Texture champArretDroiteImage;
    private Texture marioMeurtImage;
    private Texture chateauFinImage;
    private Texture drapeauImage;
    private ArrayList<Enemy> enemies;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera; // Caméra pour le HUD

    private GlyphLayout layout;

    // Textures pour le menu
    private Texture menuBackgroundImage;
    private Texture startButtonImage;
    private Texture exitButtonImage;

    //Liste des objets du jeu
    private ArrayList<Item> items; // Liste des objets dans le jeu
    private ArrayList<Bloc> blocs; // Liste des blocs
    private ArrayList<Rectangle> pipes; // Liste pour stocker les tuyaux


    private boolean isMenuActive = true; // Indique si le menu est actif
    private float deathTimer = -1; // Timer pour gérer l'affichage de la mort
    private boolean hasWon = false; // Indique si Mario a gagné
    private Rectangle flagBounds; // Zone représentant le drapeau de fin
    private Texture menuButtonImage;






    @Override
    public void create() {
        batch = new SpriteBatch();

        // Chargement des images du jeu
        backgroundImage = new Texture("fondEcran.png");
        pipeImage = new Texture("tuyauRouge.png");
        blocImage = new Texture("bloc.png");
        chateauImage = new Texture("chateau1.png");
        departImage = new Texture("depart.png");
        marioArretDroiteImage = new Texture("marioArretDroite.png");
        piece1Image = new Texture("piece1.png");
        tortueArretDroiteImage = new Texture("tortueArretDroite.png");
        champArretDroiteImage = new Texture("champArretDroite.png");
        marioMeurtImage = new Texture("marioMeurt.png");
        chateauFinImage = new Texture("chateauFin.png");
        drapeauImage = new Texture("drapeau.png");
        menuButtonImage = new Texture("arriere.png"); // Remplacez par le chemin de l'image du bouton
        flagBounds = new Rectangle(3650, 95, drapeauImage.getWidth(), drapeauImage.getHeight());

        // Chargement des images du menu
        menuBackgroundImage = new Texture("Menu.jpg");
        startButtonImage = new Texture("bouton-start.png");
        exitButtonImage = new Texture("boutonExit.png");

        // Initialisation de la caméra
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        // Caméra du HUD
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.setToOrtho(false); // Origine en bas à gauche
        hudCamera.update();

        // Création du joueur
        player = new Player(300, 95);
        // Vérification de la collision avec le drapeau de la fin

        // Création des ennemis
        enemies = new ArrayList<>();
        enemies.add(new Enemy(1100, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(1200, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(1650, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(1750, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(1550, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(2150, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(2050, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(2250, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(2650, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(3100, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(3200, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(3300, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue

        items = new ArrayList<>();
        // Exemple d'ajout d'objets dans le jeu
        items.add(new Item(500, 220, "piece1.png", ItemType.COIN)); // Une pièce
        items.add(new Item(700, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1100, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1300, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1600, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1800, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(2700, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(2900, 220, "piece1.png", ItemType.COIN)); // Une autre pièce

        // Initialisation des blocs
        blocs = new ArrayList<>();
        blocs.add(new Bloc(500, 180, "bloc.png")); // Exemple de bloc
        blocs.add(new Bloc(700, 180, "bloc.png")); // Un autre bloc
        blocs.add(new Bloc(1100, 180, "bloc.png"));
        blocs.add(new Bloc(1300, 180, "bloc.png"));
        blocs.add(new Bloc(1600, 180, "bloc.png"));
        blocs.add(new Bloc(1800, 180, "bloc.png"));
        blocs.add(new Bloc(2700, 180, "bloc.png"));
        blocs.add(new Bloc(2900, 180, "bloc.png"));

        //Initialisation des tuyau
        pipes = new ArrayList<>();
        pipes.add(new Rectangle(900, 95, pipeImage.getWidth(), pipeImage.getHeight())); // Tuyau 1
        pipes.add(new Rectangle(1450, 95, pipeImage.getWidth(), pipeImage.getHeight())); // Tuyau 2
        pipes.add(new Rectangle(1950, 95, pipeImage.getWidth(), pipeImage.getHeight())); // Tuyau 3
        pipes.add(new Rectangle(2500, 95, pipeImage.getWidth(), pipeImage.getHeight())); // Tuyau 4
        pipes.add(new Rectangle(3000, 95, pipeImage.getWidth(), pipeImage.getHeight())); // Tuyau 5
        pipes.add(new Rectangle(3450, 95, pipeImage.getWidth(), pipeImage.getHeight())); // Tuyau 6



        font = new BitmapFont();
        layout = new GlyphLayout();
    }
    @Override
    public void resize(int width, int height) {
        // Mettre à jour la caméra pour qu'elle s'ajuste à la nouvelle taille
        camera.setToOrtho(false, width, height);
        camera.update();

        // Mettre à jour la HUD caméra si nécessaire
        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();
    }

    private void resetItemsAndEnemies() {
        // Réinitialiser les ennemis
        enemies.clear();
        enemies.add(new Enemy(1100, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(1200, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(1650, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(1750, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(1550, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(2150, 95, "champArretDroite.png", "Champignon", 1)); // Champignon
        enemies.add(new Enemy(2050, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(2250, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(2650, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(3100, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(3200, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue
        enemies.add(new Enemy(3300, 95, "tortueArretDroite.png", "Tortue", 1)); // Tortue

        // Réinitialiser les objets (pièces)
        items.clear();
        items.add(new Item(500, 220, "piece1.png", ItemType.COIN)); // Une pièce
        items.add(new Item(700, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1100, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1300, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1600, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(1800, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(2700, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
        items.add(new Item(2900, 220, "piece1.png", ItemType.COIN)); // Une autre pièce
    }

    private void resetToMenu() {
        isMenuActive = true; // Active l'affichage du menu
        deathTimer = -1;     // Réinitialise le timer de mort
        player = new Player(300, 95); // Réinitialise Mario
        player.setDead(false);

        resetItemsAndEnemies(); // Réinitialise les ennemis et les pièces
        hasWon = false; // Réinitialise l'état de victoire
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (isMenuActive) {
            // Afficher le menu
            renderMenu();
        } else {
            // Afficher le jeu
            renderGame();
        }

    }
    private void renderGameOver() {
        // Assurer que le batch utilise la caméra HUD pour un affichage fixe
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        // Messages de Game Over
        String gameOverMessage = "Game Over!";
        String scoreMessage = "Score: " + player.getScore();
        String coinsMessage = "Coins: " + player.getCoins();

        // Calcul de la position du texte (centré)
        float centerX = hudCamera.viewportWidth / 2;
        float centerY = hudCamera.viewportHeight / 2;

        GlyphLayout layout = new GlyphLayout();

        // Dessiner le message principal
        layout.setText(font, gameOverMessage);
        font.draw(batch, gameOverMessage, centerX - layout.width / 2, centerY + 50);

        // Dessiner le score
        layout.setText(font, scoreMessage);
        font.draw(batch, scoreMessage, centerX - layout.width / 2, centerY);

        // Dessiner le nombre de pièces
        layout.setText(font, coinsMessage);
        font.draw(batch, coinsMessage, centerX - layout.width / 2, centerY - 50);

        // Dessiner le bouton retour au menu
        float buttonWidth = 200; // Largeur du bouton
        float buttonHeight = 80; // Hauteur du bouton
        float buttonX = centerX - buttonWidth / 2; // Centrer horizontalement
        float buttonY = centerY - 150; // Position sous le texte

        batch.draw(menuButtonImage, buttonX, buttonY, buttonWidth, buttonHeight);

        batch.end();

        // Gérer le clic sur le bouton retour au menu
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverser l'axe Y

            // Vérifier si le bouton est cliqué
            if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                resetToMenu(); // Retourner au menu
            }
        }
    }

    private void renderMenu() {
        batch.begin();

        // Dessiner l'image de fond du menu
        batch.draw(menuBackgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Dessiner les boutons Start et Exit
        float buttonWidth = 200;
        float buttonHeight = 80;

        float startButtonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float startButtonY = ((float) Gdx.graphics.getHeight() / 2) + 50;
        float exitButtonY = startButtonY - 100;

        batch.draw(startButtonImage, startButtonX, startButtonY, buttonWidth, buttonHeight);
        batch.draw(exitButtonImage, startButtonX, exitButtonY, buttonWidth, buttonHeight);

        batch.end();

        // Gérer les clics sur les boutons
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverser l'axe Y

            // Vérifier si le bouton Start est cliqué
            if (touchX >= startButtonX && touchX <= startButtonX + buttonWidth &&
                touchY >= startButtonY && touchY <= startButtonY + buttonHeight) {
                isMenuActive = false; // Passer au jeu
                resetGame();          // Réinitialise Mario
                resetItemsAndEnemies(); // Réinitialise les pièces et ennemis
            }

            // Vérifier si le bouton Exit est cliqué
            if (touchX >= startButtonX && touchX <= startButtonX + buttonWidth &&
                touchY >= exitButtonY && touchY <= exitButtonY + buttonHeight) {
                Gdx.app.exit(); // Quitter le jeu
            }
        }
    }


    private void resetGame() {
        player.x = 300; // Réinitialiser la position X de Mario
        player.y = 95;  // Réinitialiser la position Y de Mario
        player.velocityX = 0; // Réinitialiser la vitesse X
        player.velocityY = 0; // Réinitialiser la vitesse Y
        player.setOnGround(true); // Considérer que Mario est au sol
        hasWon = false; // Réinitialise l'état de victoire
    }

    private void renderHUD() {
        // Utiliser la caméra du HUD pour une projection fixe
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        // Dimensions de l'écran

        // Dimensions dynamiques de l'écran
        float screenWidth = hudCamera.viewportWidth;
        float screenHeight = hudCamera.viewportHeight;

        // Afficher le score (en haut à droite)
        String scoreText = "Score: " + player.getScore();
        font.draw(batch, scoreText, screenWidth - 150, screenHeight - 20);

        // Afficher les coins (en dessous du score)
        String coinsText = "Coins: " + player.getCoins();
        font.draw(batch, coinsText, screenWidth - 150, screenHeight - 50);

        // Afficher les vies (en dessous des coins)
        String livesText = "Vies: " + player.getHealth();
        font.draw(batch, livesText, screenWidth - 150, screenHeight - 80);

        batch.end();
    }

    private void renderGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (hasWon) {
            // Assurer que le batch utilise la caméra HUD pour un affichage fixe
            batch.setProjectionMatrix(hudCamera.combined);
            batch.begin();

            // Messages de victoire
            String victoryMessage = "Vous avez gagné !";
            String scoreMessage = "Score: " + player.getScore();
            String coinsMessage = "Coins: " + player.getCoins();

            // Calcul de la position du texte (centré)
            float centerX = hudCamera.viewportWidth / 2;
            float centerY = hudCamera.viewportHeight / 2;

            GlyphLayout layout = new GlyphLayout();

            // Dessiner le message principal
            layout.setText(font, victoryMessage);
            font.draw(batch, victoryMessage, centerX - layout.width / 2, centerY + 50);

            // Dessiner le score
            layout.setText(font, scoreMessage);
            font.draw(batch, scoreMessage, centerX - layout.width / 2, centerY);

            // Dessiner le nombre de pièces
            layout.setText(font, coinsMessage);
            font.draw(batch, coinsMessage, centerX - layout.width / 2, centerY - 50);

            // Dessiner le bouton retour au menu
            float buttonWidth = 200; // Largeur du bouton
            float buttonHeight = 80; // Hauteur du bouton
            float buttonX = centerX - buttonWidth / 2; // Centrer horizontalement
            float buttonY = centerY - 150; // Position sous le texte

            batch.draw(menuButtonImage, buttonX, buttonY, buttonWidth, buttonHeight);

            batch.end();

            // Gérer le clic sur le bouton retour au menu
            if (Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverser l'axe Y

                // Vérifier si le bouton est cliqué
                if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                    touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                    resetToMenu(); // Retourner au menu
                }
            }

            return; // Ne pas continuer à mettre à jour le jeu
        }


        // Vérifier si Mario atteint le drapeau
        if (player.getBoundingRectangle().overlaps(flagBounds)) {
            hasWon = true; // Définir l'état de victoire
            return; // Arrêter la logique du jeu
        }
        // Appeler la mise à jour de Mario
        player.update(deltaTime);
        // Si Mario est mort, afficher l'image de Mario mort et arrêter le jeu
        if (player.isDead()) {
            renderGameOver(); // Appeler la méthode pour afficher l'écran "Game Over"
            return; // Arrêter le rendu du reste du jeu
        }

        // Vérification de la collision avec le tuyau rouge
        for (Rectangle pipe : pipes) {
            player.checkCollisionWithPipe(pipe); // Vérifie la collision avec chaque tuyau
        }


        // Vérification des collisions avec les ennemis
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);

            // Vérifier les collisions
            if (player.getBoundingRectangle().overlaps(enemy.getBoundingRectangle())) {
                // Vérification si Mario saute sur l'ennemi
                if (player.getY() > enemy.getY() + enemy.getHeight() * 0.5f && player.getVelocityY() < 0) {
                    // Mario écrase l'ennemi
                    enemiesToRemove.add(enemy); // Marquer l'ennemi pour suppression
                    player.increaseScore(100);  // Ajouter 100 points au score
                    player.setVelocityY(300);   // Faire rebondir Mario légèrement
                    System.out.println("Mario a écrasé un ennemi ! Score : " + player.getScore());
                } else {
                    // Collision normale, Mario perd une vie
                    player.adjustHealth(-1);
                    if (player.getHealth() > 0) {
                        System.out.println("Mario a perdu une vie. Vies restantes : " + player.getHealth());
                        resetGame(); // Réinitialiser le jeu
                    } else {
                        System.out.println("Mario est mort !");
                        player.setDead(true);
                    }
                }
            }
        }
        enemies.removeAll(enemiesToRemove); // Supprimer les ennemis écrasés
        for (Enemy enemy : enemies) {
            for (Rectangle pipe : pipes) {
                enemy.checkCollisionWithPipe(pipe); // Vérifie la collision de l'ennemi avec chaque tuyau
            }
        }



        // Mise à jour des objets et vérification des collisions
        ArrayList<Item> itemsToRemove = new ArrayList<>();
        for (Item item : items) {
            item.update(deltaTime);

            // Vérification collision entre Player et Item
            if (player.getBoundingRectangle().overlaps(item.getBoundingRectangle())) {
                item.applyEffect(player); // Applique l'effet de l'objet (ex: ajoute des pièces)
                itemsToRemove.add(item);  // Marque l'objet pour suppression
            }
        }
        items.removeAll(itemsToRemove); // Supprime les objets collectés

        // Vérification des collisions avec les blocs
        for (Bloc bloc : blocs) {
            player.checkCollisionWithBloc(bloc);
        }

        // Mise à jour de la caméra
        camera.position.x = Math.max(player.getX() + Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() / 2f);
        camera.position.y = Gdx.graphics.getHeight() / 2f;
        camera.update();


        // Rendu des entités
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dessiner l'arrière-plan pour couvrir toute la vue de la caméra
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        float backgroundStartX = camera.position.x - screenWidth / 2;
        float backgroundWidth = backgroundImage.getWidth();
        for (float x = backgroundStartX; x < backgroundStartX + screenWidth; x += backgroundWidth) {
            batch.draw(backgroundImage, x, 0, backgroundWidth, screenHeight);
        }

        // Empêcher la caméra d'aller trop à gauche
        camera.position.x = Math.max(camera.viewportWidth / 2, camera.position.x);

        // Empêcher Mario de sortir à gauche
        if (player.getX() < 0) {
            player.setX(0);
        }
        player.render(batch);  // Dessiner Mario, via la méthode héritée de Entity
        batch.draw(pipeImage, 900, 95);
        batch.draw(chateauImage, 1, 95);
        batch.draw(departImage, 200, 95);
        batch.draw(chateauFinImage,3785,95);
        batch.draw(drapeauImage, flagBounds.x, flagBounds.y, flagBounds.width, flagBounds.height);

        // Dessiner le joueur
        player.render(batch);

        // Dessiner tous les tuyaux
        for (Rectangle pipe : pipes) {
            batch.draw(pipeImage, pipe.x, pipe.y, pipe.width, pipe.height);
        }
        // Dessiner les ennemis
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        // Dessiner les objets
        for (Item item : items) {
            item.render(batch);
        }

         // Dessiner les blocs
        for (Bloc bloc : blocs) {
            bloc.render(batch);
        }

        batch.end();

        // Rendu du HUD (fixe)
        renderHUD();
        /*// Dessiner les statistiques sur un écran fixe
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt()); // Réinitialiser la matrice pour des éléments fixes
        batch.begin();

        // Afficher les statistiques du joueur
        String scoreText = "Score: " + player.getScore();
        layout.setText(font, scoreText);
        font.draw(batch, scoreText, 10, screenHeight - 10);

        String livesText = "Vies: " + player.getHealth();
        layout.setText(font, livesText);
        float livesWidth = layout.width;
        font.draw(batch, livesText, (screenWidth - livesWidth) / 2, screenHeight - 10);

        String coinsText = "Coins: " + player.getCoins();
        layout.setText(font, coinsText);
        font.draw(batch, coinsText, screenWidth - layout.width - 10, screenHeight - 10);

        batch.end();*/
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundImage.dispose();
        pipeImage.dispose();
        blocImage.dispose();
        chateauImage.dispose();
        departImage.dispose();
        marioArretDroiteImage.dispose();
        piece1Image.dispose();
        tortueArretDroiteImage.dispose();
        champArretDroiteImage.dispose();
        chateauFinImage.dispose();
        marioMeurtImage.dispose();
        menuBackgroundImage.dispose();
        startButtonImage.dispose();
        exitButtonImage.dispose();
        menuButtonImage.dispose();

        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }
}
