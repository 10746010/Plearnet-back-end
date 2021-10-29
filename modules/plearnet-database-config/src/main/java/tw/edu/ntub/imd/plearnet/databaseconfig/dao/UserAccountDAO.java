package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

import java.util.Optional;

@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String> {
    Optional<UserAccount> findById(Integer userId);
}
