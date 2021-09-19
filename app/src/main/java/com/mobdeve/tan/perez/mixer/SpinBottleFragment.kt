package com.mobdeve.tan.perez.mixer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.view.animation.*
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentSpinBottleBinding
import java.lang.Math.abs
import kotlin.random.Random

class SpinBottleFragment : Fragment() {
    private var _binding: FragmentSpinBottleBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentSpinBottleBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            val mainMenu = MenuFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,mainMenu)
            transaction.commit()
        }

        binding.spinBtn.setOnClickListener {
            val dirRand = Random.nextInt(4)
            spinBottle(dirRand)
        }

        binding.root.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
        object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeLeft() { spinBottle(1) }
            override fun onSwipeUp() { spinBottle(2) }
            override fun onSwipeRight() { spinBottle(3) }
            override fun onSwipeDown() { spinBottle(4) }
        })

        newRand = random.toFloat()

        animSpin = RotateAnimation(prevRand, newRand,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        animSpin.duration = 2500
        animSpin.interpolator = AccelerateDecelerateInterpolator()
        animSpin.fillAfter = true

        return binding.root
    }

    private fun spinBottle(dir: Int) {
        Log.i("my","$dir $prevRand and $newRand")
        when (dir) {
            1 -> { // left - ccw
                if (prevRand < newRand) { // if 100 < 2000
                    newRand = prevRand - Random.nextInt(500) // 2000 becomes -400
                }
            }
            2 -> { // up - cw
                if (prevRand > newRand) { // if 2000 > -100
                    newRand = prevRand + Random.nextInt(500) // -100 becomes 2500
                    if (newRand < 0) {
                        newRand = -newRand
                    }
                }
            }
            3 -> { // right - cw
                if (prevRand > newRand) {
                    newRand = prevRand + Random.nextInt(90)
                    if (newRand < 0) {
                        newRand = -newRand
                    }
                }
            }
            4 -> { // down - ccw
                if (prevRand < newRand) {
                    newRand = prevRand - Random.nextInt(500)
                }
            }
        }

        val diffRand = abs(newRand) - abs(prevRand)

        // if values get too high or too low
        if ((prevRand > 1500 && newRand > 1500) || (prevRand < -1500 && newRand < -1500)) {
            if (prevRand > 0 && newRand > 0) {
                prevRand -= 1080
                newRand -= 1080
            } else if (prevRand < 0 && newRand < 0) {
                prevRand += 1080
                newRand += 1080
            }
        }

        Log.i("my","$dir $prevRand and $newRand")

        // if bottle may not spin enough based on the start and end values
        if (diffRand < 360) {
            when (dir) {
                1 -> {
                    if (newRand > 0) {
                        newRand -= 720
                    } else {
                        newRand -= 720
                    }
                }
                2 -> {
                    if (newRand > 0) {
                        newRand += 720
                    } else {
                        newRand -= 720
                    }
                }
                3 -> {
                    if (newRand > 0) {
                        newRand += 720
                    } else {
                        newRand -= 720
                    }
                }
                4 -> {
                    if (newRand > 0) {
                        newRand -= 720
                    } else {
                        newRand -= 720
                    }
                }
            }
        } else if (diffRand > 1000) {
            when (dir) {
                1 -> {
                    if (newRand > 0) {
                        newRand += 720
                    } else {
                        newRand += 720
                    }
                }
                2 -> {
                    if (newRand > 0) {
                        newRand -= 720
                    } else {
                        newRand += 720
                    }
                }
                3 -> {
                    if (newRand > 0) {
                        newRand -= 720
                    } else {
                        newRand += 720
                    }
                }
                4 -> {
                    if (newRand > 0) {
                        newRand += 720
                    } else {
                        newRand += 720
                    }
                }
            }
        }

        // set new values for the bottle spin
        animSpin = RotateAnimation(prevRand, newRand,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        animSpin.duration = 2500
        animSpin.interpolator = AccelerateDecelerateInterpolator()
        animSpin.fillAfter = true

        Log.i("my","$dir $prevRand and $newRand")

        // start animation
        binding.bottleMain.startAnimation(animSpin)

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

        init { gestureDetector = GestureDetector(ctx, GestureListener()) }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            var touched: Boolean

            touched = gestureDetector.onTouchEvent(event)

            return touched
        }

        inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean { return true }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                var swiped = false

                // end of swipe (e2) - start (e1) to determine direction
                val diffX = e1?.x?.let { e2?.x?.minus(it) }
                val diffY = e1?.y?.let { e2?.y?.minus(it) }

                if (diffX != null && diffY != null) {
                    if (abs(diffX) > abs(diffY)) { // if there is more horizontal motion
                        if (abs(diffX) > minSwipe && abs(velocityX) > minVelo) { // if the values exceed thresholds
                            if (diffX > 0) { onSwipeRight() }
                            else { onSwipeLeft() }
                            swiped = true
                        }
                    } else {
                        if (abs(diffY) > minSwipe && abs(velocityY) > minVelo) {
                            if (diffY < 0) { onSwipeUp() }
                            else { onSwipeDown() }
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
}