package game.state;

import java.util.ArrayList;

/**
 * The SokobanState class represents the state of the game.
 * @author orszaghlev
 */

public class SokobanState {

    public static final int OFFSET = 30;
    public static final int SPACE = 64;
    private static final int LEFT_COL = 1;
    private static final int RIGHT_COL = 2;
    private static final int TOP_COL = 3;
    private static final int BOTTOM_COL = 4;

    private static ArrayList<Wall> walls;
    private static ArrayList<Ball> balls;
    private static ArrayList<Storage> storages;

    private static Character character;
    private static int w = 0;
    private static int h = 0;

    private static boolean isCompleted = false;

    private String level
            = "WWWWW    \n"
            + "WP  W    \n"
            + "W BBW WWW\n"
            + "W B W WAW\n"
            + "WWW WWWAW\n"
            + " WW    AW\n"
            + " W   W  W\n"
            + " W   WWWW\n"
            + " WWWWW   \n";

    /**
     * The constructor of the class, initializes the level.
     */

    public SokobanState(){
        initWorld();
    }

    public void initWorld(){

        walls = new ArrayList<>();
        balls = new ArrayList<>();
        storages = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Ball ball;
        Storage storage;

        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);
            switch (item) {

                case '\n':
                    y += SPACE;

                    if (this.w < x) {
                        this.w = x;
                    }

                    x = OFFSET;
                    break;

                case 'W':
                    wall = new Wall(x,y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case 'B':
                    ball = new Ball(x,y);
                    balls.add(ball);
                    x += SPACE;
                    break;

                case 'A':
                    storage = new Storage(x,y);
                    storages.add(storage);
                    x += SPACE;
                    break;

                case 'P':
                    character = new Character(x,y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;

            }

            h = y;
        }

    }

    public static boolean checkWallCollision(Actor actor, int type) {
        switch (type) {

            case LEFT_COL:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isLeftCollision(wall)) {
                        return true;
                    }
                }

                return false;

            case RIGHT_COL:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isRightCollision(wall)) {
                        return true;
                    }
                }

                return false;

            case TOP_COL:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isTopCollision(wall)) {
                        return true;
                    }
                }

                return false;

            case BOTTOM_COL:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isBottomCollision(wall)) {
                        return true;
                    }
                }

                return false;

            default:
                break;

        }

        return false;
    }

    public static boolean checkBallCollision(int type) {

        switch (type) {
            case LEFT_COL:
                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);
                    if (character.isLeftCollision(ball)) {
                        for (int j = 0; j < balls.size(); j++) {
                            Ball item = balls.get(j);
                            if (!ball.equals(item)) {
                                if (ball.isLeftCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(ball, LEFT_COL)) {
                                return true;
                            }
                        }

                        ball.move(-SPACE, 0);
                        isCompleted();
                    }
                }
                return false;

            case RIGHT_COL:
                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);
                    if (character.isRightCollision(ball)) {
                        for (int j = 0; j < balls.size(); j++) {
                            Ball item = balls.get(j);
                            if (!ball.equals(item)) {
                                if (ball.isRightCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(ball, RIGHT_COL)) {
                                return true;
                            }
                        }

                        ball.move(SPACE, 0);
                        isCompleted();
                    }
                }
                return false;

            case TOP_COL:
                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);
                    if (character.isTopCollision(ball)) {
                        for (int j = 0; j < balls.size(); j++) {
                            Ball item = balls.get(j);
                            if (!ball.equals(item)) {
                                if (ball.isTopCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(ball, TOP_COL)) {
                                return true;
                            }
                        }

                        ball.move(0, -SPACE);
                        isCompleted();
                    }
                }
                return false;

            case BOTTOM_COL:
                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);
                    if (character.isBottomCollision(ball)) {
                        for (int j = 0; j < balls.size(); j++) {
                            Ball item = balls.get(j);
                            if (!ball.equals(item)) {
                                if (ball.isBottomCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(ball, BOTTOM_COL)) {
                                return true;
                            }
                        }

                        ball.move(0, SPACE);
                        isCompleted();
                    }
                }
                break;

            default:
                break;
        }
        return false;
    }

    /**
     * The level is completed when the size of the balls ArrayList is equal
     * to the number of balls in the storages.
     */

    public static boolean isCompleted() {

        int nOfBalls = balls.size();
        int finishedBalls = 0;

        for (int i = 0; i < nOfBalls; i++) {
            Ball ball = balls.get(i);

            for (int j = 0; j < nOfBalls; j++) {
                Storage storage = storages.get(j);

                if (ball.x() == storage.x() && ball.y() == storage.y()) {
                    finishedBalls += 1;
                }
            }
        }

        if (finishedBalls == nOfBalls) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        SokobanState state = new SokobanState();
        System.out.println(state);
    }
}