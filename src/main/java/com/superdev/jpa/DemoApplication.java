package com.superdev.jpa;

import com.superdev.jpa.proxy.entity.Address;
import com.superdev.jpa.proxy.entity.Member;

import javax.persistence.*;

public class DemoApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작

            Member member1 = Member.builder()
                    .name("최윤진")
                    .age(28)
                    .address(Address.builder().city("서울").street("봉천동").zipcode("100-48").build())
                    .build();
            em.persist(member1);
            em.flush();

            Address address = member1.getAddress().clone();

            Member member2 = Member.builder()
                    .name("최윤진")
                    .age(28)
                    .address(address)
                    .build();
            em.persist(member2);

            member2.getAddress();


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
