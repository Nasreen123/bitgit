object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val controller = Controller(FileInterface())

        val command = Controller.Command.valueOf(args[0].toUpperCase())
        val path = if (args.size > 1) args[1] else ""

        controller.callCommand(command, path)
    }
}
