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
    val children = mutableListOf<Element>()
    val deepElementCount: Int
        get() = -1

    init {
        parent?.children?.add(this)
    }
}

data class FileElement(
    override val name: String,
    override val parent: DirectoryElement? = null
) : Element {
    init {
        parent?.children?.add(this)
    }
}
