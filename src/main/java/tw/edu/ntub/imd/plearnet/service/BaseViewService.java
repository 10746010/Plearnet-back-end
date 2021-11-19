package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.plearnet.dto.PageInfo;

import java.util.List;
import java.util.Optional;

public interface BaseViewService<B, ID> {
    Optional<B> getById(ID id);

    List<B> searchAll();

    List<B> searchAll(Pager pager);

    List<B> searchByBean(B b);

    Optional<B> getByBean(B b);

    PageInfo getPageInfo(int count);
}
