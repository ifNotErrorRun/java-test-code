package com.doodle.testcode.member;

import com.doodle.testcode.domain.Member;
import com.doodle.testcode.domain.Study;
import java.util.Optional;

public interface MemberService {

  Optional<Member> findById(Long memberId);

  void validate(Long memberId);

  void notify(Study newstudy);

  void notify(Member member);
}
