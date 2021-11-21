package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.CollectBean;
import tw.edu.ntub.imd.plearnet.bean.HistoryBean;

import java.util.List;

public interface HistoryService extends BaseService<HistoryBean,Integer>{
    List<HistoryBean> searchAll(Integer userId);
}
