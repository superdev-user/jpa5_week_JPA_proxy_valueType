package com.superdev.jpa.proxy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int age;

    @Embedded Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city" , column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street" , column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode" , column = @Column(name = "COMPANY_ZIPCODE"))
    })
    Address companyAddress;


    @Builder
    public Member(String name, int age, Address address,Address companyAddress) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.companyAddress = companyAddress;
    }
}
