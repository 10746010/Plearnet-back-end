package tw.edu.ntub.imd.plearnet.util.file;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.imd.plearnet.exception.file.FileExtensionNotFoundException;
import tw.edu.ntub.imd.plearnet.exception.file.InvalidCharsetException;
import tw.edu.ntub.imd.plearnet.exception.file.InvalidOptionException;
import tw.edu.ntub.imd.plearnet.exception.file.UnauthorizedException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.UUID;

@UtilityClass
@SuppressWarnings("unused")
public class FileUtils {
    @NonNull
    public String getRandomFileName() {
        return UUID.randomUUID().toString();
    }

    @NonNull
    public String replaceFileName(@NonNull String fullFileName) {
        return replaceFileName(fullFileName, null);
    }

    @NonNull
    public String replaceFileName(@NonNull String fullFileName, @Nullable String newFileName) {
        if (newFileName == null) {
            newFileName = getRandomFileName();
        }
        try {
            String fileExtension = getFileExtension(fullFileName);
            return newFileName + "." + fileExtension;
        } catch (FileExtensionNotFoundException e) {
            return newFileName;
        }
    }

    public String getFileName(String fullFileName) {
        int lastDotIndex = fullFileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fullFileName.substring(0, lastDotIndex);
        } else {
            return fullFileName;
        }
    }

    @NonNull
    public String getFileExtension(@NonNull String fullFileName) throws FileExtensionNotFoundException {
        int lastDotIndex = fullFileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new FileExtensionNotFoundException(fullFileName);
        } else {
            return fullFileName.substring(lastDotIndex + 1);
        }
    }

    @NonNull
    public InputStream openInputStream(@NonNull String filePath, OpenOption... openOptionArray) {
        try {
            return Files.newInputStream(getFilePath(filePath), openOptionArray);
        } catch (IllegalArgumentException e) {
            throw new InvalidOptionException(e);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    @NonNull
    public Path getFilePath(@NonNull String filePath) {
        try {
            return Paths.get(filePath);
        } catch (InvalidPathException e) {
            throw new tw.edu.ntub.imd.plearnet.exception.file.InvalidPathException(e, filePath);
        }
    }

    @NonNull
    public Path getDirectoryPath(@NonNull String firstDirectoryPath, String... subDirectoryPath) {
        try {
            return Paths.get(firstDirectoryPath, subDirectoryPath);
        } catch (InvalidPathException e) {
            throw new tw.edu.ntub.imd.plearnet.exception.file.InvalidPathException(e, firstDirectoryPath, subDirectoryPath);
        }
    }

    @NonNull
    public OutputStream openOutputStreamUseOverwriteMode(@NonNull String filePath) {
        return openOutputStream(filePath);
    }

    @NonNull
    public OutputStream openOutputStream(@NonNull String filePath, OpenOption... openOptionArray) {
        try {
            return Files.newOutputStream(getFilePath(filePath), openOptionArray);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    @NonNull
    public OutputStream openOutputStreamUseAppendMode(@NonNull String filePath) {
        return openOutputStream(filePath, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    @NonNull
    public BufferedReader openReader(@NonNull String filePath) {
        return openReader(filePath, StandardCharsets.UTF_8);
    }

    @NonNull
    public BufferedReader openReader(@NonNull String filePath, @NonNull Charset charset) {
        try {
            return Files.newBufferedReader(getFilePath(filePath), charset);
        } catch (MalformedInputException e) {
            throw new InvalidCharsetException(charset.name(), e);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    @NonNull
    public BufferedWriter openWriter(@NonNull String filePath, @NonNull Charset charset) {
        try {
            return Files.newBufferedWriter(getFilePath(filePath), charset);
        } catch (IllegalArgumentException e) {
            throw new InvalidOptionException(e);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public void copy(@NonNull InputStream source, @NonNull String targetPath, @NonNull String fileNameExtension) {
        copy(source, targetPath, getRandomFileName(), fileNameExtension);
    }

    public void copy(@NonNull InputStream source, @NonNull String targetPath, @NonNull String fileName, @NonNull String fileNameExtension) {
        copyByFullFileName(source, targetPath, fileName + "." + fileNameExtension);
    }

    public void copyByFullFileName(@NonNull InputStream source, @NonNull String targetPath, @NonNull String fullFileName) {
        Path directoryPath = getDirectoryPath(targetPath);
        Path targetFilePath = directoryPath.resolve(fullFileName);
        copy(source, targetFilePath);
    }

    public void copy(@NonNull InputStream source, @NonNull Path targetFilePath) {
        try {
            Files.copy(source, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (SecurityException e) {
            throw new UnauthorizedException(e);
        } catch (Exception e) {
            throw new UnknownException(e);
        }
    }

    public void copyByRandomFileName(@NonNull InputStream source, @NonNull String targetPath, @NonNull String fullFileName) {
        copyByFullFileName(source, targetPath, replaceFileName(fullFileName));
    }

    public boolean isNotDirectory(@NonNull Path path) {
        return !isDirectory(path);
    }

    public boolean isDirectory(@NonNull Path path) {
        return Files.isDirectory(path);
    }

    public String getFullFileNameFromPath(Path path) {
        Path fileNamePath = path.getFileName();
        return fileNamePath.toString();
    }
}
