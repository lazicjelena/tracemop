package edu.cornell;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "run", requiresDirectInvocation = true, requiresDependencyResolution = ResolutionScope.TEST)
@Execute(phase = LifecyclePhase.PROCESS_TEST_CLASSES, lifecycle = "run")
public class TraceMOPMojo extends AbstractMojo {
    @Parameter(property = "collectTraces", defaultValue = "true")
    private boolean collectTraces;

    @Parameter(property = "outputDirectory", defaultValue = "")
    private String outputDirectory;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Component
    private BuildPluginManager manager;

    public void execute() throws MojoExecutionException {
        String output = Utils.getOutputDirectory(project, getLog(), outputDirectory);
        Utils.setEnv(getLog(), "RVMLOGGINGLEVEL", "UNIQUE");

        if (collectTraces) {
            Utils.setEnv(getLog(), "TRACEDB_PATH", output + File.separator + "all-traces");
            Utils.setEnv(getLog(), "TRACEDB_CONFIG_PATH", output + File.separator + ".trace-db.config");
            Utils.setEnv(getLog(), "TRACEDB_RANDOM", "1");
            Utils.setEnv(getLog(), "COLLECT_MONITORS", "1");
            Utils.setEnv(getLog(), "COLLECT_TRACES", "1");
        }

        String repoDirectory = session.getLocalRepository().getBasedir();
        String agentName = collectTraces ? "track-no-stats-agent.jar" : "no-track-no-stats-agent.jar";
        Utils.setupRepo(getLog(), repoDirectory, agentName);

        getLog().info("Running surefire:test using agent: " + agentName);

        executeMojo(
                plugin(
                    groupId("org.apache.maven.plugins"),
                    artifactId("maven-surefire-plugin"),
                    version("3.1.2")
                ),
                goal("test"),
                configuration(element(name("argLine"), "-javaagent:${settings.localRepository}/javamop-agent/javamop-agent/1.0/" + agentName + " -Xmx4g -XX:-UseGCOverheadLimit")),
                executionEnvironment(
                    project,
                    session,
                    manager
                )
        );

        if (collectTraces) {
            postProcess(output);
        }
    }

    private void postProcess(String output) {
        TracesProcessor processor = new TracesProcessor(getLog(), output);
        processor.process();
    }
}
