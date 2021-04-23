package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.BaseViewDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.plearnet.dto.PageInfo;
import tw.edu.ntub.imd.plearnet.service.BaseViewService;
import tw.edu.ntub.imd.plearnet.service.transformer.BeanEntityTransformer;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseViewServiceImpl<B, E, ID extends Serializable> implements BaseViewService<B, ID> {
    private final BaseViewDAO<E, ID> baseDAO;
    private final BeanEntityTransformer<B, E> transformer;

    public BaseViewServiceImpl(BaseViewDAO<E, ID> d, BeanEntityTransformer<B, E> transformer) {
        Assert.notNull(d, "baseDAO不能為null");
        Assert.notNull(d, "transformer不能為null");
        this.baseDAO = d;
        this.transformer = transformer;
    }

    @Override
    public Optional<B> getById(ID id) {
        Optional<E> optional = baseDAO.findById(id);
        if (optional.isPresent()) {
            E entity = optional.get();
            B bean = transformer.transferToBean(entity);
            return Optional.of(bean);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<B> searchAll() {
        return CollectionUtils.map(baseDAO.findAll(), transformer::transferToBean);
    }

    @Override
    public List<B> searchAll(Pager pager) {
        List<E> entityList;
        if (pager.isInfinity()) {
            entityList = baseDAO.findAll();
        } else {
            PageRequest pageRequest = PageRequest.of(
                    MathUtils.translateOneBasedToZeroBased(pager.getPage()),
                    pager.getCount()
            );
            entityList = baseDAO.findAll(pageRequest).getContent();
        }
        return CollectionUtils.map(entityList, transformer::transferToBean);
    }

    @Override
    public List<B> searchByBean(B b) {
        List<E> eList = baseDAO.findAll(Example.of(transformer.transferToEntity(b)));
        return CollectionUtils.map(eList, transformer::transferToBean);
    }

    @Override
    public Optional<B> getByBean(B b) {
        Optional<E> optional = baseDAO.findOne(Example.of(transformer.transferToEntity(b)));
        return optional.map(transformer::transferToBean);
    }

    @Override
    public PageInfo getPageInfo(int count) {
        Page<?> page = baseDAO.findAll(PageRequest.of(0, count));
        return PageInfo.builder()
                .totalPage(page.getTotalPages())
                .totalDataCount(page.getTotalElements())
                .countPerPage(count)
                .build();
    }
}
