package tw.edu.ntub.imd.plearnet.dto.file.uploader;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.plearnet.dto.file.directory.Directory;
import tw.edu.ntub.imd.plearnet.exception.file.EmptyFileException;
import tw.edu.ntub.imd.plearnet.exception.file.FileException;
import tw.edu.ntub.imd.plearnet.exception.file.FileUnknownException;
import tw.edu.ntub.imd.plearnet.exception.file.NotHavePathException;
import tw.edu.ntub.imd.plearnet.util.file.FileUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultipartFileUploader {
    private final Directory baseDirectory;
    private final String baseURL;

    public MultipartFileUploader(@NonNull Directory baseDirectory, @NonNull String baseURL) {
        this.baseDirectory = baseDirectory;
        this.baseURL = baseURL;
    }

    @NonNull
    public UploadResult upload(@NonNull MultipartFile multipartFile, String... subDirectoryName) throws FileException {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException(multipartFile.getName());
        } else if (baseDirectory.getAbsolutePath() == null) {
            throw new NotHavePathException();
        }
        Directory uploadTarget = baseDirectory;
        for (String name : subDirectoryName) {
            uploadTarget = uploadTarget.addDirectory(name);
        }
        if (uploadTarget.isNotExist()) {
            uploadTarget.create();
        }
        try {
            String fullRandomFileName = getFullRandomFileName(multipartFile);
            Path absolutePath = uploadTarget.getAbsolutePath();
            Path storePath = absolutePath.resolve(fullRandomFileName);
            multipartFile.transferTo(storePath);
            return UploadResult.builder()
                    .filePath(storePath)
                    .url(String.format(
                            "%s/%s/%s",
                            baseURL,
                            Arrays.stream(subDirectoryName)
                                    .map(name -> URLEncoder.encode(name, StandardCharsets.UTF_8))
                                    .collect(Collectors.joining("/")),
                            fullRandomFileName
                    ))
                    .build();
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    private String getFullRandomFileName(MultipartFile multipartFile) {
        String originalFilename = Objects.requireNonNull(multipartFile.getOriginalFilename());
        int firstDotIndex = originalFilename.indexOf(".");
        return FileUtils.getRandomFileName() + originalFilename.substring(firstDotIndex);
    }

    @NonNull
    public List<UploadResult> upload(@NonNull MultipartFile[] fileArray, String... subDirectoryName) throws FileException {
        return upload(List.of(fileArray), subDirectoryName);
    }

    @NonNull
    public List<UploadResult> upload(@NonNull List<MultipartFile> fileArray, String... subDirectoryName) throws FileException {
        return fileArray.parallelStream().map(file -> upload(file, subDirectoryName)).collect(Collectors.toList());
    }
}
