package me.gking2224.wagpi.plugin

import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class AngularConfigurer {
    Logger logger = LoggerFactory.getLogger(this.class)

    Project project
    public AngularConfigurer() {
    }
    
    def configureProject(Project p) {
        this.project = p
        p.ext.angularProject = false
        File ngDir = new File(p.projectDir, "src/main/ng")
        project.ext.ngSrcDir = ngDir
        if (ngDir.exists()) {
            p.ext.angularProject = true
            configureAngularTasks()
        }
    }
    def configureAngularTasks() {
        project.task("ngBuild", type:Exec) {
            executable "ng"
            args("build")
            args("-e", "${project.env}")
            args("-o", "${project.envProps.webapp.ng.buildDir}")
        }
        if (project.envProps.webapp.ng.deleteIndexHtml) {
            project.tasks.ngBuild.doLast {
                logger.debug("deleting index.html")
                new File(project.envProps.webapp.ng.buildDir, "index.html").delete()
            }
        }
        if (project.env=="prod") {
            project.tasks.ngBuild.doLast {
                logger.debug("Copying d3 files to ${project.envProps.webapp.ng.buildDir}")
                ant.copy(todir:"${project.envProps.webapp.ng.buildDir}/vendor/d3/") {
                    fileset(dir:"node_modules/d3/") {
                        include(name:"**/*.js")
                        include(name:"**/*.css")
                    }
                }
                logger.debug("Copying codemirror files to ${project.envProps.webapp.ng.buildDir}")
                ant.copy(todir:"${project.envProps.webapp.ng.buildDir}/vendor/codemirror/") {
                    fileset(dir:"node_modules/codemirror/") {
                        include(name:"**/*.js")
                        include(name:"**/*.css")
                    }
                }
                logger.debug("Copying dateformat files to ${project.envProps.webapp.ng.buildDir}")
                ant.copy(todir:"${project.envProps.webapp.ng.buildDir}/vendor/dateformat/") {
                    fileset(dir:"node_modules/dateformat/") {
                        include(name:"**/*.js")
                        include(name:"**/*.css")
                    }
                }
            }
        }
        project.task("ngServe", type:Exec) {
            executable "ng"
            args("serve")
            args("-e", "${project.env}")
            args("-op", "${project.envProps.webapp.ng.buildDir}")
            args("-p", "${project.envProps.webapp.ng.port}")
        }
    }

}
