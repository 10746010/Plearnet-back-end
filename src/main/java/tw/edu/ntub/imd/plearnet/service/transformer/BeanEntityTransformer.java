package tw.edu.ntub.imd.plearnet.service.transformer;

import org.springframework.lang.NonNull;

public interface BeanEntityTransformer<B, E> {
    @NonNull
    E transferToEntity(@NonNull B b);

    @NonNull
    B transferToBean(@NonNull E e);
}
