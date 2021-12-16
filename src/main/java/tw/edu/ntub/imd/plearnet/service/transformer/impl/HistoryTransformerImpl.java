package tw.edu.ntub.imd.plearnet.service.transformer.impl;


import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.HistoryBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.History;
import tw.edu.ntub.imd.plearnet.service.transformer.HistoryTransformer;

@Component
public class HistoryTransformerImpl implements HistoryTransformer {

    @NonNull
    @Override
    public History transferToEntity(@NonNull HistoryBean historyBean){return JavaBeanUtils.copy(historyBean, new History());  }

    @NonNull
    @Override
    public  HistoryBean transferToBean(@NonNull History history){return  JavaBeanUtils.copy(history, new HistoryBean()); }
}
