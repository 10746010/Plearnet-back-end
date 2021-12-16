package tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener;

import tw.edu.ntub.imd.plearnet.databaseconfig.entity.History;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class HistoryListener {
    @PrePersist
    public void preSave(History history) {
        if(history.getViewTime() == null){
            history.setViewTime(LocalDateTime.now());
        }
    }
}
