package com.tit.tit.service;

import com.tit.tit.converter.DTO.DialogDTO;
import com.tit.tit.model.Dialog;

import java.util.List;

public interface DialogService {
    void addMessage(Dialog message);

    List<Dialog> showDialog(Long userId, Long sel);

    List<DialogDTO> showDialogs(Long userId);

    void delMessage(Long messageId);

    void delDialog(Long userId, Long sel);

    void updateMsg(Long msgId, String message);
}
