package edu.wpi.cs3733.d22.teamY.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/** Interface for request objects. Add methods that are common across all request objects. */
@MappedSuperclass
public abstract class Requestable {

  @Id private String requestNum;
  private String roomID;
  private String assignedNurse;
  private String additionalNotes;
  private RequestStatus requestStatus;
  private int requestPriority;

  protected void initParent(
          String requestNum,
          String roomID,
          String assignedNurse,
          String additionalNotes,
          RequestStatus status,
          int priority){
    this.requestNum = requestNum;
    this.roomID = roomID;
    this.assignedNurse = assignedNurse;
    this.additionalNotes = additionalNotes;
    this.requestStatus = status;
    this.requestPriority = priority;
  }

  // region Getters/Setters
  public String getRequestNum() {
    return requestNum;
  }

  public void setRequestNum(String requestNum) {
    this.requestNum = requestNum;
  }

  public String getRoomID() {
    return roomID;
  }

  public void setRoomID(String roomID) {
    this.roomID = roomID;
  }

  public String getAssignedNurse() {
    return assignedNurse;
  }

  public void setAssignedNurse(String assignedNurse) {
    this.assignedNurse = assignedNurse;
  }

  public RequestStatus getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(RequestStatus requestStatus) {
    this.requestStatus = requestStatus;
  }

  public String getAdditionalNotes() {
    return additionalNotes;
  }

  public void setAdditionalNotes(String additionalNotes) {
    this.additionalNotes = additionalNotes;
  }
  // endregion

  public abstract String getLocID();
}
