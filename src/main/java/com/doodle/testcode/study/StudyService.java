package com.doodle.testcode.study;


import com.doodle.testcode.domain.Member;
import com.doodle.testcode.domain.Study;
import com.doodle.testcode.member.MemberNotFoundException;
import com.doodle.testcode.member.MemberService;
import java.util.Optional;

public class StudyService {

  private final MemberService memberService;
  private final StudyRepository studyRepository;

  public StudyService(MemberService memberService, StudyRepository studyRepository) {
    assert memberService != null;
    assert studyRepository != null;
    this.memberService = memberService;
    this.studyRepository = studyRepository;
  }

  public Study createNewStudy(Long memberId, Study study) throws MemberNotFoundException {
    Optional<Member> memberOptional = memberService.findById(memberId);
    study.setOwner(memberOptional.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: " + memberId)));
    return studyRepository.save(study);
  }

}
