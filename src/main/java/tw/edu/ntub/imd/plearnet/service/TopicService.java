package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.TopicBean;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TopicService extends BaseService<TopicBean, Integer>{
    List<TopicBean> searchMessageByTopicID(Integer topicID);
    List<TopicBean> searchAll(Integer tag);
    List<TopicBean> searchAllByAuthor(Integer author);
}
