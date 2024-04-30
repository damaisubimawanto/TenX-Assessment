package com.damai.tenx

import android.util.Log

/**
 * Created by damai007 on 30/April/2024
 */
class PixelFiveByFive {

    private fun Int.convertToRange(): Double {
        return this / HIGH_PIXEL
    }

    private fun average(pixel: Triple<Int, Int, Int>): Int {
        return (pixel.first + pixel.second + pixel.third) / 3
    }

    private fun max(pixel: Triple<Int, Int, Int>): Double {
        var result = 0.0
        pixel.first.convertToRange().let { red ->
            if (red > result) {
                result = red
            }
        }
        pixel.second.convertToRange().let { green ->
            if (green > result) {
                result = green
            }
        }
        pixel.third.convertToRange().let { blue ->
            if (blue > result) {
                result = blue
            }
        }
        return result
    }

    private fun min(pixel: Triple<Int, Int, Int>): Double {
        var result = 1.0
        pixel.first.convertToRange().let { red ->
            if (red < result) {
                result = red
            }
        }
        pixel.second.convertToRange().let { green ->
            if (green < result) {
                result = green
            }
        }
        pixel.third.convertToRange().let { blue ->
            if (blue < result) {
                result = blue
            }
        }
        return result
    }

    private fun getLuminosity(pixel: Triple<Int, Int, Int>): Double {
        val bigM = max(pixel = pixel)
        val smallM = min(pixel = pixel)
        return (bigM + smallM) / 2
    }

    private fun getHue(pixel: Triple<Int, Int, Int>): Int {
        val red = pixel.first.convertToRange()
        val green = pixel.second.convertToRange()
        val blue = pixel.third.convertToRange()
        val result = when {
            //(A) If R >= G >= B
            green in blue..red -> {
                (60 * ( (green - blue) / (red - blue) )).toInt()
            }

            //(B) If G > R >= B
            green > red && red >= blue -> {
                (60 * ( 2 - ( (red - blue) / (green - blue) ) )).toInt()
            }

            //(C) If G >= B > R
            green >= blue && blue > red -> {
                (60 * ( 2 + ( (blue - red) / (green - red) ) )).toInt()
            }

            //(D) If B > G > R
            blue > green && green > red -> {
                (60 * ( 4 - ( (green - red) / (blue - red) ) )).toInt()
            }

            //(E) If B > R >= G
            blue > red && red > green -> {
                (60 * ( 4 + ( (red - green) / (blue - green) ) )).toInt()
            }

            //(F) If R >= B > G
            red >= blue && blue > green -> {
                (60 * ( 6 - ( (blue - green) / (red - green) ) )).toInt()
            }

            else -> 0
        }

        return result
    }

    private fun getSaturation(
        luminosity: Double,
        pixel: Triple<Int, Int, Int>
    ): Double {
        return when {
            luminosity < 1.0 -> {
                (max(pixel = pixel) - min(pixel = pixel)) / (1 - ((2 * luminosity) - 1))
            }

            luminosity == 1.0 -> 0.0

            else -> -1.0
        }
    }

    fun removeNonColorfulPixel(
        givenPixelList: List<Triple<Int, Int, Int>>
    ): List<Triple<Int, Int, Int>> {
        val resultList = mutableListOf<Triple<Int, Int, Int>>()
        for (pixel in givenPixelList) {
            val average = average(pixel = pixel)
            val luminosity = getLuminosity(pixel = pixel)
            val hue = getHue(pixel = pixel)
            val saturation = getSaturation(
                luminosity = luminosity,
                pixel = pixel
            ).let {
                (it * 100).toInt()
            }
            Log.d(TAG, "pixel = $pixel, luminosity = ${(luminosity * 100).toInt()}%, hue = $hue degree, saturation = ${saturation}%")

            if (saturation > 20) {
                resultList.add(pixel)
            }
        }
        Log.d(TAG, "\n\nResult list = $resultList")
        return resultList.toList()
    }

    fun determineColorfulImage(
        givenPixelList: List<Triple<Int, Int, Int>>
    ): ImageState {
        var totalSaturation = 0
        var increment = 0
        for (pixel in givenPixelList) {
            val luminosity = getLuminosity(pixel = pixel)
            val saturation = getSaturation(
                luminosity = luminosity,
                pixel = pixel
            ).let {
                (it * 100).toInt()
            }
            totalSaturation += saturation
            increment++
        }

        val average = totalSaturation / increment
        return if (average < 30) {
            ImageState.BlackOrWhite
        } else {
            ImageState.Colorful
        }
    }

    companion object {
        private const val HIGH_PIXEL = 255.0
        private const val TAG = "PixelResult"
    }
}