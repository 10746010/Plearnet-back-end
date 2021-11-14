package tw.edu.ntub.imd.plearnet.service;

public interface BaseService<B, ID> extends BaseViewService<B, ID> {
    @SuppressWarnings("UnusedReturnValue")
    B save(B b);

    void update(ID id, B b);

    void update(Integer id, B b);

    void delete(ID id);


}
