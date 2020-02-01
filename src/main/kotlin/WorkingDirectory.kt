class WorkingDirectory(private val fileInterface: FileInterface) {

    fun getFileContents(path: String): String {
        return fileInterface.readFile(path)
    }

}