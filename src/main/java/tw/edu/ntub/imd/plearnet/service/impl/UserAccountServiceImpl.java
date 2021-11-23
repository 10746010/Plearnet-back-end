package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.UserAccountDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.plearnet.exception.NotFoundException;
import tw.edu.ntub.imd.plearnet.service.UserAccountService;
import tw.edu.ntub.imd.plearnet.service.transformer.UserAccountTransformer;

import java.util.Optional;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountBean, UserAccount, Integer> implements UserAccountService {
    private final UserAccountDAO userAccountDAO;
    private final UserAccountTransformer transformer;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountDAO userAccountDAO, UserAccountTransformer transformer, PasswordEncoder passwordEncoder) {
        super(userAccountDAO, transformer);
        this.userAccountDAO = userAccountDAO;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserAccountBean> getById(Integer id) {
        Optional<UserAccount> optional = userAccountDAO.findById(id);
        if (optional.isPresent()) {
            UserAccount userAccount = optional.get();
            UserAccountBean userAccountBean = transformer.transferToBean(userAccount);
            return Optional.of(userAccountBean);
        } else{
            return Optional.empty();
        }
    }

    @Override
    public UserAccountBean save(UserAccountBean userAccountBean) {
        UserAccount userAccount = userAccountDAO.save(transformer.transferToEntity(userAccountBean));
        return transformer.transferToBean(userAccount);
    }

    @Override
    public void update(Integer id, UserAccountBean userAccountBean) {
        Optional<UserAccount> optional = userAccountDAO.findById(id);
        if (optional.isPresent()) {
            UserAccount userAccount = optional.get();
            JavaBeanUtils.copy(userAccountBean, userAccount);
            userAccountDAO.update(userAccount);
        } else {
            throw new NotFoundException("找不到資料, id = " + id);
        }
    }

    @Override
    public Boolean accountExsist(String account) {
        Optional<UserAccount> optional = userAccountDAO.findByAccount(account);
        if (optional.isPresent()) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    public Optional<UserAccountBean> getByAccount(String account) {
        Optional<UserAccount> optional = userAccountDAO.findByAccount(account);
        if (optional.isPresent()) {
            UserAccount userAccount = optional.get();
            UserAccountBean userAccountBean = transformer.transferToBean(userAccount);
            return Optional.of(userAccountBean);
        } else{
            return Optional.empty();
        }
    }
}
