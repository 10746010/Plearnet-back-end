package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;

public interface UserAccountService extends BaseService<UserAccountBean, Integer> {
    public void update(Integer id, UserAccountBean userAccountBean);
    public Boolean getByUsername(String username);
}
