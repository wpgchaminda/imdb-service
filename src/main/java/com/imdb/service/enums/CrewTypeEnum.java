package com.imdb.service.enums;

public enum CrewTypeEnum {
  DIRECTOR(1, "Director"),
  WRITER(2, "Writer");

  private int id;
  private String description;

  CrewTypeEnum(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static CrewTypeEnum getById(int id) {
    for (CrewTypeEnum e : CrewTypeEnum.values()) {
      if (e.getId() == id) {
        return e;
      }
    }
    return null;
  }
}
