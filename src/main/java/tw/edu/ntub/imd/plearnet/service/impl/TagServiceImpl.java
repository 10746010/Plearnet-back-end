package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.MessageBean;
import tw.edu.ntub.imd.plearnet.bean.TagBean;
import tw.edu.ntub.imd.plearnet.bean.TopicBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.TagDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Tag;
import tw.edu.ntub.imd.plearnet.exception.NotFoundException;
import tw.edu.ntub.imd.plearnet.service.TagService;
import tw.edu.ntub.imd.plearnet.service.transformer.TagTransformer;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends BaseServiceImpl<TagBean, Tag, Integer> implements TagService {
    private final TagDAO tagDAO;
    private final TagTransformer transformer;

    public TagServiceImpl(TagDAO tagDAO, TagTransformer transformer){
        super(tagDAO, transformer);
        this.tagDAO = tagDAO;
        this.transformer = transformer;
    }

    @Override
    public TagBean save(TagBean tagBean){
        Tag tag = tagDAO.save(transformer.transferToEntity(tagBean));
        return transformer.transferToBean(tag);
    }

    @Override
    public void update(Integer id, TagBean tagBean) {
        Optional<Tag> optional = tagDAO.findById(id);
        if (optional.isPresent()) {
            Tag tag = optional.get();
            JavaBeanUtils.copy(tagBean, tag);
            tagDAO.update(tag);
        } else {
            throw new NotFoundException("找不到資料, id = " + id);
        }
    }

    public List<TagBean> searchAll(Integer tagType){
        return CollectionUtils.map(tagDAO.findByTagType(tagType),transformer :: transferToBean);
    }
}
