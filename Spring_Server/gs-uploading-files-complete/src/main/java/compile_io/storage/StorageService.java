package compile_io.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
	
    void init();

    void store(MultipartFile file);
    
    void storeAddPath(MultipartFile file, String addedFolderPathName);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
    
    void cleanDirectory();

}
