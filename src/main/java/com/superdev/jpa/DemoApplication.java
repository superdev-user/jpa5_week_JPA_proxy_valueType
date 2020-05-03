package com.superdev.jpa;

import com.superdev.jpa.proxy.entity.Member;
import com.superdev.jpa.proxy.entity.Team;

import javax.persistence.*;
import java.util.Arrays;

public class DemoApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작

            Team team = Team.builder()
                    .name("VOS")
                    .build();

            Member member = Member.builder()
                    .username("최윤진")
                    .team(team)
                    .build();

            em.persist(team);
            em.persist(member);
            tx.commit();//트랜잭션 커밋

            em.clear();


            tx.begin(); //트랜잭션 시작
            printUser(em, member.getId());
//            printUserAndTeam(em, member.getId());
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void printUserAndTeam (EntityManager em , Long memberId) {

        System.out.println("-----printUserAndTeam START------");
        Member findMember = em.getReference(Member.class , memberId);
        Team findTeam = findMember.getTeam();

        System.out.println("객체의 이름 : " + findMember.getClass().getName());
        System.out.println("회원 이름 : " + findMember.getUsername());
        System.out.println("팀 이름 : " + findTeam.getName());
        System.out.println("-----printUserAndTeam END------");
    }

    public static void printUser(EntityManager em , Long memberId) {
        System.out.println("-----printUser START------");
        Member findMember = em.getReference(Member.class , memberId);

        System.out.println("객체의 이름 : " + findMember.getClass().getName());
        System.out.println("회원 이름 : " + findMember.getUsername());
        System.out.println("-----printUser END------");
    }
}
