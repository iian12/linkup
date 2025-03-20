package com.dju.linkup.domain.chat.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReadStatus {

    @Id
    @Tsid
    private String id;

    private String roomId;

}
