package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.CollectBean;

import java.util.List;

public interface CollectService extends BaseService<CollectBean, Integer>{
    List<CollectBean> searchAll(Integer userId);
}
