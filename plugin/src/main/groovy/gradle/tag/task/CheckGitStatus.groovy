package gradle.tag.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleScriptException
import org.gradle.api.tasks.TaskAction

class CheckGitStatus extends DefaultTask {

    @TaskAction
    def checkStatus() {

        def versionResult = GitUtils.gitVersionResult
        if (!versionResult.contains("git")) {
            throw new GradleScriptException("Git isn't installed yet. Please install Git.", null)
        }
        def statusResult = GitUtils.gitStatusResult
//        if (statusResult.contains("new file") || statusResult.contains("modified")) {
//            throw new GradleScriptException("Uncommitted changes was found", null)
//        }
//        else
        if(statusResult.isEmpty()){
            GitUtils.gitInit;
            throw new GradleScriptException("Project was not initialized by Git", null)
        }
    }
}