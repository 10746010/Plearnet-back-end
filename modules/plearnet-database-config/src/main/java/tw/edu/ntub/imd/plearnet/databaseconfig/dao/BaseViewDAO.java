package tw.edu.ntub.imd.plearnet.databaseconfig.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseViewDAO<E, ID extends Serializable> extends Repository<E, ID>, QueryByExampleExecutor<E> {
    @NonNull
    Optional<E> findById(@NonNull ID id);

    @NonNull
    List<E> findAll();

    @NonNull
    List<E> findAllById(@NonNull Iterable<ID> ids);

    @NonNull
    List<E> findAll(@NonNull Sort sort);

    /**
     * @param pageable 分頁、排序
     * @return 查詢到的清單
     * @see org.springframework.data.domain.PageRequest
     */
    @NonNull
    Page<E> findAll(@NonNull Pageable pageable);

    @Override
    @NonNull
    <S extends E> List<S> findAll(@NonNull Example<S> example);

    @Override
    @NonNull
    <S extends E> List<S> findAll(@NonNull Example<S> example, @NonNull Sort sort);

    @Override
    @NonNull
    <S extends E> Page<S> findAll(@NonNull Example<S> example, @NonNull Pageable pageable);

    boolean existsById(ID id);

    long count();
}
