package tw.edu.ntub.imd.plearnet.dto.file.directory;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import tw.edu.ntub.imd.plearnet.dto.file.File;
import tw.edu.ntub.imd.plearnet.dto.file.FileFactory;
import tw.edu.ntub.imd.plearnet.exception.file.FileUnknownException;
import tw.edu.ntub.imd.plearnet.util.file.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryImpl implements Directory {
    private final String name;
    private final Path path;
    private final List<Directory> directoryList = new ArrayList<>();
    private final List<File> fileList = new ArrayList<>();

    public DirectoryImpl(Path path) {
        Path fileName = path.getFileName();
        this.name = fileName.toString();
        this.path = path;
        if (isExist()) {
            reloadContent();
        }
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public Path getAbsolutePath() {
        return path;
    }

    @Override
    public void reloadContent() {
        try {
            directoryList.clear();
            fileList.clear();
            Files.list(path).forEach(this::addDirectoryOfFile);
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    @Override
    public Directory addDirectory(String newDirectoryName) {
        Directory newDirectory = new DirectoryImpl(path.resolve(newDirectoryName));
        newDirectory.create();
        directoryList.add(newDirectory);
        return newDirectory;
    }

    @Override
    public List<Directory> getSubDirectoryList() {
        return Collections.unmodifiableList(directoryList);
    }

    private void addDirectoryOfFile(Path path) {
        if (FileUtils.isDirectory(path)) {
            directoryList.add(DirectoryFactory.create(path));
        } else {
            fileList.add(FileFactory.create(path));
        }
    }

    @Override
    public int getSubDirectoryCount() {
        return directoryList.size();
    }

    @Override
    public List<File> getSubFileList() {
        return Collections.unmodifiableList(fileList);
    }

    @Override
    public int getSubFileCount() {
        return fileList.size();
    }
}
