package gradle.tag.task


import org.gradle.api.DefaultTask
import org.gradle.api.GradleScriptException
import org.gradle.api.tasks.TaskAction

class CreateMajorRelease extends DefaultTask{

    @TaskAction
    def createMajorRelease() {

        def tags = GitUtils.getGitTagsResult
        println(tags)

        def tagsArray = tags.split("\n")
        tagsArray.toList().forEach {println it}

        def currentVersion = tagsArray[tagsArray.size() -1]
        println("current version = $currentVersion")

        def currentVersionSplitted = currentVersion.split('\\.')

        def newMajorVersion = Integer.parseInt(currentVersionSplitted[0].replaceAll("[^\\d.]", "")) + 1

        def newVersion = String.join(".","v".concat(newMajorVersion as String) , "0")

        println("new tag version $newVersion")
        /////////
        /////////
        def gitHash = GitUtils.getGitCommitHash

        def checkCommitHash = GitUtils.getGitTagName(gitHash)

        if (checkCommitHash.isEmpty()) {
            GitUtils.createTag(newVersion)
        }else {
            throw new GradleScriptException("That commit have an a tag already", null)
        }
        /////////
        /////////
        ("git push origin $newVersion").execute()
    }

}
