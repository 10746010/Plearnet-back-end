package tw.edu.ntub.imd.plearnet.service;

import tw.edu.ntub.imd.plearnet.bean.TagBean;

import java.util.List;

public interface TagService extends BaseService<TagBean, Integer>{
    List<TagBean> searchAll(Integer tagType);
}
