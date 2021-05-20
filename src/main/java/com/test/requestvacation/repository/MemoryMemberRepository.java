package com.test.requestvacation.repository;

import com.test.requestvacation.entity.Member;
import org.springframework.data.domain.Example;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Member findByEmailAndPassword(String email, String password) {
        ArrayList memberList = (ArrayList) store.values();
        for (Object object: memberList) {
            Member member = (Member)object;
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }
}
