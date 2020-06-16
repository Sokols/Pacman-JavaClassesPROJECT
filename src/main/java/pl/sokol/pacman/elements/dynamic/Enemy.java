package pl.sokol.pacman.elements.dynamic;

import pl.sokol.pacman.elements.Junction;
import pl.sokol.pacman.elements.Renderable;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Rectangle implements Renderable, Moveable {

    private final int ENEMY_WIDTH = 32;
    private final int ENEMY_HEIGHT = 32;
    private final int SPEED = 1;
    private final int SEARCH_RANGE = 160;
    private String[] IMAGES = {
            "/enemies/enemy1.png",
            "/enemies/enemy2.png",
            "/enemies/enemy3.png",
            "/enemies/enemy4.png",
            "/enemies/enemy5.png"
    };

    private Random random = new Random();

    private Player player;

    private List<Junction> junctions;

    private BufferedImage bf;

    private int currentMovement;
    private int previousMovement;

    public Enemy(int x, int y, Player player, List<Junction> junctions, int numberOfTheImage) throws IOException {
        this.player = player;
        this.bf = ImageIO.read(getClass().getResourceAsStream(IMAGES[numberOfTheImage]));
        this.junctions = junctions;
        this.currentMovement = 0;
        this.previousMovement = 0;
        setBounds(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    public void render(Graphics g) {
        move();
        g.drawImage(bf, x, y, width, height, null);
    }

    @Override
    public void move() {

        // vector position of the enemy relative to the player
        // HERE WE USE JAVA COORDINATE SYSTEM
        int vectorX = player.x - x;
        int vectorY = player.y - y;
        boolean findPlayerFlag = false;
        // try to find a player in search range
        if (Math.abs(vectorX) < SEARCH_RANGE && Math.abs(vectorY) < SEARCH_RANGE) {
            findPlayerFlag = true;
        }

        boolean findJunctionFlag = false;
        // check if the enemy is on the junction
        for (Junction junction : junctions) {
            if (junction.contains(this)) {
                findJunctionFlag = true;
                break;
            }
        }

        if (findJunctionFlag && findPlayerFlag) {
            List<Integer> priorities = new ArrayList<>();
            // I QUARTER
            final boolean vectorsDifference = Math.abs(vectorX) >= Math.abs(vectorY);
            if (vectorX >= 0 && vectorY <= 0) {
                if (vectorsDifference) {
                    priorities.add(1);
                    priorities.add(0);
                    priorities.add(2);
                    priorities.add(3);
                } else {
                    priorities.add(0);
                    priorities.add(1);
                    priorities.add(3);
                    priorities.add(2);
                }
            }

            // II QUARTER
            else if (vectorX <= 0 && vectorY <= 0) {
                if (vectorsDifference) {
                    priorities.add(3);
                    priorities.add(0);
                    priorities.add(2);
                    priorities.add(1);
                } else {
                    priorities.add(0);
                    priorities.add(3);
                    priorities.add(1);
                    priorities.add(2);
                }
            }

            // III QUARTER
            else if (vectorX <= 0) {
                if (vectorsDifference) {
                    priorities.add(3);
                    priorities.add(2);
                    priorities.add(0);
                    priorities.add(1);
                } else {
                    priorities.add(2);
                    priorities.add(3);
                    priorities.add(1);
                    priorities.add(0);
                }
            }

            // IV QUARTER
            else {
                if (vectorsDifference) {
                    priorities.add(1);
                    priorities.add(2);
                    priorities.add(0);
                    priorities.add(3);
                } else {
                    priorities.add(2);
                    priorities.add(1);
                    priorities.add(3);
                    priorities.add(0);
                }
            }

            for (int movement : priorities) {
                if (canMove(movement, SPEED, this) && movement != previousMovement) {
                    previousMovement = currentMovement;
                    currentMovement = movement;
                    break;
                }
            }

        } else if (findJunctionFlag) {
            // find new movement
            previousMovement = currentMovement;
            do {
                currentMovement = random.nextInt(4);
            } while (!canMove(currentMovement, SPEED, this));

        } else {
            // if possible, move with the current movement, if not - find new one
            int temp = currentMovement;
            while (!canMove(currentMovement, SPEED, this)) {
                currentMovement = random.nextInt(4);
            }
            if (currentMovement != temp) {
                previousMovement = temp;
            }
        }
        makeMove(currentMovement, SPEED, this);
    }

    public void setJunctions(List<Junction> junctions) {
        this.junctions = junctions;
    }
}
