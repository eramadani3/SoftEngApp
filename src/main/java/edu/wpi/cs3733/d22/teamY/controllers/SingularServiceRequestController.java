package edu.wpi.cs3733.d22.teamY.controllers;

import edu.wpi.cs3733.d22.teamY.DBUtils;
import edu.wpi.cs3733.d22.teamY.model.Requestable;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class SingularServiceRequestController {
  @FXML
  void initialize() {
    ActiveServiceRequestController.requestControllers.add(this);
  }

  @FXML private Group colorGizmo;

  @FXML private Label assignedNurse;
  @FXML private Label reqType;
  @FXML private Label mapLocation;

  private String additional;
  private int priority;

  public void populateFromRequestable(Requestable req) {
    additional = req.getInfoBoxText();
    priority = req.getRequestPriority();
    setColor(priorityColor(priority));

    assignedNurse.setText(DBUtils.getNameFromID(req.getAssignedNurse()));
    reqType.setText(req.getTypeString());
    mapLocation.setText(DBUtils.convertIDToName(req.getLocID()));
  }

  public int getPriority() {
    return priority;
  }

  @FXML
  void displayDetailedInfo() {
    SceneUtil.serviceRequests.fillInfoField(additional);
  }

  private void setColor(Color color) {
    for (Node child : colorGizmo.getChildren()) {
      ((Shape) child).setFill(color);
    }
  }

  public static Color priorityColor(int priority) {
    int p = Math.min(Math.max(priority, 0), 10);
    return Color.hsb((10 - p) * 12.8, 0.36, 0.98);
  }
}