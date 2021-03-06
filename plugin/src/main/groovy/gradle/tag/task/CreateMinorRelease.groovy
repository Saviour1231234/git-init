package gradle.tag.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleScriptException
import org.gradle.api.tasks.TaskAction

class CreateMinorRelease extends DefaultTask{

    @TaskAction
    def createMinorRelease() {
        def tags = GitUtils.getGitTagsResult

        println(tags)

        def tagsArray = tags.split("\n")
        tagsArray.toList().forEach {println it}

        def currentVersion = tagsArray[tagsArray.size() -1]
        println("current version = $currentVersion")

        if (currentVersion.isEmpty()){
            println("current version1 = $currentVersion")
            currentVersion = "v0.0"
        }

        def currentVersionSplitted = currentVersion.split('\\.')
        println("splitted current version = $currentVersionSplitted")

        def currentMinor = Integer.parseInt(currentVersionSplitted[1]) + 1
        def newVersion = String.join(".", currentVersionSplitted[0], currentMinor as String)

        println("new tag version $newVersion")
        /////////
        /////////
        def gitHash = GitUtils.getGitCommitHash
        def checkCommitHash = GitUtils.getGitTagName(gitHash);

        if (checkCommitHash.isEmpty()) {
            GitUtils.createTag(newVersion)
        }else {
            throw new GradleScriptException("That commit have an a tag already", null)
        }

        ("git push origin $newVersion").execute()
    }


}
