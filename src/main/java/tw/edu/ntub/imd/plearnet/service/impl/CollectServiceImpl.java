package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.CollectBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.CollectDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Collect;
import tw.edu.ntub.imd.plearnet.exception.NotFoundException;
import tw.edu.ntub.imd.plearnet.service.CollectService;
import tw.edu.ntub.imd.plearnet.service.transformer.CollectTransformer;

import java.util.List;
import java.util.Optional;

@Service
public class CollectServiceImpl extends BaseServiceImpl<CollectBean, Collect, Integer> implements CollectService {
    private final CollectDAO collectDAO;
    private final CollectTransformer transformer;

    public CollectServiceImpl(CollectDAO collectDAO, CollectTransformer transformer){
        super(collectDAO, transformer);
        this.collectDAO = collectDAO;
        this.transformer = transformer;
    }

    @Override
    public CollectBean save(CollectBean collectBean){
        Collect collect = collectDAO.save(transformer.transferToEntity(collectBean));
        return transformer.transferToBean(collect);
    }

    public Optional<CollectBean> getByUserIdAndTopicId(Integer userId, Integer topicId){
        Optional<Collect> optional = collectDAO.getByUserIdAndTopicId(userId,topicId);
        if (optional.isPresent()) {
            Collect collect = optional.get();
            CollectBean collectBean = transformer.transferToBean(collect);
            return Optional.of(collectBean);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void update(Integer id, CollectBean collectBean) {
        Optional<Collect> optional = collectDAO.findById(id);
        if (optional.isPresent()) {
            Collect collect = optional.get();
            JavaBeanUtils.copy(collectBean, collect);
            collectDAO.update(collect);
        } else {
            throw new NotFoundException("找不到資料, id = " + id);
        }
    }

    @Override
    public Optional<CollectBean> getById(Integer id){return Optional.empty(); }

    public List<CollectBean> searchAll(Integer userId){
        return CollectionUtils.map(collectDAO.findByUserId(userId), transformer::transferToBean);
    }
}
