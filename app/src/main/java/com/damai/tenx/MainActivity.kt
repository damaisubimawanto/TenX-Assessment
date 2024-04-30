package com.damai.tenx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.damai.tenx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pixelFiveByFive = PixelFiveByFive()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCase1.setOnClickListener {
            val result = pixelFiveByFive.removeNonColorfulPixel(
                givenPixelList = listOf(
                    Triple(34, 203, 55),
                    Triple(67, 76, 73),
                    Triple(99, 105, 93),
                    Triple(178, 173, 169),
                    Triple(144, 89, 54),

                    Triple(22, 20, 18),
                    Triple(10, 40, 50),
                    Triple(171, 180, 211),
                    Triple(150, 150, 90),
                    Triple(50, 150, 150),

                    Triple(209, 109, 107),
                    Triple(111, 117, 212),
                    Triple(214, 113, 165),
                    Triple(45, 137, 212),
                    Triple(182, 240, 245),

                    Triple(199, 184, 72),
                    Triple(204, 75, 193),
                    Triple(140, 132, 139),
                    Triple(87, 76, 63),
                    Triple(170, 209, 167),

                    Triple(1, 90, 20),
                    Triple(174, 214, 174),
                    Triple(196, 106, 112),
                    Triple(173, 166, 167),
                    Triple(48, 35, 46),
                )
            )

            val resultText = "Result = $result"
            binding.tvResultCase1.text = resultText
        }

        binding.btnCase2.setOnClickListener {
            val result = pixelFiveByFive.determineColorfulImage(
                givenPixelList = listOf(
                    Triple(87, 76, 63),
                    Triple(67, 76, 73),
                    Triple(99, 105, 93),
                    Triple(178, 173, 169),
                    Triple(48, 35, 46),

                    Triple(22, 20, 18),
                    Triple(10, 40, 50),
                    Triple(67, 76, 73),
                    Triple(173, 166, 167),
                    Triple(87, 76, 63),

                    Triple(10, 40, 50),
                    Triple(99, 105, 93),
                    Triple(178, 173, 169),
                    Triple(67, 76, 73),
                    Triple(22, 20, 18),

                    Triple(22, 20, 18),
                    Triple(87, 76, 63),
                    Triple(140, 132, 139),
                    Triple(87, 76, 63),
                    Triple(99, 105, 93),

                    Triple(99, 105, 93),
                    Triple(87, 76, 63),
                    Triple(67, 76, 73),
                    Triple(173, 166, 167),
                    Triple(48, 35, 46),
                )
            )

            val resultText = "The image is $result"
            binding.tvResultCase2.text = resultText
        }
    }
}