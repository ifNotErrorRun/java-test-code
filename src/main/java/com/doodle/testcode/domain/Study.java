package com.doodle.testcode.domain;

import com.doodle.testcode.study.StudyStatus;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Study {

  private final StudyStatus status;

  private String name;

  private int limit;

  private Member member;

  public Study() {
    this.status = StudyStatus.TODO;
  }

  public Study(StudyStatus status) {
    this.status = status;
  }

  public     Study(String name, int limit) {
    this();
    this.name = name;
    this.limit = limit;
  }

  public void setOwner(Member     member) {
    this.member = member;
  }
}
