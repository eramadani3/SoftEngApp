package edu.wpi.cs3733.d22.teamY;

import edu.wpi.cs3733.d22.teamY.model.Employee;
import edu.wpi.cs3733.d22.teamY.model.Location;
import edu.wpi.cs3733.d22.teamY.model.MedEquip;
import java.util.List;
import javafx.util.Pair;
import org.hibernate.Session;

/** This class is used to create specific queries for the database. */
public class DBUtils {

  private DBUtils() {}

  public static List<Location> getLocationsOnFloor(String floor) {
    return DBManager.getAll(Location.class, new Where(Location.FLOOR, floor));
  }

  public static List<MedEquip> getEquipmentAtLocation(Location location) {
    return DBManager.getAll(MedEquip.class, new Where(MedEquip.EQUIP_LOC_ID, location.getNodeID()));
  }

  public static Pair<Integer, Integer> getAvailableEquipment(String equipType) {
    List<MedEquip> equipment =
        DBManager.getAll(MedEquip.class, new Where(MedEquip.EQUIP_TYPE, equipType));

    if (equipment == null) {
      return null;
    }

    int total = equipment.size();
    int available = 0;

    for (MedEquip i : equipment) {
      if (i.isClean().equals("1")) {
        available++;
      }
    }
    Pair<Integer, Integer> returnPair = new Pair<>(available, total);
    return returnPair;
  }

  // find MedEquip object
  public static void updateCleanStatus(String equipType, String locationID) {
    Session s = SessionManager.getSession();
    List<MedEquip> equipment =
        s.createQuery("from MedEquip where equipType = :equipType and isClean = '1'")
            .setParameter("equipType", equipType)
            .list();
    s.close();

    if (equipment.size() == 0) {
      return;
    }

    MedEquip thisEquip = equipment.get(0);

    thisEquip.setEquipLocId(locationID);
    thisEquip.setClean("0");

    DBManager.update(thisEquip);
  }
  /**
   * Returns if a valid login was made.
   *
   * @param username the username of the employee
   * @param password the password of the employee
   * @return true if the employee had valid credentials, false otherwise
   */
  @SuppressWarnings("unchecked")
  public static boolean isValidLogin(String username, String password) {
    Session s = SessionManager.getSession();
    // search for the employee with the given username and password
    List<Employee> employees =
        s.createQuery("from Employee where username = :username and password = :password")
            .setParameter("username", username)
            .setParameter("password", password)
            .list();
    s.close();
    return employees.size() == 1;
    
  /**
   * Changes an employee's password.
   *
   * @param username the username of the employee
   * @param oldPassword the old password of the employee
   * @param newPassword the new password of the employee
   * @return String relating to the success of the change
   */
  public static String changePassword(String username, String oldPassword, String newPassword) {
    Session s = SessionManager.getSession();
    List<Employee> employees =
        s.createQuery("from Employee where username = :username")
            .setParameter("username", username)
            .list();
    s.close();
    // if the employee exists
    if (employees.size() != 1) {
      return "Error: Invalid username.";
    }

    Employee employee = employees.get(0);

    // check validity of old password and new password
    if (!employee.getPassword().equals(oldPassword)) {
      return "Error: Invalid old password.";
    }
    if (oldPassword.equals(newPassword)) {
      return "Error: New password cannot be the same as old password.";
    }
    if (!Employee.isValidNewPassword(newPassword)) {
      return "Error: New password must be at least 5 characters long and contain at least one number, one letter, and one special character.";
    }

    employee.setPassword(newPassword);
    DBManager.update(employee);
    return "Successfully changed password.";
  }
}
