package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.History;

import java.util.List;

@Repository
public interface HistoryDAO extends BaseDAO<History,Integer>{
    List<History> findByUserId(Integer userId);
}
