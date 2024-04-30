package com.damai.tenx

/**
 * Created by damai007 on 30/April/2024
 */
sealed class ImageState {

    data object Colorful : ImageState()

    data object BlackOrWhite : ImageState()
}