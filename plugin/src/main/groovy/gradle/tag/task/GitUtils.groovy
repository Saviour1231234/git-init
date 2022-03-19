package gradle.tag.task

import org.gradle.api.GradleScriptException

class GitUtils {

    public static def getGitTagsResult = ("git tag -l").execute().text

    public static def createTag(String version) {
        def result = ("git tag -a $version -m \"Created\"").execute().text

        if (!result.isEmpty()) {
            throw new GradleScriptException("Tag was not created", null)
        }

    }
    public static def getGitCommitHash = ("git rev-parse HEAD").execute().text

    public static def getGitTagName(String hash) {
        return ("git tag --points-at $hash").execute().text
    }

    public static def getGitStatusResult() {
        return ("git status").execute().text
    }

    public static def getCurrentBranch() {
        return ("git branch --show-current").execute().text
    }

    public static def getGitVersionResult() {
        return ("git --version").execute().text
    }

    public static def getGitInit() {
        return ("git init").execute().text
    }

}
