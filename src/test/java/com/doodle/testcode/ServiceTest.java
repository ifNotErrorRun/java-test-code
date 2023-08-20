package com.doodle.testcode;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.doodle.testcode.domain.Member;
import com.doodle.testcode.member.MemberService;
import com.doodle.testcode.study.StudyRepository;
import com.doodle.testcode.study.StudyService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceTest {


  @Test
  void createStudyService(@Mock MemberService memberService,
                          @Mock StudyRepository studyRepository) {
    Optional<Member> optional = memberService.findById(1L);
    assertNull(optional);

    StudyService studyService = new StudyService(memberService,studyRepository);
    assertNotNull(studyService);

  }
}
