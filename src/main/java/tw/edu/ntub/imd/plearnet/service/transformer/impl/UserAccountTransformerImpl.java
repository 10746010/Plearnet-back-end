package tw.edu.ntub.imd.plearnet.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.plearnet.service.transformer.UserAccountTransformer;

@Component
public class UserAccountTransformerImpl implements UserAccountTransformer {
    @NonNull
    @Override
    public UserAccount transferToEntity(@NonNull UserAccountBean userAccountBean) {
        return JavaBeanUtils.copy(userAccountBean, new UserAccount());
    }

    @Override
    public UserAccountBean transferToBean(UserAccount userid) {
        return JavaBeanUtils.copy(userid, new UserAccountBean());
    }
}
