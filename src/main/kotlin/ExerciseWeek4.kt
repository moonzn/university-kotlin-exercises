sealed interface Element {
    val name: String
    val parent: Element?
    val depth: Int
        get() = parent?.depth?.plus(1) ?: 0
    val path: String
        get() = parent?.path?.plus("/$name") ?: "C:/".plus(name)
    val toText: String
        get() = parent?.toText?.plus("\t".repeat(this.depth).plus("$name\n")) ?: "\n$name\n"
}

/*val toText: String
    get() {
        fun findRoot(seed: Element): Element {
            return if (seed.parent != null) {
                findRoot(seed.parent!!)
            } else {
                seed
            }
        }
        //TODO from root calculate full tree
    }*/

data class DirectoryElement(
    override val name: String,
    override val parent: DirectoryElement? = null
) : Element {
    init {
        parent?.children?.add(this)
    }

    val children = mutableListOf<Element>()
    val deepElementCount: Int
        get() = children.sumOf {
            when (it) {
              is FileElement -> 1
              is DirectoryElement -> it.deepElementCount+ 1
            }
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