package tw.edu.ntub.imd.plearnet.dto.file.directory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;

@Component
public class LogDirectory extends DirectoryImpl {

    @Autowired
    public LogDirectory(@Value("${log.path}${file.separator}plearnet") String logPath) {
        super(Paths.get(logPath));
    }

    public Directory getTraceLogDirectory() {
        return getLogDirectory("trace");
    }

    public Directory getLogDirectory(String level) {
        Optional<Directory> optionalLogDirectory = getDirectory(level);
        return optionalLogDirectory.orElseGet(() -> addDirectory(level));
    }

    public Directory getInfoLogDirectory() {
        return getLogDirectory("info");
    }

    public Directory getDebugLogDirectory() {
        return getLogDirectory("debug");
    }

    public Directory getErrorLogDirectory() {
        return getLogDirectory("error");
    }
}
