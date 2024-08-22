package com.example.database.repository;

import com.example.database.vo.MemberVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("unused")
public interface MemberRepository extends JpaRepository<MemberVo, Long> {
    //비워있어도 잘 작동함.
    // long 이 아니라 Long 으로 작성. ex) int => Integer 같이 primitive 형식 사용못함

    // findBy 뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다
    List<MemberVo> findById(String id);

    List<MemberVo> findByName(String name);

    //like 검색도 가능
    List<MemberVo> findByNameLike(String keyword);
}
