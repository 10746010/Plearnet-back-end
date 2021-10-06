package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.TopicBean;

import java.util.List;

public interface TopicService extends BaseService<TopicBean, String>{
    List<TopicBean> searchMessageByTopicID(Integer topicID);
}
