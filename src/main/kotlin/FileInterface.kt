import java.io.File

class FileInterface {

    fun makeDirectory(command: WriteDirectoryCommand) {
        //File(command.path).mkdirs()
        val file = java.io.File(command.path)
        if (!file.exists()) {
            file.mkdir()
        }
    }

    fun writeFile(command: WriteFileCommand) {
        File(command.path).writeText(command.contents)
    }

    fun addLine(command: AddLineCommand) {
        File(command.path).appendText(command.line)
    }

    fun readFile(path: String): String {
        return File(path).readText()
    }

}

interface Command
data class WriteDirectoryCommand(val path: String): Command
data class WriteFileCommand(val path: String,
                            val contents: String) : Command
data class AddLineCommand(val path: String,
                          val line : String) : Command