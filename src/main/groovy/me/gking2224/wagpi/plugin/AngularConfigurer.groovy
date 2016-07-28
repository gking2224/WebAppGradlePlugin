package me.gking2224.wagpi.plugin

import org.gradle.api.Project
import org.gradle.api.tasks.Exec


class AngularConfigurer {

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
                println ("deleting index.html")
                new File(project.envProps.webapp.ng.buildDir, "index.html").delete()
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
