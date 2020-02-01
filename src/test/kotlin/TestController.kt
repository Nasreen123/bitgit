import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.Test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore

class TestController {

    private val fileInterface = mockk<FileInterface>(relaxed = true)

    private lateinit var writeDirectoryCommands: MutableList<WriteDirectoryCommand>
    private lateinit var writeFileCommands: MutableList<WriteFileCommand>
    private lateinit var addLineCommands: MutableList<AddLineCommand>

    @Before
    fun setup() {

        writeDirectoryCommands = mutableListOf()
        every { fileInterface.makeDirectory(capture(writeDirectoryCommands)) } just Runs

        writeFileCommands = mutableListOf()
        every { fileInterface.writeFile(capture(writeFileCommands)) } just Runs

        addLineCommands = mutableListOf()
        every { fileInterface.addLine(capture(addLineCommands)) } just Runs
    }

    @Test
    fun testInit() {
        val controller = Controller(fileInterface)
        controller.callCommand(Controller.Command.INIT, "")

        //todo add number of calls

        assertThat(writeDirectoryCommands[0].path).isEqualTo(Config.GIT_DIR_PATH)

        assertThat(writeDirectoryCommands[1].path).isEqualTo(Config.OBJECTS_DIR_PATH)

        val indexFileCommand = writeFileCommands[0]
        assertThat(indexFileCommand.path).isEqualTo(Config.INDEX_PATH)
        assertThat(indexFileCommand.contents).isEqualTo("")

        val headFileCommand = writeFileCommands[1]
        assertThat(headFileCommand.path).isEqualTo(Config.HEAD_PATH)
        assertThat(headFileCommand.contents).isEqualTo(Config.MASTER_PATH)

    }

    @Test
    fun testAdd() {
        val controller = Controller(fileInterface)
        val blobPath = "blob_path"
        val blobContents = "blob_contents"
        every { fileInterface.readFile(blobPath) } returns blobContents

        controller.callCommand(Controller.Command.ADD, blobPath)

        //todo add number of calls

        // check the dir is created
        val blobFileCommand = writeFileCommands[0]
        //todo assert path is valid objects path
        assertThat(blobFileCommand.contents).isEqualTo(blobContents)

        val indexFileCommand = addLineCommands[0]
        assertThat(indexFileCommand.path).isEqualTo(Config.INDEX_PATH)
        assertThat(indexFileCommand.line).contains(blobPath)
    }

    @Test
    fun testCommit() {
        every { fileInterface.readFile(Config.INDEX_PATH) } returns "hello hash \n"
        val controller = Controller(fileInterface)
        val commitMessage = "commit_message"
        controller.callCommand(Controller.Command.COMMIT, commitMessage)

        val writeTreeCommand = writeFileCommands[0]
        assertThat(writeTreeCommand.contents).contains("blob")

        val writeCommitCommand = writeFileCommands[1]
        assertThat(writeCommitCommand.contents).contains(commitMessage)
        assertThat(writeCommitCommand.contents).contains("tree")
    }

    @Ignore
    @Test
    fun run() {
        val controller = Controller(FileInterface())
        controller.callCommand(Controller.Command.INIT, "")
        controller.callCommand(Controller.Command.ADD, "hello")
        controller.callCommand(Controller.Command.COMMIT, "hello")
    }

}
