
class Controller(private val fileInterface: FileInterface) {

    private val index: Index = Index(fileInterface)
    private val objectsDatabase: ObjectsDatabase = ObjectsDatabase(fileInterface)
    private val workingDirectory: WorkingDirectory = WorkingDirectory(fileInterface)

    enum class Command {
        INIT,
        ADD,
        COMMIT
    }

    fun callCommand(command: Command, path: String) {
        when (command) {
            Command.INIT -> init()
            Command.ADD -> add(path)
            Command.COMMIT -> commit(path)
        }
    }

    private fun init() {
        fileInterface.makeDirectory(WriteDirectoryCommand(Config.GIT_DIR_PATH))
        fileInterface.makeDirectory(WriteDirectoryCommand(Config.OBJECTS_DIR_PATH))

        fileInterface.writeFile(WriteFileCommand(Config.INDEX_PATH, ""))

        fileInterface.writeFile(WriteFileCommand(Config.HEAD_PATH, Config.MASTER_PATH))
        fileInterface.makeDirectory(WriteDirectoryCommand(Config.REFS_PATH))
        fileInterface.makeDirectory(WriteDirectoryCommand(Config.HEADS_PATH))
        fileInterface.writeFile(WriteFileCommand(Config.MASTER_PATH, ""))
    }

    private fun add(workingDirectoryPath: String) {
        val fileContents = workingDirectory.getFileContents(workingDirectoryPath)
        val blobHash = objectsDatabase.createAndWriteBlob(fileContents)
        index.addBlobToIndex(workingDirectoryPath, blobHash)
    }

    private fun commit(message: String) {
        // for now we commit everything in the index
        val treeHash = writeTree()
        val commitHash = commitTree(treeHash, message)
        fileInterface.writeFile(WriteFileCommand(Config.MASTER_PATH, commitHash))
    }

    private fun writeTree(): String {
        val entriesInIndex = index.getObjectsInIndex()
        return objectsDatabase.createAndWriteTree(entriesInIndex)
    }

    private fun commitTree(treeHash: String, message: String): String {
        val commitHash = objectsDatabase.createAndWriteCommit(treeHash, message)
        index.clear()
        return commitHash
    }
}
