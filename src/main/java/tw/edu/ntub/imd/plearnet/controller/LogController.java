package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.imd.plearnet.dto.file.File;
import tw.edu.ntub.imd.plearnet.dto.file.TextFile;
import tw.edu.ntub.imd.plearnet.dto.file.directory.Directory;
import tw.edu.ntub.imd.plearnet.dto.file.directory.LogDirectory;
import tw.edu.ntub.imd.plearnet.exception.file.FileNotExistException;
import tw.edu.ntub.imd.plearnet.exception.file.FileTypeMismatchException;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/administrator/jsoqgvaofmep1if048vndktirkei/log")
public class LogController {
    private final LogDirectory logDirectory;

    @GetMapping(path = "/{level}")
    public ModelAndView infoLogDirectoryListPage(@PathVariable(name = "level") String level) {
        return getLogDirectoryListPage(logDirectory.getLogDirectory(level));
    }

    private ModelAndView getLogDirectoryListPage(Directory logDirectory) {
        logDirectory.reloadContent();
        List<String> directoryNameList = logDirectory.getSubDirectoryList()
                .parallelStream()
                .map(Directory::getName)
                .collect(Collectors.toList());
        Collections.reverse(directoryNameList);
        ModelAndView logListPage = new ModelAndView("/log/log_list");
        logListPage.addObject("subDirectoryNameList", directoryNameList);
        return logListPage;
    }

    @GetMapping(path = "/{level}/{directoryName}")
    public ModelAndView infoLogFileListPage(
            @PathVariable(name = "level") String level,
            @PathVariable(name = "directoryName") String directoryName
    ) {
        return getLogFileListPage(logDirectory.getLogDirectory(level), directoryName);
    }

    private ModelAndView getLogFileListPage(Directory logDirectory, String subDirectoryName) {
        Directory logSubDirectory = getLogSubDirectory(logDirectory, subDirectoryName);
        List<String> fileNameList = logSubDirectory.getSubFileList()
                .parallelStream()
                .map(File::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ModelAndView logFileListPage = new ModelAndView("/log/log_file_list");
        logFileListPage.addObject("subFileNameList", fileNameList);
        return logFileListPage;
    }

    private Directory getLogSubDirectory(Directory logDirectory, String directoryName) {
        Optional<Directory> optionalTargetLogSubDirectory = logDirectory.getDirectory(directoryName);
        return optionalTargetLogSubDirectory.orElseThrow(() -> {
            Path absolutePath = Objects.requireNonNull(logDirectory.getAbsolutePath());
            return new FileNotExistException(absolutePath.resolve(directoryName));
        });
    }

    @GetMapping(path = "/{level}/{directoryName}/{fileName}")
    public ModelAndView infoLogFileContentPage(
            @PathVariable(name = "level") String level,
            @PathVariable(name = "directoryName") String directoryName,
            @PathVariable(name = "fileName") String fileName
    ) {
        return getLogContentPage(getLogSubDirectory(logDirectory.getLogDirectory(level), directoryName), fileName);
    }

    private ModelAndView getLogContentPage(Directory logFileDirectory, String logFileName) {
        ModelAndView logContentPage = new ModelAndView("/log/log_file_content");
        Optional<File> optionalFile = logFileDirectory.getFile(logFileName);
        File logFile = optionalFile.orElseThrow(() -> {
            Path absolutePath = Objects.requireNonNull(logFileDirectory.getAbsolutePath());
            return new FileNotExistException(absolutePath.resolve("plearnet-debug.log"));
        });
        if (logFile instanceof TextFile) {
            TextFile textFile = (TextFile) logFile;
            logContentPage.addObject("fileContent",
                    textFile.readLinesWithStream().collect(Collectors.joining("\n")));
            return logContentPage;
        } else {
            throw new FileTypeMismatchException(logFile.getPath(), TextFile.class);
        }
    }

    @GetMapping(path = "/{level}/today")
    public ModelAndView todayInfoLog(@PathVariable(name = "level") String level) {
        return getLogContentPage(logDirectory, "plearnet-" + level + ".log");
    }
}
