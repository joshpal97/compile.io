package compile_io.docker;

import java.io.*;

/**
 * This class builds a Dockerfile that the superclass uses to create a Docker image.
 */
public class PythonCompiler extends AbstractCompiler {

    /**
     * Constructor that builds a docker image with the given name
     * @param File file File uploaded by the student
     */
    public PythonCompiler(File file) {
        super(file);
    }

    /**
     * Creates the Dockerfile used for creating the docker container
     * IMPORTANT: Dockerfile MUST be in the directory of the source files it intends to run
     * @return void
     * @throws FileNotFoundException e If the the given file does not exist or cannot be found
     * @throws IOException e If the java IO encounters an error
     */
    public void createDockerfile() { 
        // this can be abstracted further
        StringBuilder dockerfileData = new StringBuilder();
        dockerfileData.append("FROM python:latest\n");
        dockerfileData.append("WORKDIR " + super.getFileDirectory() + "\n");
        dockerfileData.append("ADD " + super.getFileName() + " " + super.getFileName() + "\n");
        dockerfileData.append("EXPOSE 8000\n");
        dockerfileData.append("CMD python " + super.getFileName() + "\n");
        FileOutputStream fos = null;
        File file;

        // this try catch block is repeated in every compiler, abstract out to superclass
        try {
            System.out.println("Making Dockerfile in directory: " + super.getFileDirectory());
            file = new File(super.getFileDirectory() + "/Dockerfile");
            fos = new FileOutputStream(file);
      
            /* This logic will check whether the file
             * exists or not. If the file is not found
             * at the specified location it would create
             * a new file*/
            if (!file.exists()) {
               file.createNewFile();
            }

            fos.write(dockerfileData.toString().getBytes());
            fos.flush();
            System.out.println("Dockerfile successfully written: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error in closing the Stream");
            }
        }
    }

}