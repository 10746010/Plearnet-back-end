package tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener;

import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class UserAccountListener {
    @PrePersist
    public void preSave(UserAccount userAccount) {
        if(userAccount.getRegister() == null){
            userAccount.setRegister(LocalDateTime.now());
        }
    }
}
