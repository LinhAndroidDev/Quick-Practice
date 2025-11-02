package com.example.quickpractice.ui.theme.screen.exam.model

enum class Choice(val title: String, val value: Int) {
    A("A", 0),
    B("B", 1),
    C("C", 2),
    D("D", 3);

    companion object {
        fun fromValue(value: Int): Choice {
            return entries.find { it.value == value } ?: A
        }
    }
}