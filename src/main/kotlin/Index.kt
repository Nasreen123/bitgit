import java.util.ArrayList

class Index(private val fileInterface: FileInterface) {

    private val entries: ArrayList<IndexEntry> = ArrayList()

    init {
        readCurrentIndex()
    }

    private fun readCurrentIndex() {
        val existingLines = fileInterface.readFile(Config.INDEX_PATH)

        existingLines.split("\n")
                .forEach {
                    parseLine(it)?.let { entry ->
                        entries.add(entry)
        } }

    }

    private fun parseLine(line: String): IndexEntry? {
        val splitLine = line.trim().split(" ")
        if (splitLine.size == 2) {
            return IndexEntry(splitLine[0], splitLine[1])
        }
        return null
    }

    fun addBlobToIndex(workingDirPath: String, hash: String) {
        val indexLine = indexLineForBlob(workingDirPath, hash)
        fileInterface.addLine(AddLineCommand(Config.INDEX_PATH, indexLine))
        entries.add(IndexEntry(workingDirPath, hash))
    }

    fun getObjectsInIndex(): ArrayList<IndexEntry> {
        return entries
    }

    fun clear() {
        fileInterface.writeFile(WriteFileCommand(Config.INDEX_PATH, ""))
    }

    companion object {
        fun indexLineForBlob(workingDirPath: String, hash: String) = "$workingDirPath $hash\n"
    }
}

data class IndexEntry(val path: String, val hash: String)