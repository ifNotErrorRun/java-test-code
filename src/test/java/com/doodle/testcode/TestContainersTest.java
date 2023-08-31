package com.doodle.testcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.doodle.testcode.domain.Member;
import com.doodle.testcode.domain.Study;
import com.doodle.testcode.domain.StudyStatus;
import com.doodle.testcode.member.MemberService;
import com.doodle.testcode.study.StudyRepository;
import com.doodle.testcode.study.StudyService;
import java.io.File;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = StudyServiceTest.ContainerPropertyInitializer.class)
class StudyServiceTest {

  @Container
  static DockerComposeContainer composeContainer =
      new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
          .withExposedService("study-db", 5432);
  @Mock MemberService memberService;

  @Autowired
  StudyRepository studyRepository;

  @Value("${container.port}")
  int port;

  @Test
  void createNewStudy() {
    System.out.println("========");
    System.out.println(port);

    // Given
    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);

    Member member = new Member();
    member.setId(1L);
    member.setEmail("keesun@email.com");

    Study study = new Study( "테스트",10);

    given(memberService.findById(1L)).willReturn(Optional.of(member));

    // When
    Study openedStudy = studyService.createNewStudy(1L, study);

    // Then
    assertEquals(1L, openedStudy.getOwnerId());
    then(memberService).should(times(1)).notify(study);
    then(memberService).shouldHaveNoMoreInteractions();
  }

  @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
  @Test
  void openStudy() {
    // Given
    StudyService studyService = new StudyService(memberService, studyRepository);
    Study study = new Study("더 자바, 테스트",10);
    assertNull(study.getOpenedDateTime());

    // When
    studyService.openStudy(study);

    // Then
    assertEquals(StudyStatus.OPENED, study.getStatus());
    assertNotNull(study.getOpenedDateTime());
    then(memberService).should().notify(study);
  }

  static class ContainerPropertyInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
      TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-db", 5432))
          .applyTo(context.getEnvironment());
    }
  }

}