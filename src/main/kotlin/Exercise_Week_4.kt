
interface Element {
    val name: String
    val parent: DirectoryElement?
    val depth: Int
        get() = 1 // TODO recursive element count

    // TODO
}

data class FileElement(
    override val name: String,
    override val parent: DirectoryElement? = null
) : Element {

    // TODO
}

data class DirectoryElement(
    override val name: String,
    override val parent: DirectoryElement? = null,
    val children: List<Element>? = emptyList(),
    val deepElementCount: Int
) : Element {

}