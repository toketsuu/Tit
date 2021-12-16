package com.tit.tit.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQuery(name = "Dialog.findDialog", query = "SELECT d FROM Dialog d WHERE d.sender=:userId AND d.recipient=:sel OR d.sender=:sel AND d.recipient=:userId ORDER BY d.time")

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class Dialog extends AbstractIdentity {

    private Long sender;
    private Long recipient;
    private String dialogId;
    private String time;
    private String message;
    private int status;
}


