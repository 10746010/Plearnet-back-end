package tw.edu.ntub.imd.plearnet.service.impl;

import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.plearnet.bean.TagBean;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.UserAccountDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.plearnet.service.UserAccountService;
import tw.edu.ntub.imd.plearnet.service.transformer.UserAccountTransformer;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountBean, UserAccount, String> implements UserAccountService {
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
    public UserAccountBean save(UserAccountBean userAccountBean) {
//        if (userAccountDAO.existsById(userAccountBean.getId())) {
//            throw new DuplicateCreateException("此帳號已被註冊")
//        }
        UserAccount userAccount = userAccountDAO.saveAndFlush(transformer.transferToEntity(userAccountBean));
        userAccount.setPassword(passwordEncoder.encode(userAccountBean.getPassword()));
        userAccountDAO.save(userAccount);
        return transformer.transferToBean(userAccountDAO.save(userAccount));
    }

    @Override
    public List<UserAccountBean> searchAll(Integer tag) {
        return null;
    }

    @Override
    public void update(String id) {
        Optional<UserAccount> optional = userAccountDAO.findById(id);
        if(optional.isPresent()) {
            UserAccount userAccount = optional.get();
//            userAccount.setAvailable(true);
            userAccountDAO.update(userAccount);
        }
    }

//    @Override
//    public List<UserAccountBean> searchAll(Pager pager) {
//        List<UserAccount> userAccountList;
//        if (pager.isInfinity()) {
//            userAccountList = userAccountDAO.findAll();
//        } else {
//            Page<UserAccount> page = userAccountDAO.findAll(
//                    PageRequest.of(MathUtils.translateOneBasedToZeroBased(pager.getPage()), pager.getCount(),
//                            Sort.by(Sort.Order.desc(UserAccount_.AVAILABLE), Sort.Order.asc(UserAccount_.USERID)
//                            )
//                    )
//            );
//            userAccountList = page.getContent();
//        }
//        return CollectionUtils.map(userAccountList, transformer::transferToBean);
//    }

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
        return null;
    }
}
