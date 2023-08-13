package com.doodle.testcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

//@SpringBootTest
class TestCodeApplicationTests {

  @Test
  @EnabledOnOs(value = OS.MAC)
  @DisplayName("테스트 코드 작성연습 - mac")
  void test() {
    Study myStudy = new Study();
    assertNotEquals(StudyStatus.DONE, myStudy.getStatus(), () -> "스터디를 처음 만들면 TODO 상태다.");
  }

  @Test
  @DisplayName("테스트 코드 작성연습 - window")
  @EnabledOnOs(OS.WINDOWS)
  void test2() {
    Study myStudy = new Study();
    assertNotEquals(StudyStatus.DONE, myStudy.getStatus(), () -> "스터디를 처음 만들면 TODO 상태다.");
  }

  @DisplayName("반복 테스트")
  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @CsvSource({"자바 스터디,10", "스프링,20"})
  void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study) {
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
