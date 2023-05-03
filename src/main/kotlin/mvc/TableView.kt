package mvc

import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * View for listing the int pairs in an editable table
 */
class TableView(val model: PairDataSet) : JPanel() {
    private val observers: MutableList<TableViewObserver> = mutableListOf()

    init {
        layout = GridLayout(0, 1)
        model.forEach {
            addPair(it)
        }

        // TODO 4: register observer in the model
    }

    fun addObserver(observer: TableViewObserver) {
        observers.add(observer)
    }

    private fun addPair(pair: IntPair) {
        add(PairComponent(pair))
        revalidate()
        repaint()
    }

    fun removePair(pair: IntPair) {
        val find = components.find { it is PairComponent && it.matches(pair) }
        find?.let {
            remove(find)
        }
        revalidate()
        repaint()
    }

    fun replacePair(old: IntPair, new: IntPair) {
        val find = components.find { it is PairComponent && it.matches(old) } as? PairComponent
        find?.let {
            find.modify(new)
        }
    }

    inner class PairComponent(var pair: IntPair) : JComponent() {
        val first = JTextField("${pair.first}")
        val second = JTextField("${pair.second}")

        inner class MouseClick(val first: Boolean) : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val newPair = dualPrompt("new values", "first", "second", pair.first.toString(), pair.second.toString())
                newPair?.let {
                    observers.forEach {
                        it.pairModified(pair, IntPair(newPair.first.toInt(), newPair.second.toInt()))
                    }
                }
            }
        }

        init {
            layout = GridLayout(0, 3)

            first.isEnabled = false
            first.addMouseListener(MouseClick(true))
            add(first)

            second.isEnabled = false
            second.addMouseListener(MouseClick(false))
            add(second)

            add(button("delete") {
                // TODO 4: fire event
            })
        }

        fun modify(new: Pair<Int, Int>) {
            pair = new
            first.text = "${new.first}"
            second.text = "${new.second}"
        }

        fun matches(p: Pair<Int, Int>) = pair == p
    }
}

interface TableViewObserver {
    fun pairModified(old: IntPair, new: IntPair) {}

    // TODO: observable event: delete mvc.button click
}