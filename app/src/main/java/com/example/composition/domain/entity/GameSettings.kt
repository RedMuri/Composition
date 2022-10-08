package com.example.composition.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val percentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)