package mvc

import javax.swing.JLabel

// TODO 1: make this class react to changes in the model
class LabelView(private val model: PairDataSet) : JLabel() {
    init {
        text = "$model"
        model.addObserver(object: PairDataSetObserver {
            override fun pairAdded(pair: IntPair) {
                text = "$model"
            }
        })
    }
}