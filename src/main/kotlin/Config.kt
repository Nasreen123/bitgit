object Config {

    @JvmField val GIT_DIR_PATH = "abitofgit"
    @JvmField val OBJECTS_DIR_PATH = "$GIT_DIR_PATH/objects"
    @JvmField val HEAD_PATH = "$GIT_DIR_PATH/HEAD"
    @JvmField val INDEX_PATH = "$GIT_DIR_PATH/index"
    @JvmField val REFS_PATH = "$GIT_DIR_PATH/refs"
    @JvmField val HEADS_PATH = "$REFS_PATH/heads"
    @JvmField val MASTER_PATH = "$HEADS_PATH/master"

}
