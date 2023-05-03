package mvc


/**
 * Controller: wires model and view
 */
fun main() {
    val model = PairDataSet(Pair(10, 10), Pair(20, 20), Pair(30, 30))
    val canvas = CanvasView(model)
    canvas.addObserver(object: CanvasViewObserver {
        override fun pairClicked(pair: IntPair) {
            model.add(pair)
        }
    })

    val view = window {
        title = "MVC Exercise"
        size = 600 x 300

        content {
            columns = 1
            +panel {
                +LabelView(model)
                +button("add") {
                    val pair = dualPrompt("Values?", "first","second", "0", "0")
                    pair?.let {
                        model.add(Pair(pair.first.toInt(), pair.second.toInt()))
                    }
                }
            }
            +canvas
            +TableView(model) // TODO 3: react to edits // TODO 4: react to deletes
        }
    }
    view.open()
}





