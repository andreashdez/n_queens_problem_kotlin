package com.andreashdez.app

class Gene(val position: Int) {

    var conflicts = 0

    override fun toString(): String {
        return "($position,$conflicts)"
    }

}
