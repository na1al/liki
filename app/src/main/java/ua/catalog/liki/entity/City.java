package ua.catalog.liki.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class City {

    public static final int DEFAULT_CITY_ID = 1116;

    @Id
    private int id;

    @Basic
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Getter
    @CreationTimestamp
    @Column(columnDefinition = "timestamp default now() not null", updatable = false)
    protected Date createdAt;

}

