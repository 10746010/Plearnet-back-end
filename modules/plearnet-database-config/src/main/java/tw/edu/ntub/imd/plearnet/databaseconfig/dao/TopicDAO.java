package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Topic;

import java.util.List;

@Repository
public interface TopicDAO extends BaseDAO<Topic, String>{
    List<Topic> findByTagId(Integer tag);
}