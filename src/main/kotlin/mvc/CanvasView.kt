package mvc

import java.awt.Color
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JComponent

// TODO 2: make these objects observable with respect to mouse clicks - observers should obtain the clicked coordinate
class CanvasView(private val model: PairDataSet) : JComponent() {
    private val observers: MutableList<CanvasViewObserver> = mutableListOf()

    public override fun paintComponent(g: Graphics) {
        g.color = Color.BLACK
        for (d in model) {
            g.fillOval(d.first, d.second, 10, 10)
        }
    }

    init {
        border = BorderFactory.createLineBorder(Color.BLACK)

        // reacts to changes in the model
        model.addObserver(object : PairDataSetObserver {
            override fun pairAdded(pair: IntPair) {
                revalidate()
                repaint()
            }

            override fun pairRemoved(pair: IntPair) {
                revalidate()
                repaint()
            }

            override fun pairModified(old: IntPair, new: IntPair) {
                revalidate()
                repaint()
            }
        })

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val pairClicked = IntPair(e.x, e.y)
                observers.forEach {
                    it.pairClicked(pairClicked)
                }
            }
        })
    }

    fun addObserver(observer: CanvasViewObserver) {
        observers.add(observer)
    }
}

interface CanvasViewObserver {
    fun pairClicked(pair: IntPair) {}
}
