package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

import java.util.List;

@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String> {

    List<UserAccount> findAll(Sort sort);
import java.util.Optional;
    Optional<UserAccount> findById(Integer userId);
}
