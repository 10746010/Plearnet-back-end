package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;

import java.util.List;

@Repository
public interface MessageDAO extends BaseDAO<Message, String>{
    List<Message> findByTopicId(Integer topicID);
}
