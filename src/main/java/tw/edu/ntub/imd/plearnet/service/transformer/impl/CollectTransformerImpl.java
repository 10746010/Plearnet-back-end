package tw.edu.ntub.imd.plearnet.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.CollectBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Collect;
import tw.edu.ntub.imd.plearnet.service.transformer.CollectTransformer;

@Component
public class CollectTransformerImpl implements CollectTransformer {

    @NonNull
    @Override
    public Collect transferToEntity(@NonNull CollectBean collectBean){return JavaBeanUtils.copy(collectBean, new Collect()); }

    @NonNull
    @Override
    public CollectBean transferToBean(@NonNull Collect collect){return  JavaBeanUtils.copy(collect, new CollectBean()); }
}
