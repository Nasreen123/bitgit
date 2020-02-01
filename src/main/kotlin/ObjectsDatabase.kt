class ObjectsDatabase(private val fileInterface: FileInterface) {

    fun createAndWriteBlob(contents: String): String {
        val hash = Hasher.hash(contents)

        writeObject(hash, contents)

        return hash
    }

    fun createAndWriteTree(nodes: List<IndexEntry>): String {
        // for now its more of a list of items than nodes
        val treeContents = createTreeBody(nodes)
        val treeHash = Hasher.hash(treeContents)

        writeObject(treeHash, treeContents)

        return treeHash
    }

    private fun createTreeBody(nodes: List<IndexEntry>): String {
        val contents = StringBuilder("")
        for (node in nodes) {
            contents.append(entryLine(node.path, node.hash))
        }
        return contents.toString()
    }

    fun createAndWriteCommit(treeHash: String, message: String): String {
        val commitBody = commitBody(treeHash, message)
        val commitHash = Hasher.hash(commitBody)

        writeObject(commitHash, commitBody)

        return commitHash
    }

    private fun writeObject(hash: String, contents: String) {
        fileInterface.makeDirectory(WriteDirectoryCommand(getObjectsDirectory(hash)))
        fileInterface.writeFile(WriteFileCommand(getObjectsPath(hash), contents))
    }

    private fun getObjectsDirectory(hash: String) = Config.OBJECTS_DIR_PATH + "/" + hash.substring(0, 2)

    private fun getObjectsPath(hash: String) = getObjectsDirectory(hash) + "/" + hash.substring(2)

    companion object {

        fun commitBody(treeHash: String, message: String) = "tree $treeHash \n author \n committer \n $message"

        fun entryLine(path: String, hash: String) = "blob $hash $path"

    }

}
