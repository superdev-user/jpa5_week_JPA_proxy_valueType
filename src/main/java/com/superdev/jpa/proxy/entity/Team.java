package com.superdev.jpa.proxy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team" , cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Member> members = new ArrayList<Member>();

    @Builder
    public Team(String name) {
        this.name = name;
    }
}
