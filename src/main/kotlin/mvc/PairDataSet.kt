package mvc

typealias IntPair = Pair<Int, Int>

/**
 * Set of integer pairs (no duplicates), serving as the model
 */
class PairDataSet(vararg pairs: IntPair) : Iterable<IntPair> {
    private val data = pairs.toMutableSet()
    private val observers: MutableList<PairDataSetObserver> = mutableListOf()

    fun addObserver(observer: PairDataSetObserver) = observers.add(observer)

    fun removeObserver(observer: PairDataSetObserver) = observers.remove(observer)

    fun add(p: IntPair) {
        if (data.add(p))
        // fires event to registered observers
            observers.forEach {
                it.pairAdded(p)
            }
    }

    fun remove(p: IntPair) {
        if (data.remove(p))
            observers.forEach {
                it.pairRemoved(p)
            }
    }

    fun modify(old: IntPair, new: IntPair) {
    }

    override fun iterator(): Iterator<IntPair> = data.iterator()

    override fun toString(): String {
        return data.joinToString(separator = "    ") { it.toString() }
    }
}

interface PairDataSetObserver {
    fun pairAdded(pair: IntPair) { }
    fun pairRemoved(pair: IntPair) { }
    fun pairModified(old: IntPair, new: IntPair) { }
    // TODO 3: observable operations for removing and replacing pairs
}