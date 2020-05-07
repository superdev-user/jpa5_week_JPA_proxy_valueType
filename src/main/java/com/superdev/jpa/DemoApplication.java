package com.superdev.jpa;

import com.superdev.jpa.proxy.entity.Member;
import com.superdev.jpa.proxy.entity.Team;
import javafx.scene.Parent;

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
                    .name("VOS").build();

            Member member = Member.builder()
                    .username("최윤진")
                    .team(team)  // 연관관계 추가
                    .build();

            Member member1 = Member.builder()
                    .username("원빈")
                    .team(team)  // 연관관계 추가
                    .build();

            team.getMembers().add(member);
            team.getMembers().add(member1);

            em.persist(team);

            em.flush();
            em.clear();

            Team findParent = em.find(Team.class , team.getId());
            em.remove(findParent);

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
