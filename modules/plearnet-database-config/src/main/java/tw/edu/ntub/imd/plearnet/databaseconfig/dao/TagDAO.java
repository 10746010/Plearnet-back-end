package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagDAO extends BaseDAO<Tag, Integer>{
//    List<Tag> findByTagType(Integer tagType);

    Optional<Tag> findById(Integer id);
}
