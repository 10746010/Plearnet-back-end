package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.plearnet.bean.TopicBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.TopicDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.plearnet.service.TopicService;
import tw.edu.ntub.imd.plearnet.service.transformer.TopicTransformer;

import java.util.Collection;
import java.util.List;

@Service
public class TopicServiceImpl extends BaseServiceImpl<TopicBean, Topic, String> implements TopicService {
    private final TopicDAO topicDAO;
    private final TopicTransformer transformer;

    public TopicServiceImpl(TopicDAO topicDAO,TopicTransformer transformer){
        super(topicDAO, transformer);
        this.topicDAO = topicDAO;
        this.transformer = transformer;
    }

    @Override
    public TopicBean save(TopicBean topicBean){
        Topic topic = topicDAO.save(transformer.transferToEntity(topicBean));
        return transformer.transferToBean(topic);
    }

    @Override
    public List<TopicBean> searchAll(Integer tag){
        return CollectionUtils.map(topicDAO.findByTagId(tag),transformer :: transferToBean);
    }
}
