package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.plearnet.bean.TagBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.TagDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Tag;
import tw.edu.ntub.imd.plearnet.service.TagService;
import tw.edu.ntub.imd.plearnet.service.transformer.TagTransformer;

import java.util.List;

@Service
public class TagServiceImpl extends BaseServiceImpl<TagBean, Tag, String> implements TagService {
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
    public List<TagBean> searchAll(Integer tagType){
        return CollectionUtils.map(tagDAO.findByTagType(tagType),transformer :: transferToBean);
    }
}
