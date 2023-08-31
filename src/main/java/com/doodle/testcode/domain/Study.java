package com.doodle.testcode.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class Study {

  @Id
  @GeneratedValue
  private long id;
  private String name;
  private int limitMember;
  private StudyStatus status = StudyStatus.TODO;
  private LocalDateTime openedDateTime;

  public Study(String name, int limitMember) {
    this();
    this.name = name;
    this.limitMember = limitMember;
  }

  public Study() {
  }

  public void open() {
    this.status = StudyStatus.OPENED;
    this.openedDateTime = LocalDateTime.now();
  }

  public long getOwnerId() {
    return this.id;
  }

  public void setOwnerId(Long memberId) {
    this.id = memberId;
  }
}
