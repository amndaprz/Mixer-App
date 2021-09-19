package com.mobdeve.tan.perez.mixer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.view.animation.*
import android.widget.SeekBar
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentRandomColorBinding
import java.lang.Math.abs
import kotlin.random.Random

class RandomColorFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    private var _binding: FragmentRandomColorBinding? = null
    private val binding get() = _binding!!

    private val colorsList = mutableListOf<Int>()
    private var result: String = "None"
    private lateinit var animSpin: RotateAnimation
    private var min: Float = 200.0f
    private var max: Float = 2000.0f
    private var prevRand: Float = 0.0f
    private var newRand: Float = 0.0f
    private var random: Double = min + Random.nextDouble() * (max - min)

    @SuppressLint("UseRequireInsteadOfGet", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomColorBinding.inflate(inflater, container, false)

        binding.settingsSkbr.setOnSeekBarChangeListener(this)

        binding.backBtn.setOnClickListener {
            val mainMenu = MenuFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout, mainMenu)
            transaction.commit()
        }

        binding.generateBtn.setOnClickListener {
            generateColor()
        }

        binding.root.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
        object : OnSwipeTouchListener(requireContext()), View.OnTouchListener {
            override fun onSwipeLeft() {
                generateColor()
            }

            override fun onSwipeUp() {
                generateColor()
            }

            override fun onSwipeRight() {
                generateColor()
            }

            override fun onSwipeDown() {
                generateColor()
            }
        })

        newRand = random.toFloat()

        animSpin = RotateAnimation(
            prevRand, newRand,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animSpin.duration = 2500
        animSpin.interpolator = AccelerateDecelerateInterpolator()
        animSpin.fillAfter = true

        return binding.root
    }

    private fun generateColor() {
        if (prevRand > newRand) { // if 2000 > -100
            newRand = prevRand + Random.nextInt(500) // -100 becomes 2500
            if (newRand < 0) {
                newRand = -newRand
            }
        }

        val diffRand = abs(newRand) - abs(prevRand)

        // if values get too high or too low
        if (prevRand > 1500 && newRand > 1500) {
            if (prevRand > 0 && newRand > 0) {
                prevRand -= 1080
                newRand -= 1080
            }
        }

        // if bottle may not spin enough based on the start and end values
        if (diffRand < 360) {
            if (newRand > 0) {
                newRand += 720
            } else {
                newRand -= 720
            }
        } else if (diffRand > 1000) {
            if (newRand > 0) {
                newRand -= 720
            } else {
                newRand += 720
            }
        }

        // set new values for the bottle spin
        animSpin = RotateAnimation(
            prevRand, newRand,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animSpin.duration = 2500
        animSpin.interpolator = AccelerateDecelerateInterpolator()
        animSpin.fillAfter = true

        // start animation
        if (binding.color2.visibility == View.VISIBLE) {
            binding.color2.startAnimation(animSpin)
        } else if (binding.color3.visibility == View.VISIBLE) {
            binding.color3.startAnimation(animSpin)
        } else if (binding.color4.visibility == View.VISIBLE) {
            binding.color4.startAnimation(animSpin)
        } else if (binding.color5.visibility == View.VISIBLE) {
            binding.color5.startAnimation(animSpin)
        } else if (binding.color6.visibility == View.VISIBLE) {
            binding.color6.startAnimation(animSpin)
        } else if (binding.color7.visibility == View.VISIBLE) {
            binding.color7.startAnimation(animSpin)
        } else if (binding.color8.visibility == View.VISIBLE) {
            binding.color8.startAnimation(animSpin)
        } else if (binding.color9.visibility == View.VISIBLE) {
            binding.color9.startAnimation(animSpin)
        } else if (binding.color10.visibility == View.VISIBLE) {
            binding.color10.startAnimation(animSpin)
        }

        // set new values for the next bottle spin
        prevRand = newRand
        random = min + Random.nextDouble() * (max - min)
        newRand = random.toFloat()
    }

    open class OnSwipeTouchListener(ctx: Context) : View.OnTouchListener {
        val gestureDetector: GestureDetector

        companion object {
            private const val minSwipe = 200
            private const val minVelo = 200
        }

        init {
            gestureDetector = GestureDetector(ctx, GestureListener())
        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            var touched: Boolean

            touched = gestureDetector.onTouchEvent(event)

            return touched
        }

        inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                var swiped = false

                // end of swipe (e2) - start (e1) to determine direction
                val diffX = e1?.x?.let { e2?.x?.minus(it) }
                val diffY = e1?.y?.let { e2?.y?.minus(it) }

                if (diffX != null && diffY != null) {
                    if (abs(diffX) > abs(diffY)) { // if there is more horizontal motion
                        if (abs(diffX) > minSwipe && abs(velocityX) > minVelo) { // if the values exceed thresholds
                            if (diffX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                            swiped = true
                        }
                    } else {
                        if (abs(diffY) > minSwipe && abs(velocityY) > minVelo) {
                            if (diffY < 0) {
                                onSwipeUp()
                            } else {
                                onSwipeDown()
                            }
                            swiped = true
                        }
                    }
                } else {
                    onSwipeUp()
                    swiped = true
                }
                return swiped
            }
        }

        open fun onSwipeRight() {}

        open fun onSwipeLeft() {}

        open fun onSwipeUp() {}

        open fun onSwipeDown() {}
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p0 == binding.settingsSkbr) { // wheel number
            when (p1) {
                0 -> {
                    binding.color2.visibility = View.VISIBLE

                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                1 -> {
                    binding.color2.visibility = View.INVISIBLE

                    binding.color3.visibility = View.VISIBLE

                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color2.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                2 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE

                    binding.color4.visibility = View.VISIBLE

                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color2.clearAnimation()
                    binding.color3.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                3 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE

                    binding.color5.visibility = View.VISIBLE

                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color2.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                4 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE

                    binding.color6.visibility = View.VISIBLE

                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color2.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                5 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE

                    binding.color7.visibility = View.VISIBLE

                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color2.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                6 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE

                    binding.color8.visibility = View.VISIBLE

                    binding.color9.visibility = View.INVISIBLE
                    binding.color10.visibility = View.INVISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color2.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color10.clearAnimation()
                }
                7 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE

                    binding.color9.visibility = View.VISIBLE

                    binding.color10.visibility = View.INVISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color2.clearAnimation()
                    binding.color10.clearAnimation()
                }
                8 -> {
                    binding.color2.visibility = View.INVISIBLE
                    binding.color3.visibility = View.INVISIBLE
                    binding.color4.visibility = View.INVISIBLE
                    binding.color5.visibility = View.INVISIBLE
                    binding.color6.visibility = View.INVISIBLE
                    binding.color7.visibility = View.INVISIBLE
                    binding.color8.visibility = View.INVISIBLE
                    binding.color9.visibility = View.INVISIBLE

                    binding.color10.visibility = View.VISIBLE

                    binding.color3.clearAnimation()
                    binding.color4.clearAnimation()
                    binding.color5.clearAnimation()
                    binding.color6.clearAnimation()
                    binding.color7.clearAnimation()
                    binding.color8.clearAnimation()
                    binding.color9.clearAnimation()
                    binding.color2.clearAnimation()
                }
            }
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        return
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        return
    }
}
