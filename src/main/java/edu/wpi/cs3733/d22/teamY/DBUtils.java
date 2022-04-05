package edu.wpi.cs3733.d22.teamY;

import edu.wpi.cs3733.d22.teamY.model.Location;
import edu.wpi.cs3733.d22.teamY.model.MedEquip;
import javafx.util.Pair;

import java.util.List;

/** This class is used to create specific queries for the database. */
public class DBUtils {

  private DBUtils() {}

  public static List<Location> getLocationsOnFloor(String floor) {
    return DBManager.getAll(Location.class, new Where(Location.FLOOR, floor));
  }

  public static Pair<Integer,Integer> getAvailableEquipment(String equipType) {
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
}
