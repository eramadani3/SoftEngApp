package edu.wpi.cs3733.d22.teamY.controllers;

import edu.wpi.cs3733.d22.teamY.DBUtils;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class welcomePageController {

  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Pane loginPane;
  @FXML private Pane failedLoginPane;
  @FXML private Label attemptsRemaining;

  private boolean lockOut = false;

  int maxAttempts = 5;
  int attCount = 0;

  @FXML
  void mainPage() throws IOException {
    SceneLoading.loadScene("views/mainPage.fxml");
  }

  @FXML
  void killApplication() throws IOException {
    System.out.println("pressed button");
    SceneLoading.loadPopup("views/ConfirmClose.fxml", "views/welcomePage.fxml");
  }

  @FXML
  void loginToMainPage() throws IOException, InterruptedException {
    if (DBUtils.isValidLogin(username.getText(), password.getText()) && !lockOut) {
      mainPage();
    } else {
      failedLoginPane.setOpacity(0.0);
      failedLoginPane.setVisible(true);
      FadeTransition ft = new FadeTransition(Duration.millis(1000), failedLoginPane);
      ft.setFromValue(0.0);
      ft.setToValue(1.0);
      FadeTransition ft2 = new FadeTransition(Duration.millis(1000), failedLoginPane);
      ft2.setFromValue(1.0);
      if (attCount >= maxAttempts) {
        lockOut = true;
        attemptsRemaining.setText("No Remaining Attempts");
        ft2.setToValue(1.0);
      } else {
        attemptsRemaining.setText((maxAttempts - attCount) + " Attempts Remain");
        ft2.setToValue(0.0);
      }
      Timeline tl =
          new Timeline(
              new KeyFrame(Duration.seconds(0), (e) -> ft.play()),
              new KeyFrame(Duration.seconds(4), (e) -> {}),
              new KeyFrame(Duration.seconds(5), (e) -> ft2.play()));
      tl.play();
      attCount++;
    }
  }
}
