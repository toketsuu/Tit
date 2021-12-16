package com.tit.tit.converter.DTO;

import com.tit.tit.model.AbstractIdentity;
import com.tit.tit.model.ChatMessage;
import com.tit.tit.model.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class ChatDTO extends AbstractIdentity {

    private String name;

    private String createTime;

    private String photo;
}
