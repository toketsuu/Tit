package com.tit.tit.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessage extends AbstractIdentity {
    private Long sender;
    private String message;
    private String time;

    @ManyToOne (optional=false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn
    private Chat chat;

}
