package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Tag;

import java.util.List;

@Repository
public interface TagDAO extends BaseDAO<Tag, String>{
    List<Tag> findByTagType(Integer tagType);
}
