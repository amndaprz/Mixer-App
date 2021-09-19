package com.mobdeve.tan.perez.mixer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentCoinFlipBinding
import java.lang.Math.abs
import kotlin.random.Random

class CoinFlipFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    private var _binding: FragmentCoinFlipBinding? = null
    private val binding get() = _binding!!

    private val coinImgs = mutableListOf<Int>()
    private val coinResults = mutableListOf<String>()
    private lateinit var animFlip: Animation
    private var result: String = "None"

    @SuppressLint("UseRequireInsteadOfGet", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinFlipBinding.inflate(inflater, container, false)

        binding.settingsSkbr.setOnSeekBarChangeListener(this)

        binding.backBtn.setOnClickListener {
            val mainMenu = MenuFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,mainMenu)
            transaction.commit()
        }

        binding.shareBtn.setOnClickListener {
            val intent = Intent()
            val message =
                """
                    I flipped $result!
                    
                    Try Mixer now through this link: dlsu.edu.ph
                """

            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to "))
        }

        binding.flipBtn.setOnClickListener { flipCoin() }

        // add all coin images to coinImgs list
        coinImgs.add(R.drawable.coin_heads)
        coinImgs.add(R.drawable.coin_tails)

        // initialize String list
        coinResults.add("Heads")
        coinResults.add("Tails")

        animFlip = AnimationUtils.loadAnimation(requireContext(), R.anim.flip)

        binding.root.setOnTouchListener(
        object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() { Log.i("m", "r") }
            override fun onSwipeLeft() { Log.i("m", "l") }
            override fun onSwipeUp() { flipCoin()
                Log.i("m", "u") }
            override fun onSwipeDown() { Log.i("m", "down") }
        })

        return binding.root
    }

    private fun flipCoin() {
        // get random value from 1-2
        val random = Random.nextInt(2)

        if (binding.coinImg.visibility == View.VISIBLE) {
            // start animation
            binding.coinImg.startAnimation(animFlip)

            animFlip.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set coin image to randomly generated value after animation
                    binding.coinImg.setImageResource(coinImgs.elementAt(random))
                    result = "a coin and got ${coinResults.elementAt(random)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // unused
                    binding.coinImg.setImageResource(R.drawable.coin_neutral)
                }
            })
        } else if (binding.coinImg1.visibility == View.VISIBLE) {
            val random1 = Random.nextInt(2)
            binding.coinImg1.startAnimation(animFlip)
            binding.coinImg11.startAnimation(animFlip)

            animFlip.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set coin image to randomly generated value after animation
                    binding.coinImg1.setImageResource(coinImgs.elementAt(random))
                    binding.coinImg11.setImageResource(coinImgs.elementAt(random1))
                    result = "2 coins and got ${coinResults.elementAt(random)} and  ${coinResults.elementAt(random1)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // unused
                    binding.coinImg1.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg11.setImageResource(R.drawable.coin_neutral)
                }
            })
        } else if (binding.coinImg2.visibility == View.VISIBLE) {
            val random1 = Random.nextInt(2)
            val random2 = Random.nextInt(2)
            binding.coinImg2.startAnimation(animFlip)
            binding.coinImg22.startAnimation(animFlip)
            binding.coinImg222.startAnimation(animFlip)

            animFlip.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set coin image to randomly generated value after animation
                    binding.coinImg2.setImageResource(coinImgs.elementAt(random))
                    binding.coinImg22.setImageResource(coinImgs.elementAt(random1))
                    binding.coinImg222.setImageResource(coinImgs.elementAt(random2))
                    result = "3 coins and got ${coinResults.elementAt(random)}, ${coinResults.elementAt(random1)}, and ${coinResults.elementAt(random2)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // unused
                    binding.coinImg2.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg22.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg222.setImageResource(R.drawable.coin_neutral)
                }
            })
        } else if (binding.coinImg3.visibility == View.VISIBLE) {
            val random1 = Random.nextInt(2)
            val random2 = Random.nextInt(2)
            val random3 = Random.nextInt(2)
            binding.coinImg3.startAnimation(animFlip)
            binding.coinImg33.startAnimation(animFlip)
            binding.coinImg333.startAnimation(animFlip)
            binding.coinImg3333.startAnimation(animFlip)

            animFlip.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set coin image to randomly generated value after animation
                    binding.coinImg3.setImageResource(coinImgs.elementAt(random))
                    binding.coinImg33.setImageResource(coinImgs.elementAt(random1))
                    binding.coinImg333.setImageResource(coinImgs.elementAt(random2))
                    binding.coinImg3333.setImageResource(coinImgs.elementAt(random3))
                    result = "4 coins and got ${coinResults.elementAt(random)}, ${coinResults.elementAt(random1)}, ${coinResults.elementAt(random2)}, and ${coinResults.elementAt(random3)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // unused
                    binding.coinImg3.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg33.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg333.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg3333.setImageResource(R.drawable.coin_neutral)
                }
            })
        } else if (binding.coinImg4.visibility == View.VISIBLE) {
            val random1 = Random.nextInt(2)
            val random2 = Random.nextInt(2)
            val random3 = Random.nextInt(2)
            val random4 = Random.nextInt(2)
            binding.coinImg4.startAnimation(animFlip)
            binding.coinImg44.startAnimation(animFlip)
            binding.coinImg444.startAnimation(animFlip)
            binding.coinImg4444.startAnimation(animFlip)
            binding.coinImg44444.startAnimation(animFlip)

            animFlip.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set coin image to randomly generated value after animation
                    binding.coinImg4.setImageResource(coinImgs.elementAt(random))
                    binding.coinImg44.setImageResource(coinImgs.elementAt(random1))
                    binding.coinImg444.setImageResource(coinImgs.elementAt(random2))
                    binding.coinImg4444.setImageResource(coinImgs.elementAt(random3))
                    binding.coinImg44444.setImageResource(coinImgs.elementAt(random4))
                    result = "5 coins and got ${coinResults.elementAt(random)}, ${coinResults.elementAt(random1)}, ${coinResults.elementAt(random2)}, ${coinResults.elementAt(random3)}, and ${coinResults.elementAt(random4)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // unused
                    binding.coinImg4.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg44.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg444.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg4444.setImageResource(R.drawable.coin_neutral)
                    binding.coinImg44444.setImageResource(R.drawable.coin_neutral)
                }
            })
        }
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
            override fun onDown(e: MotionEvent): Boolean { return true }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
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
        if (p0 == binding.settingsSkbr) { // coin number
            when (p1) {
                0 -> {
                    binding.coinImg.visibility = View.VISIBLE

                    binding.coinImg1.visibility = View.INVISIBLE
                    binding.coinImg11.visibility = View.INVISIBLE
                    binding.coinImg2.visibility = View.INVISIBLE
                    binding.coinImg22.visibility = View.INVISIBLE
                    binding.coinImg222.visibility = View.INVISIBLE
                    binding.coinImg3.visibility = View.INVISIBLE
                    binding.coinImg33.visibility = View.INVISIBLE
                    binding.coinImg333.visibility = View.INVISIBLE
                    binding.coinImg3333.visibility = View.INVISIBLE
                    binding.coinImg4.visibility = View.INVISIBLE
                    binding.coinImg44.visibility = View.INVISIBLE
                    binding.coinImg444.visibility = View.INVISIBLE
                    binding.coinImg4444.visibility = View.INVISIBLE
                    binding.coinImg44444.visibility = View.INVISIBLE
                }
                1 -> {
                    binding.coinImg.visibility = View.INVISIBLE

                    binding.coinImg1.visibility = View.VISIBLE
                    binding.coinImg11.visibility = View.VISIBLE

                    binding.coinImg2.visibility = View.INVISIBLE
                    binding.coinImg22.visibility = View.INVISIBLE
                    binding.coinImg222.visibility = View.INVISIBLE
                    binding.coinImg3.visibility = View.INVISIBLE
                    binding.coinImg33.visibility = View.INVISIBLE
                    binding.coinImg333.visibility = View.INVISIBLE
                    binding.coinImg3333.visibility = View.INVISIBLE
                    binding.coinImg4.visibility = View.INVISIBLE
                    binding.coinImg44.visibility = View.INVISIBLE
                    binding.coinImg444.visibility = View.INVISIBLE
                    binding.coinImg4444.visibility = View.INVISIBLE
                    binding.coinImg44444.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.coinImg.visibility = View.INVISIBLE
                    binding.coinImg1.visibility = View.INVISIBLE
                    binding.coinImg11.visibility = View.INVISIBLE

                    binding.coinImg2.visibility = View.VISIBLE
                    binding.coinImg22.visibility = View.VISIBLE
                    binding.coinImg222.visibility = View.VISIBLE

                    binding.coinImg3.visibility = View.INVISIBLE
                    binding.coinImg33.visibility = View.INVISIBLE
                    binding.coinImg333.visibility = View.INVISIBLE
                    binding.coinImg3333.visibility = View.INVISIBLE
                    binding.coinImg4.visibility = View.INVISIBLE
                    binding.coinImg44.visibility = View.INVISIBLE
                    binding.coinImg444.visibility = View.INVISIBLE
                    binding.coinImg4444.visibility = View.INVISIBLE
                    binding.coinImg44444.visibility = View.INVISIBLE
                }
                3 -> {
                    binding.coinImg.visibility = View.INVISIBLE
                    binding.coinImg1.visibility = View.INVISIBLE
                    binding.coinImg11.visibility = View.INVISIBLE
                    binding.coinImg2.visibility = View.INVISIBLE
                    binding.coinImg22.visibility = View.INVISIBLE
                    binding.coinImg222.visibility = View.INVISIBLE

                    binding.coinImg3.visibility = View.VISIBLE
                    binding.coinImg33.visibility = View.VISIBLE
                    binding.coinImg333.visibility = View.VISIBLE
                    binding.coinImg3333.visibility = View.VISIBLE

                    binding.coinImg4.visibility = View.INVISIBLE
                    binding.coinImg44.visibility = View.INVISIBLE
                    binding.coinImg444.visibility = View.INVISIBLE
                    binding.coinImg4444.visibility = View.INVISIBLE
                    binding.coinImg44444.visibility = View.INVISIBLE
                }
                4 -> {
                    binding.coinImg.visibility = View.INVISIBLE
                    binding.coinImg1.visibility = View.INVISIBLE
                    binding.coinImg11.visibility = View.INVISIBLE
                    binding.coinImg2.visibility = View.INVISIBLE
                    binding.coinImg22.visibility = View.INVISIBLE
                    binding.coinImg222.visibility = View.INVISIBLE
                    binding.coinImg3.visibility = View.INVISIBLE
                    binding.coinImg33.visibility = View.INVISIBLE
                    binding.coinImg333.visibility = View.INVISIBLE
                    binding.coinImg3333.visibility = View.INVISIBLE

                    binding.coinImg4.visibility = View.VISIBLE
                    binding.coinImg44.visibility = View.VISIBLE
                    binding.coinImg444.visibility = View.VISIBLE
                    binding.coinImg4444.visibility = View.VISIBLE
                    binding.coinImg44444.visibility = View.VISIBLE
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