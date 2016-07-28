package me.gking2224.wagpi.plugin

import org.gradle.api.Project

class AngularConfigurer {

    Project project
    public AngularConfigurer() {
        // TODO Auto-generated constructor stub
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
        project.task("ngBuild") << {
            
        }
        project.task("ngServe") << {
            
        }
    }

}
