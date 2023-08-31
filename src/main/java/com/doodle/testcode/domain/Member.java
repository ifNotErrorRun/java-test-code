package com.doodle.testcode.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Member {

  @Id
  @GeneratedValue
  private long id;
  private String email;

}
