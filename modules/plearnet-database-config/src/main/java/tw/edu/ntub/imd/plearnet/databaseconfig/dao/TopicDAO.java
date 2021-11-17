package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Topic;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicDAO extends BaseDAO<Topic, Integer>{
    List<Topic> findByTagId(Integer tag);

    Optional<Topic> findById(Integer id);
}