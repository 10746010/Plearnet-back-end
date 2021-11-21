package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.plearnet.bean.CollectBean;
import tw.edu.ntub.imd.plearnet.bean.HistoryBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.HistoryDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.History;
import tw.edu.ntub.imd.plearnet.service.HistoryService;
import tw.edu.ntub.imd.plearnet.service.transformer.HistoryTransformer;

import java.util.List;

@Service
public class HistoryServiceImpl extends BaseServiceImpl<HistoryBean, History, Integer> implements HistoryService {
    private final HistoryDAO historyDAO;
    private final HistoryTransformer transformer;

    public HistoryServiceImpl(HistoryDAO historyDAO, HistoryTransformer transformer){
        super(historyDAO, transformer);
        this.historyDAO = historyDAO;
        this.transformer =  transformer;
    }

    @Override
    public HistoryBean save(HistoryBean historyBean){
        History history = historyDAO.save(transformer.transferToEntity(historyBean));
        return transformer.transferToBean(history);
    }

    public List<HistoryBean> searchAll(Integer userId){
        return CollectionUtils.map(historyDAO.findByUserId(userId),transformer :: transferToBean);
    }

}
