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


            Address address = Address.builder().city("서울").street("봉천동").zipcode("100-48").build();
            Address companyAddress = Address.builder().city("서울시").street("관악구").zipcode("1234-56").build();


            Member member = Member.builder()
                    .name("최윤진")
                    .age(28)
                    .address(address)
                    .companyAddress(companyAddress)
                    .build();

            em.persist(member);


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
