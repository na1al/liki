package ua.catalog.liki.entity;

import javax.persistence.*;

@Entity
public class Integration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Basic
    private String name;

    @Basic
    private boolean isActive;

}

