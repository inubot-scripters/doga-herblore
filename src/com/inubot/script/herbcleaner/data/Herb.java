package com.inubot.script.herbcleaner.data;

import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.component.tdi.Skills;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Herb {

  GUAM("Guam leaf", 3, 199, 249),
  MARRENTILL("Marrentill", 5, 201, 251),
  TARROMIN("Tarromin", 11, 203, 253),
  HARRALANDER("Harralander", 20, 205, 255),
  RANARR("Ranarr weed", 25, 207, 257),
  TOADFLAX("Toadflax", 30, 3049, 2998),
  IRIT("Irit leaf", 40, 209, 259),
  AVANTOE("Avantoe", 48, 211, 261),
  KWUARM("Kwuarm", 54, 213, 263),
  SNAPDRAGON("Snapdragon", 59, 3051, 3000),
  CADANTINE("Cadantine", 65, 215, 265),
  LANTADYME("Lantadyme", 67, 2485, 2481),
  DWARF_WEED("Dwarf weed", 70, 217, 267),
  TORSTOL("Torstol", 75, 219, 269);

  public static final int[] GRIMY_IDS = Stream.of(Herb.values()).mapToInt(Herb::getGrimyId).toArray();
  public static final int[] CLEAN_IDS = Stream.of(Herb.values()).mapToInt(Herb::getGrimyId).toArray();

  private final String name;
  private final int level;
  private final int grimyId;
  private final int cleanId;

  private int lastBuyTick = -24000;

  Herb(String name, int level, int grimyId, int cleanId) {
    this.name = name;
    this.level = level;
    this.grimyId = grimyId;
    this.cleanId = cleanId;
  }

  public static List<Herb> getCleanable(int ignoreLevel) {
    //use current level instead of base level in case i add boosting in the future
    int level = Skills.getCurrentLevel(Skill.HERBLORE);
    if (ignoreLevel != -1) {
      level = Math.min(ignoreLevel, level);
    }

    List<Herb> herbs = new ArrayList<>();
    for (Herb herb : Herb.values()) {
      if (herb.level <= level) {
        herbs.add(herb);
      }
    }

    return herbs;
  }

  public int getLevel() {
    return level;
  }

  public int getGrimyId() {
    return grimyId;
  }

  public int getCleanId() {
    return cleanId;
  }

  public int getLastBuyTick() {
    return lastBuyTick;
  }

  public void setLastBuyTick(int lastBuyTick) {
    this.lastBuyTick = lastBuyTick;
  }

  public String getName(boolean clean) {
    if (clean) {
      return name;
    }

    return "Grimy " + name.toLowerCase();
  }

  @Override
  public String toString() {
    return name;
  }
}
