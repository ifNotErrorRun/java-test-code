package com.doodle.testcode;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.doodle.testcode.member.MemberService;
import com.doodle.testcode.study.StudyRepository;
import com.doodle.testcode.study.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

  @Mock
  MemberService memberService;
  @Mock
  StudyRepository studyRepository;

  @Test
  void createStudyService() {
    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);
  }
}
