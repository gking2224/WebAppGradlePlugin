package me.gking2224.wagpi.plugin

import org.gradle.api.Project;
import org.slf4j.LoggerFactory


class WebAppBuildPluginExtension {
    
    def logger = LoggerFactory.getLogger(WebAppBuildPluginExtension.class)
    
    def project
    
    public WebAppBuildPluginExtension(def project) {
        this.project = project
    }
}
