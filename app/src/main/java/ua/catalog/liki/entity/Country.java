package ua.catalog.liki.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Country {

    @Id
    private int id;

    @Basic
    private String name;

    @Getter
    @CreationTimestamp
    @Column(columnDefinition = "timestamp default now() not null", updatable = false)
    protected Date createdAt;

}
