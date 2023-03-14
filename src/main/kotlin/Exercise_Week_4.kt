
sealed interface Element {
    val name: String
    val parent: Element?
    val depth: Int
        get() = parent?.depth?.plus(1) ?: 0

    //  TODO path(), toText()
}

data class DirectoryElement(
    override val name: String,
    override val parent: DirectoryElement? = null
) : Element {
    val children: List<Element> = mutableListOf()

    init {
        //TODO add children
    }
    // TODO deepElementCount()
}

data class FileElement(
    override val name: String,
    override val parent: DirectoryElement? = null
) : Element {
    init {
        //TODO add children
    }
}
