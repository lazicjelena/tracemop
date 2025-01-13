package edu.cornell;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    /*
     * Get output directory from parameter, or use default "target/tracemop"
     * then, create .trace-db.config inside this output directory
     */
    static String getOutputDirectory(MavenProject project, Log log, String outputDirectory) {
        String output = outputDirectory;
        if (output == null || output.isEmpty()) {
            output = project.getBuild().getDirectory() + File.separator + "tracemop";
        }

        try {
            Files.createDirectories(Paths.get(output));

            PrintWriter writer = new PrintWriter(output + File.separator + ".trace-db.config", "UTF-8");
            writer.println("db=memory");
            writer.println("dumpDB=false");
            writer.close();

            log.info("Output directory: " + output);
        } catch (Exception e) {
            log.error(e);
        }
        return output;
    }

    /*
     * Copy TraceMOP agent to m2 repository
     */
    static void setupRepo(Log log, String repoDirectory, String filename)  {
        File jarFile = new File(repoDirectory + File.separator + "javamop-agent" + File.separator +  "javamop-agent" + File.separator +  "1.0" + File.separator + filename);
        if (!jarFile.exists()) {
            log.info("Installing TraceMOP agent to " + jarFile.getAbsolutePath());
            try {
                URI resource = Utils.class.getResource("").toURI();
                FileSystem fs = FileSystems.newFileSystem(resource, new HashMap<String, String>());
                Path dir = Paths.get(jarFile.getParentFile().getAbsolutePath());

                Files.createDirectories(dir);
                Files.copy(fs.getPath(filename), jarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /*
     * Change environment variable
     * Source: https://stackoverflow.com/a/40682052
     */
    public static void setEnv(Log log, String key, String value) {
        try {
            Map<String, String> env = System.getenv();
            Class<?> cl = env.getClass();
            Field field = cl.getDeclaredField("m");
            field.setAccessible(true);
            Map<String, String> writableEnv = (Map<String, String>) field.get(env);
            writableEnv.put(key, value);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
