package com.andreashdez.utils

enum class BoardElement(val string: String) {
    TOP_LEFT("╔══"),
    TOP_MIDDLE("══╤══"),
    TOP_RIGHT("══╗"),
    MIDDLE_LEFT("╟──"),
    MIDDLE_MIDDLE("──┼──"),
    MIDDLE_RIGHT("──╢"),
    BOTTOM_LEFT("╚══"),
    BOTTOM_MIDDLE("══╧══"),
    BOTTOM_RIGHT("══╝"),
    BORDER_LEFT("║ "),
    BORDER_MIDDLE(" │ "),
    BORDER_RIGHT(" ║");
}
