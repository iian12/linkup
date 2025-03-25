package com.dju.linkup.domain.post.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @Tsid
    private String id;

    private String name;
    private int count;

    @Builder
    public Hashtag(String name, int count) {
        this.name = name;
        this.count = 0;
    }

    public void addCount() {
        this.count++;
    }
}
