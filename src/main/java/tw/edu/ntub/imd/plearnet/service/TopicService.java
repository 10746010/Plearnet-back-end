package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.TopicBean;

import java.util.List;

public interface TopicService extends BaseService<TopicBean, Integer>{
    List<TopicBean> searchMessageByTopicID(Integer topicID);
    List<TopicBean> searchAll(Integer tag);
}
