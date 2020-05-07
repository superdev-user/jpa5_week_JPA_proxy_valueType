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

            System.out.println("===== Start eager loading ===== ");
            Member findMember = em.find(Member.class , member.getId());
            Team eagerFindTeam = findMember.getTeam();
            System.out.println("===== End eager loading =====");


            tx.commit();//트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
