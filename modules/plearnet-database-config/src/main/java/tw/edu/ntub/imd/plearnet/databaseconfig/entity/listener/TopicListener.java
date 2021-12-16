package tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener;

import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class TopicListener {
    @PrePersist
    public void preSave(Topic topic) {
        if(topic.getCreateDate() == null){
            topic.setCreateDate(LocalDateTime.now());
            topic.setEditDate(LocalDateTime.now());
        }else{
            topic.setEditDate(LocalDateTime.now());
        }
    }
}

