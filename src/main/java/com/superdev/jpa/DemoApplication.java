package com.superdev.jpa;

import com.superdev.jpa.proxy.entity.Member;
import com.superdev.jpa.proxy.entity.Team;
import javafx.scene.Parent;

import javax.persistence.*;
import java.util.ArrayList;
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

            System.out.println(" ===== 고아객체 삭제 시작! =====");
            Team findTeam = em.find(Team.class , team.getId());

            /**
             * orphanRemoval=true 옵션으로 인해 컬력션에서 Entity를 제거하면 데이터베이스의 데이터도 삭제된다.
             * 모든 자식 Entity를 지우기 위해서는 컬렉션을 비우면 된다.
             *
             * 밑에있는 주석을 풀고 하나씩 실행해보면 좋습니다.
             * */
            //findTeam.getMembers().remove(0);
            //findTeam.getMembers().clear();

            // "A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance
            // 새로 참조하는 컬렉션은 hibernate가 관리하지 않아서 안된다! 라는 메세지 입니다.

            // Exception Code
            //findTeam.setMembers(new ArrayList<Member>(Arrays.asList(Member.builder().username("원빈").team(findTeam).build())));

            // Success Code
            //findTeam.getMembers().clear();
            //findTeam.getMembers().addAll(new ArrayList<Member>(Arrays.asList(Member.builder().username("원빈").team(findTeam).build())));

            // 고아 객체 제거 기능은 영속성 컨텍스트를 flush할때 적용된다. ( flush 시점에 delete sql 이 실행된다 )
            em.flush();
            System.out.println(" ===== 고아객체 삭제 종료! =====");

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
