package me.gking2224.wagpi.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class WebAppBuildGradlePlugin implements Plugin<Project> {
    
    def project
    
	void apply(Project project) {
		project.extensions.create("wagpi", WebAppBuildPluginExtension.class, project)
        
        def dir = project.file("db")
        
        
        project.task("prepareWebAppEnvConfigFiles", type:me.gking2224.buildtools.tasks.Copy) {
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/main/webappEnvResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "inplaceWebapp")}
                pattern = "**/.*\\..*"
            }
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/integration/webappEnvResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "inplaceWebapp")}
                pattern = "**/.*\\..*"
            }
        }
        project.tasks.processResources.dependsOn "prepareWebAppEnvConfigFiles"
        
        project.task("prepareEnvConfigFiles", type:me.gking2224.buildtools.tasks.Copy) {
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/main/envResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "classes")}
                pattern = "**/.*\\..*"
            }
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/integration/envResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "classes")}
                pattern = "**/.*\\..*"
            }
        }
        project.tasks.processResources.dependsOn "prepareEnvConfigFiles"
	}
}
