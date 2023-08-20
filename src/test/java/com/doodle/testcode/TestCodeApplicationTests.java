package com.doodle.testcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.doodle.testcode.domain.Study;
import com.doodle.testcode.study.StudyStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

//@SpringBootTest
//@ExtendWith(FindSlowTestExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestCodeApplicationTests {

  int value = 1;

  @RegisterExtension
  static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

  @BeforeAll
  static void beforeAll() {
    System.out.println("beforeAll");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("afterAll");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("beforeEach");
  }

  @AfterEach
  void afterEach() {
    System.out.println("afterEach");
  }

  @Order(3)
  @FastTest
  @EnabledOnOs(value = OS.MAC)
  void test_for_mac() {
    System.out.println(value++);
    Study myStudy = new Study();
    assertNotEquals(StudyStatus.DONE, myStudy.getStatus(), () -> "스터디를 처음 만들면 TODO 상태다.");
  }

  @Order(2)
  @Test
  @EnabledOnOs(OS.WINDOWS)
  void test_for_windows() {
    System.out.println(value++);
    Study myStudy = new Study();
    assertNotEquals(StudyStatus.DONE, myStudy.getStatus(), () -> "스터디를 처음 만들면 TODO 상태다.");
  }

  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @CsvSource({"자바 스터디,10", "스프링,20"})
  void object_test(@AggregateWith(StudyAggregator.class) Study study) {
    System.out.println(value++);
    assertEquals(StudyStatus.TODO, study.getStatus(), () -> "대기(TODO) 상태가 아닙니다.");
  }

  static class StudyAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
        throws ArgumentsAggregationException {
      return new Study(accessor.getString(0), accessor.getInteger(1));
    }
  }
}
