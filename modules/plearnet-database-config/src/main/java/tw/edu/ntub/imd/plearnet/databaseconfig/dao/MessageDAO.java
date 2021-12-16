package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Topic;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageDAO extends BaseDAO<Message, Integer>{
    List<Message> findByTopicId(Integer topicID);

    Optional<Message> findById(Integer id);
}
