package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import game.results.GameResult;
import game.results.GameResultDao;
import game.state.SokobanState;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GameController {

    private SokobanState gameState;
    private String userName;
    private int stepCount;
    private int pushCount;
    private List<Image> levelImages;
    private Instant beginGame;

    private GameResultDao gameResultDao;

    @FXML
    private Label usernameLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepLabel;

    @FXML
    private Label pushLabel;

    @FXML
    private Label solvedLabel;

    @FXML
    private Button doneButton;

    private void drawGameState() {
        stepLabel.setText(String.valueOf(stepCount));
        pushLabel.setText(String.valueOf(pushCount));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 9 + j);
                view.setImage(levelImages.get(gameState.getTray()[i][j].getValue()));
            }
        }
    }

    public void initData(String userName) {
        this.userName = userName;
        usernameLabel.setText("Current user: " + this.userName);
    }

    @FXML
    public void initialize() {
        gameResultDao = GameResultDao.getInstance();
        gameState = new SokobanState();
        stepCount = 0;
        pushCount = 0;
        beginGame = Instant.now();

        levelImages = Arrays.asList(
                new Image(getClass().getResource("/pictures/empty.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/wall.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/character.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/ball.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/storage.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/storage1.png").toExternalForm())
        );

        drawGameState();
    }

    public void levelClick(MouseEvent mouseEvent) {
        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());

        if (!gameState.isSolved() && !gameState.checkWallCollision(clickedRow, clickedColumn) && gameState.canMoveToEmptySpace(clickedRow, clickedColumn)) {
            stepCount++;
            if (!gameState.checkBallCollision(clickedRow, clickedColumn)) {
                gameState.moveToEmptySpace(clickedRow, clickedColumn);
            }
            else {
                pushCount++;
                if (!gameState.isBallPlaced(clickedRow, clickedColumn)) {
                    gameState.pushBall(clickedRow, clickedColumn);
                }
                else {
                    gameState.fillStorage(clickedRow, clickedColumn);
                }
            }
            gameState.placeEmptyStorage();
            if (gameState.isSolved()) {
                log.info("Player {} completed the level in {} steps.", userName, stepCount);
                solvedLabel.setText("You completed the game!");
                doneButton.setText("FINISH");
                gameResultDao.persist(getResult());
            }
        }
        drawGameState();
    }

    public void resetGame(ActionEvent actionEvent) {
        gameState = new SokobanState();
        stepCount = 0;
        pushCount = 0;
        solvedLabel.setText("");
        drawGameState();
        beginGame = Instant.now();
        log.info("Game reset.");
    }

    private GameResult getResult() {
        GameResult result = GameResult.builder()
                                    .player(userName)
                                    .solved(gameState.isSolved())
                                    .duration(Duration.between(beginGame, Instant.now()))
                                    .pushes(pushCount)
                                    .steps(stepCount)
                                    .build();
        return result;
    }

    public void finishGame(ActionEvent actionEvent) throws IOException {
        if (!gameState.isSolved()) {
            gameResultDao.persist(getResult());
        }

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/topten.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Finished game, loading Top Ten scene.");
    }
}
