package com.superdev.jpa.proxy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @ManyToOne(fetch = FetchType.EAGER , optional = false)
//    @JoinColumn(nullable = false)
    private Team team;

    @Builder
    public Member(String username ,Team team) {
        this.username = username;
        this.team = team;
    }
}
