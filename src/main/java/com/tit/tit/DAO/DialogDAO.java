package com.tit.tit.DAO;

import com.tit.tit.model.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogDAO extends JpaRepository<Dialog, Long> {
    @Query(value = "SELECT * FROM dialog AS d JOIN " +
            "(SELECT ds.dialog_id, MAX(ds.time) AS time FROM dialog ds GROUP BY ds.dialog_id) AS dg " +
            "ON d.dialog_id = dg.dialog_id AND d.time = dg.time WHERE d.sender=:userId OR d.recipient=:userId", nativeQuery = true)
    List<Dialog> findDialogs(Long userId);


    List<Dialog> findByDialogId(String dialogId);
}
