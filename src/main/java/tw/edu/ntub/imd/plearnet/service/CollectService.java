package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.CollectBean;

import java.util.List;
import java.util.Optional;

public interface CollectService extends BaseService<CollectBean, Integer>{
    List<CollectBean> searchAll(Integer userId);
    Optional<CollectBean> getByUserIdAndTopicId(Integer userId, Integer topicId);
}
