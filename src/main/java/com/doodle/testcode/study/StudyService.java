package com.doodle.testcode.study;


import com.doodle.testcode.domain.Member;
import com.doodle.testcode.domain.Study;
import com.doodle.testcode.member.MemberService;
import java.util.Optional;

public class StudyService {

  private final MemberService memberService;
  private final StudyRepository repository;


  public StudyService(MemberService memberService, StudyRepository repository) {
    if (memberService == null) {
      throw new IllegalArgumentException("Invalid MemberService");
    }
    if (repository == null) {
      throw new IllegalArgumentException("Invalid StudyRepository");
    }
    this.memberService = memberService;
    this.repository = repository;
  }

  public Study createNewStudy(Long memberId, Study study) {
    Optional<Member> member = memberService.findById(memberId);
    if (member.isPresent()) {
      study.setOwnerId(memberId);
    } else {
      throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
    }
    Study newstudy = repository.save(study);
    memberService.notify(newstudy);
    return newstudy;
  }

  public Study openStudy(Study study) {
    study.open();
    Study openedStudy = repository.save(study);
    memberService.notify(openedStudy);
    return openedStudy;
  }

}
