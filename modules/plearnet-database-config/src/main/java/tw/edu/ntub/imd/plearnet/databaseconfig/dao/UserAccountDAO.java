package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String> {

}
