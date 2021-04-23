package tw.edu.ntub.imd.plearnet.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StringToMultipartFileConverter implements Converter<String, MultipartFile> {

    @Override
    public MultipartFile convert(String source) {
        return null;
    }
}
