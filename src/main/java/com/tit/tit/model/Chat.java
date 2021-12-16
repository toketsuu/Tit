package com.tit.tit.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class Chat extends AbstractIdentity{

    private String name;

    private String createTime;

    private String photo;

    private Long creator;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<User> user;


    @OneToMany (mappedBy="chat", fetch=FetchType.EAGER)
    private List<ChatMessage> messages;

    public Chat(String name, String createTime, String photo, Long creator, List<User> user) {
        this.name = name;
        this.createTime = createTime;
        this.photo = photo;
        this.creator = creator;
        this.user = user;
    }
}
