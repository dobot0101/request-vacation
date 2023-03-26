package com.test.requestvacation.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Profile;

import com.test.requestvacation.entity.Member;

@Profile("test")
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
        ArrayList<Member> memberList = (ArrayList<Member>) store.values();
        for (Object object : memberList) {
            Member member = (Member) object;
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
