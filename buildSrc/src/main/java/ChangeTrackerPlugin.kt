import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.AbstractTreeIterator
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import org.gradle.api.Plugin
import org.gradle.api.Project

class ChangeTrackerPlugin : Plugin<Project> {

    override fun apply(currentProject: Project) {
        val rootProject = currentProject.rootProject
        rootProject.gradle.projectsEvaluated {
            val git = Git.open(rootProject.rootDir)
            val repository = git.repository
            val diffs = git.diff()
                .setOldTree(getDevelopTree(repository))
                .call()

            val projectList = rootProject.allprojects.map { it.name }
            val changedModuleList = diffs.mapNotNull { diff ->
                val module = diff.newPath.substringBefore('/')
                module.ifEmpty { if (diff.newPath.firstOrNull() == '/') null else rootProject.name }
            }.filter {
                projectList.contains(it)
            }.toSet()

            val rebuildModuleList = when {
                changedModuleList.contains(rootProject.name) -> rootProject.subprojects.map { it.name }
                else -> changedModuleList
            }

            rebuildModuleList.forEach { moduleName ->
                val module = rootProject.subprojects.first { it.name == moduleName }

                if (module.plugins.findPlugin("java-library") != null) {
                    module.task("testForChanges") {
                        dependsOn("assemble", "test")
                    }
                } else {
                    module.task("testForChanges") {
                        dependsOn("assembleDebug", "testDebugUnitTest", "ktlintCheck")
                    }
                }
            }
        }
    }

    private fun getDevelopTree(repository: Repository): AbstractTreeIterator {
        val walk = RevWalk(repository)
        val commit = walk.parseCommit(repository.resolve("origin/develop"))
        val tree = walk.parseTree(commit.tree.id)

        val treeParser = CanonicalTreeParser()
        val reader = repository.newObjectReader()
        treeParser.reset(reader, tree.id)

        walk.dispose()

        return treeParser
    }
}
