package com.example.database.service;

import com.example.database.repository.MemberRepository;
import com.example.database.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<MemberVo> findAll() {
        return new ArrayList<>(memberRepository.findAll());
    }

    public Optional<MemberVo> findById(Long mbrNo) {
        return memberRepository.findById(mbrNo);
    }

    public void deleteById(Long mbrNo) {
        memberRepository.deleteById(mbrNo);
    }

    public MemberVo save(MemberVo member) {
        memberRepository.save(member);
        return member;
    }

    public void updateById(Long mbrNo, MemberVo member) {
        Optional<MemberVo> e = memberRepository.findById(mbrNo);

        if (e.isPresent()) {
            e.get().setMbrNo(member.getMbrNo());
            e.get().setId(member.getId());
            e.get().setName(member.getName());
            memberRepository.save(member);
        }
    }
}
