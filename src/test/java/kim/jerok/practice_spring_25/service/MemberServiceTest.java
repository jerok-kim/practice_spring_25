package kim.jerok.practice_spring_25.service;

import kim.jerok.practice_spring_25.domain.Member;
import kim.jerok.practice_spring_25.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입_test() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test()
    @DisplayName("중복회원 예외 테스트")
    void 중복_회원_예외_test() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)  // 예외가 발생해야 한다!!
        );
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }

}
