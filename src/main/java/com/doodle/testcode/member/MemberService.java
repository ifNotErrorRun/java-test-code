package com.doodle.testcode.member;

import com.doodle.testcode.domain.Member;
import java.util.Optional;

public interface MemberService {

  Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
