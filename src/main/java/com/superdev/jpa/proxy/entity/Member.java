package com.superdev.jpa.proxy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int age;


    @ElementCollection
    @CollectionTable(name = "ADDRESS" ,
        joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    List<Address> address;


    @Builder
    public Member(String name, int age, List<Address> address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
