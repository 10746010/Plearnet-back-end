package tw.edu.ntub.imd.plearnet.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.TagBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Tag;
import tw.edu.ntub.imd.plearnet.service.transformer.TagTransformer;

@Component
public class TagTransformerImpl implements TagTransformer {

    @NonNull
    @Override
    public Tag transferToEntity(@NonNull TagBean tagBean){return JavaBeanUtils.copy(tagBean, new Tag()); }

    @NonNull
    @Override
    public TagBean transferToBean(@NonNull Tag tag){return JavaBeanUtils.copy(tag, new TagBean());}
}
