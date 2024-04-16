package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bootcamp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BootCampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "bootcamp_capability",
            joinColumns = @JoinColumn(name = "bootcamp_id"),
            inverseJoinColumns = @JoinColumn(name = "capability_id")
    )
    private Set<CapabilityEntity> capabilitiesList = new HashSet<>();
}
