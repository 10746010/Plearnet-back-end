package tw.edu.ntub.imd.plearnet.dto.file.uploader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

@Data
@AllArgsConstructor
@Builder
public class UploadResult {
    private Path filePath;
    private String url;
}
