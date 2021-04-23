package tw.edu.ntub.imd.plearnet.dto.file;

import tw.edu.ntub.imd.plearnet.dto.file.directory.Directory;

import java.nio.file.StandardCopyOption;

public interface Copyable {
    void copyTo(Directory newDirectory, StandardCopyOption... options);
}
