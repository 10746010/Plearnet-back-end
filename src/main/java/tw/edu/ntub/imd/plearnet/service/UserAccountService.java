package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;

public interface UserAccountService extends BaseService<UserAccountBean, String> {
    void update(String id);
}
