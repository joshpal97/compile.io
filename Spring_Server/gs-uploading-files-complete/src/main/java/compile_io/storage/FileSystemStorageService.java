package compile_io.storage;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileSystemStorageService implements StorageService {

    private Path rootLocation;
    public StorageProperties properties;
    
  //Windows
    private Path studentDir = Paths.get("upload-dir\\student-files");
	private Path professorDir = Paths.get("upload-dir\\professor-files");
	//Ubuntu
//	private Path studentDir = Paths.get("upload-dir/student-files");
//	private Path professorDir = Paths.get("upload-dir/professor-files");

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
    	this.properties = properties;
        this.rootLocation = Paths.get(properties.getLocation());
    }

	@Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    
    public void storeAddPath(MultipartFile file, String addedFolderPathName) {
    	Path dir;
    	if(addedFolderPathName == "student") {
    		dir = this.studentDir;
    	}
    	else if(addedFolderPathName == "professor") {
    		dir = this.professorDir;
    	}
    	else {
    		dir = this.rootLocation;
    	}
    	
    	 String filename = StringUtils.cleanPath(file.getOriginalFilename());
         try {
             if (file.isEmpty()) {
                 throw new StorageException("Failed to store empty file " + filename);
             }
             if (filename.contains("..")) {
                 // This is a security check
                 throw new StorageException(
                         "Cannot store file with relative path outside current directory "
                                 + filename);
             }
             try (InputStream inputStream = file.getInputStream()) {
                 Files.copy(inputStream, dir.resolve(filename),
                     StandardCopyOption.REPLACE_EXISTING);
             }
         }
         catch (IOException e) {
             throw new StorageException("Failed to store file " + filename, e);
         }
    }
    
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    
    public void cleanDirectory() {
    	FileSystemUtils.deleteRecursively(this.studentDir.toFile());
    	FileSystemUtils.deleteRecursively(this.professorDir.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            Files.createDirectories(studentDir);
            Files.createDirectories(professorDir);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
