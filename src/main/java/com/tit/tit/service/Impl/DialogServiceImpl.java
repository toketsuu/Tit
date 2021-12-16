package com.tit.tit.service.Impl;

import com.tit.tit.DAO.DialogDAO;
import com.tit.tit.DAO.UserDAO;
import com.tit.tit.converter.DTO.DialogDTO;
import com.tit.tit.model.Dialog;
import com.tit.tit.model.enums.DialogStatus;
import com.tit.tit.service.DialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

    @PersistenceContext
    @Autowired
    private EntityManager em;
    @Autowired
    DialogDAO dialogDAO;
    @Autowired
    UserDAO userDAO;

    @Override
    public void addMessage(Dialog message) {
        dialogDAO.save(message);
    }

    @Override
    public List<Dialog> showDialog(Long userId, Long sel) {
        List<Dialog> dlgList = em.createNamedQuery("Dialog.findDialog",Dialog.class)
                .setParameter("userId", userId)
                .setParameter("sel", sel)
                .getResultList();
        for (Dialog dlg: dlgList)
            if (dlg.getRecipient().equals(userId) && dlg.getStatus() == DialogStatus.SHIPPED.ordinal())
                dlg.setStatus(DialogStatus.READ.ordinal());
        return dlgList;
    }

    @Override
    public List<DialogDTO> showDialogs(Long userId) {
        List<DialogDTO> dialogs = new ArrayList<>();
        List<Dialog> dgList = dialogDAO.findDialogs(userId);
        for(Dialog dg : dgList)
        {
            Long sel = dg.getSender().equals(userId) ? dg.getRecipient() : dg.getSender();
            dialogs.add(new DialogDTO(
                    sel,
                    userDAO.findById(sel).orElseThrow().getName(),
                    dg.getMessage(),
                    dg.getTime(),
                    userDAO.findById(sel).orElseThrow().getPhoto(),
                    dg.getRecipient().equals(userId) ? dg.getStatus() : DialogStatus.READ.ordinal()
            ));
        }
        return dialogs;
    }

    @Override
    public void delMessage(Long messageId) {
        dialogDAO.deleteById(messageId);
    }

    @Override
    public void delDialog(Long userId, Long sel) {
        List<Dialog> dlgList = dialogDAO.findByDialogId(userId < sel ? userId + "_" + sel : sel + "_" + userId);
        for(Dialog dlg : dlgList )
            dialogDAO.delete(dlg);
    }

    @Override
    public void updateMsg(Long msgId, String message) {
        dialogDAO.findById(msgId).ifPresentOrElse(
                msg -> {
                    if (msg.getMessage().length() > 8 && msg.getMessage().endsWith(" (Изменено)"))
                        msg.setMessage(msg.getMessage().substring(0, msg.getMessage().length() - 11));
                    msg.setMessage(message + " (Изменено)");
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "message is not found, id: " + msgId);
                }
        );
    }
}
