package me.kingcjy.demo.service;

import lombok.RequiredArgsConstructor;
import me.kingcjy.demo.domain.Member;
import me.kingcjy.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id).get();
    }
}
