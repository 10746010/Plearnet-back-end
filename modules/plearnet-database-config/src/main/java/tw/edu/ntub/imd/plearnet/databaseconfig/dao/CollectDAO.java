package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Collect;

import java.util.List;

@Repository
public interface CollectDAO extends BaseDAO<Collect,Integer>{
    List<Collect> findByUserId(Integer userId);
}
