package com.doodle.testcode;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.doodle.testcode.domain.Study;
import com.doodle.testcode.domain.StudyStatus;
import com.doodle.testcode.member.MemberService;
import com.doodle.testcode.study.StudyRepository;
import com.doodle.testcode.study.StudyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
  void openStudy() {
    //Given
    StudyService studyService = new StudyService(memberService, studyRepository);
    Study study = new Study("더 자바, 테스트", 10);
    Assertions.assertNull(study.getOpenedDateTime());
    given(studyRepository.save(study)).willReturn(study);

    //When
    studyService.openStudy(study);

    //Then
    Assertions.assertEquals(StudyStatus.OPENED, study.getStatus());
    Assertions.assertNotNull(study.getOpenedDateTime());
    then(memberService).should().notify(study);
  }

}
