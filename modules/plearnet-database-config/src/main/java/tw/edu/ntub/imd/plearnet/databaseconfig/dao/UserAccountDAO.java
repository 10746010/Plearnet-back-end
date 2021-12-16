package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, Integer> {

    List<UserAccount> findAll(Sort sort);

    Optional<UserAccount> findById(Integer id);

    Optional<UserAccount> findByAccount(String account);
}
