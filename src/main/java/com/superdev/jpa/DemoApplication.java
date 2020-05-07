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

            Member member1 = Member.builder()
                    .username("원빈")
                    .team(team)
                    .build();

            em.persist(team);
            em.persist(member);
            em.persist(member1);

            em.flush();
            em.clear();


            //printUser(em, member.getId());
            //printUserAndTeam(em, member.getId());
            //proxyEqualsClass(em);
            //doesItExistInTheContextOfPersistenceExam1(em);
            doesItExistInTheContextOfPersistenceExam2(em);
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

    public static void proxyEqualsClass (EntityManager em ) {
        Member member = em.find(Member.class , 2L);
        Member memberProxy = em.getReference(Member.class , 3L);

        System.out.println("-----proxyEqualsClass START------");
        System.out.println("member find className :: " + member.getClass().getName());
        System.out.println("memberProxy getReference className :: " + memberProxy.getClass().getName());
        System.out.println("find == getReference :: " + (member == memberProxy));
        System.out.println("find member is Member.class??  :: " + (member instanceof  Member));
        System.out.println("getReference memberProxy is Member.class??  :: " + (memberProxy instanceof  Member));
        System.out.println("-----proxyEqualsClass END------");
    }

    public static void doesItExistInTheContextOfPersistenceExam1(EntityManager em) {
        Member member = em.find(Member.class , 2L);
        Member memberProxy = em.getReference(Member.class , 2L);

        System.out.println("-----doesItExistInTheContextOfPersistence START------");
        System.out.println("member find className :: " + member.getClass().getName());                      //com.superdev.jpa.proxy.entity.Member
        System.out.println("memberProxy getReference className :: " + memberProxy.getClass().getName());    //com.superdev.jpa.proxy.entity.Member
        System.out.println(member == memberProxy);
        System.out.println("-----doesItExistInTheContextOfPersistence START------");
    }


    public static void doesItExistInTheContextOfPersistenceExam2(EntityManager em) {
        Member memberProxy = em.getReference(Member.class , 2L);
        Member member = em.find(Member.class , 2L);
        System.out.println("-----doesItExistInTheContextOfPersistence START------");
        System.out.println("member find className :: " + member.getClass().getName());                      //com.superdev.jpa.proxy.entity.Member
        System.out.println("memberProxy getReference className :: " + memberProxy.getClass().getName());    //com.superdev.jpa.proxy.entity.Member

        // 두 객체는 비록 참조값은 다르지만 같다를 보장해줘야 한다.
        // Proxy든 아니든 개발에 문제가 없도록 true를 보장해줘야 한다 .
        System.out.println(member == memberProxy);
        System.out.println("-----doesItExistInTheContextOfPersistence START------");
    }
}
