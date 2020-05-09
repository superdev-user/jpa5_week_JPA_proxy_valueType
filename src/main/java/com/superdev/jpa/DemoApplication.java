package com.superdev.jpa;

import com.superdev.jpa.proxy.entity.Address;
import com.superdev.jpa.proxy.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작

            Member member = Member.builder()
                    .name("윤진")
                    .age(28)
                    .address(new ArrayList<Address>())
                    .build();

            member.getAddress().add(Address.builder().city("경기도").street("오산시").zipcode("08580").build());
            member.getAddress().add(Address.builder().city("서울시").street("관악구").zipcode("08580").build());


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
