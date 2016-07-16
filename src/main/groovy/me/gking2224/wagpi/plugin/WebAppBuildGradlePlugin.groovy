package me.gking2224.wagpi.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class WebAppBuildGradlePlugin implements Plugin<Project> {
    
    def project
    
	void apply(Project project) {
		project.extensions.create("wagpi", WebAppBuildPluginExtension.class, project)
        
        def dir = project.file("db")
        
        project.task("prepareEnvConfigFiles", type:me.gking2224.buildtools.tasks.Copy) {
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/main/envResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "resources/main")}
                pattern = "**/.*\\..*"
            }
        }
        project.tasks.prepareEnvConfigFiles.inputs.property "env", project.env
        project.tasks.prepareEnvConfigFiles.inputs.dir "src/main/envResources"
        project.tasks.prepareEnvConfigFiles.outputs.dir "build/resources/main"
        project.tasks.processResources.dependsOn "prepareEnvConfigFiles"
        
        project.task("prepareWebAppEnvConfigFiles", type:me.gking2224.buildtools.tasks.Copy) {
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/main/webappEnvResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "inplaceWebapp")}
                pattern = "**/.*\\..*"
            }
		}
        project.tasks.prepareWebAppEnvConfigFiles.inputs.property "env", project.env
        project.tasks.prepareWebAppEnvConfigFiles.inputs.dir "src/main/webappEnvResources"
        project.tasks.prepareWebAppEnvConfigFiles.outputs.dir "build/inplaceWebapp"
        project.tasks.processResources.dependsOn "prepareWebAppEnvConfigFiles"
        
        project.task("prepareIntegrationEnvConfigFiles", type:me.gking2224.buildtools.tasks.Copy) {
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/integration/envResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "resources/integration")}
                pattern = "**/.*\\..*"
            }
        }
        project.tasks.prepareIntegrationEnvConfigFiles.inputs.property "env", project.env
        project.tasks.prepareIntegrationEnvConfigFiles.inputs.dir "src/integration/envResources"
        project.tasks.prepareIntegrationEnvConfigFiles.outputs.dir "build/resources/integration"
        project.tasks.processIntegrationResources.dependsOn "prepareIntegrationEnvConfigFiles"
        
        project.task("prepareIntegrationWebAppEnvConfigFiles", type:me.gking2224.buildtools.tasks.Copy) {
            group {
                fromDir = {project.fileNameFromParts(project.rootDir, "src/integration/webappEnvResources")}
                toDir = {project.fileNameFromParts(project.buildDir, "inplaceWebapp")}
                pattern = "**/.*\\..*"
            }
        }
        project.tasks.prepareIntegrationWebAppEnvConfigFiles.inputs.property "env", project.env
        project.tasks.prepareIntegrationWebAppEnvConfigFiles.inputs.dir "src/integration/webappEnvResources"
        project.tasks.prepareIntegrationWebAppEnvConfigFiles.outputs.dir "build/inplaceWebapp"
        project.tasks.processIntegrationResources.dependsOn "prepareIntegrationWebAppEnvConfigFiles"
	}
}
