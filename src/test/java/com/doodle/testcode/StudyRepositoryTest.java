package com.doodle.testcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.doodle.testcode.domain.Study;
import com.doodle.testcode.study.StudyRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudyRepositoryTest {

  @Autowired
  @Qualifier( "studyRepository")
  StudyRepository repository;

  @Test
  void save() {
    repository.deleteAll();
    Study study = new Study("Java",10);
    repository.save(study);
    List<Study> all = repository.findAll();
    assertEquals(1, all.size());
  }

}
