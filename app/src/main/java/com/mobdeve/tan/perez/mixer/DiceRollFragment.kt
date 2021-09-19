package com.mobdeve.tan.perez.mixer

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentDiceRollBinding
import kotlin.random.Random

class DiceRollFragment : Fragment(), SensorEventListener, SeekBar.OnSeekBarChangeListener {
    private var _binding: FragmentDiceRollBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private val diceImgs = mutableListOf<Int>()
    private val diceResults = mutableListOf<String>()
    private lateinit var anim: Animation
    private var result: String = "0"
    private var numDice: Int = 6

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiceRollBinding.inflate(inflater, container, false)

        binding.settingsSkbr.setOnSeekBarChangeListener(this)
        binding.settingsSkbr1.setOnSeekBarChangeListener(this)

        binding.backBtn.setOnClickListener {
            val mainMenu = MenuFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,mainMenu)
            transaction.commit()
        }

        binding.shareBtn.setOnClickListener {
            Log.i("my", "nye")
            val intent = Intent()
            val message =
                """
                    I rolled a $result!
                    
                    Try Mixer now through this link: dlsu.edu.ph
                """

            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to "))
        }

        binding.rollBtn.setOnClickListener { rollDice() }

        // add all dice images to diceImgs list
        diceImgs.add(R.drawable.dice_1)
        diceImgs.add(R.drawable.dice_2)
        diceImgs.add(R.drawable.dice_3)
        diceImgs.add(R.drawable.dice_4)
        diceImgs.add(R.drawable.dice_5)
        diceImgs.add(R.drawable.dice_6)
        diceImgs.add(R.drawable.dice_7)
        diceImgs.add(R.drawable.dice_8)

        // initialize String list
        diceResults.add("1")
        diceResults.add("2")
        diceResults.add("3")
        diceResults.add("4")
        diceResults.add("5")
        diceResults.add("6")
        diceResults.add("7")
        diceResults.add("8")

        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        sensorSetup()

        return binding.root
    }

    private fun rollDice() {
        // get random value from 1-8
        val random = Random.nextInt(numDice)

        if (binding.diceImg.visibility == View.VISIBLE) {
            // start animation
            binding.diceImg.startAnimation(anim)

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    //unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set dice image to randomly generated value after animation
                    binding.diceImg.setImageResource(diceImgs.elementAt(random))
                    result = diceResults.elementAt(random)
                }

                override fun onAnimationStart(p0: Animation?) {
                    // during the animation, use default dice image
                    binding.diceImg.setImageResource(R.drawable.dice_0)
                }
            })
        } else if (binding.diceImg1.visibility == View.VISIBLE) {
            // start animation
            val random1 = Random.nextInt(numDice)
            binding.diceImg1.startAnimation(anim)
            binding.diceImg11.startAnimation(anim)

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    //unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set dice image to randomly generated value after animation
                    binding.diceImg1.setImageResource(diceImgs.elementAt(random))
                    binding.diceImg11.setImageResource(diceImgs.elementAt(random1))
                    result = "${diceResults.elementAt(random)} and a ${diceResults.elementAt(random1)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // during the animation, use default dice image
                    binding.diceImg1.setImageResource(R.drawable.dice_0)
                    binding.diceImg11.setImageResource(R.drawable.dice_0)
                }
            })
        } else if (binding.diceImg2.visibility == View.VISIBLE) {
            // start animation
            val random1 = Random.nextInt(numDice)
            val random2 = Random.nextInt(numDice)

            binding.diceImg2.startAnimation(anim)
            binding.diceImg22.startAnimation(anim)
            binding.diceImg222.startAnimation(anim)

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    //unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set dice image to randomly generated value after animation
                    binding.diceImg2.setImageResource(diceImgs.elementAt(random))
                    binding.diceImg22.setImageResource(diceImgs.elementAt(random1))
                    binding.diceImg222.setImageResource(diceImgs.elementAt(random2))

                    result = "${diceResults.elementAt(random)}, ${diceResults.elementAt(random1)}, and a ${diceResults.elementAt(random2)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // during the animation, use default dice image
                    binding.diceImg2.setImageResource(R.drawable.dice_0)
                    binding.diceImg22.setImageResource(R.drawable.dice_0)
                    binding.diceImg222.setImageResource(R.drawable.dice_0)
                }
            })
        } else if (binding.diceImg3.visibility == View.VISIBLE) {
            // start animation
            val random1 = Random.nextInt(numDice)
            val random2 = Random.nextInt(numDice)
            val random3 = Random.nextInt(numDice)

            binding.diceImg3.startAnimation(anim)
            binding.diceImg33.startAnimation(anim)
            binding.diceImg333.startAnimation(anim)
            binding.diceImg3333.startAnimation(anim)

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    //unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set dice image to randomly generated value after animation
                    binding.diceImg3.setImageResource(diceImgs.elementAt(random))
                    binding.diceImg33.setImageResource(diceImgs.elementAt(random1))
                    binding.diceImg333.setImageResource(diceImgs.elementAt(random2))
                    binding.diceImg3333.setImageResource(diceImgs.elementAt(random3))

                    result = "${diceResults.elementAt(random)}, ${diceResults.elementAt(random1)}, ${diceResults.elementAt(random2)}, and a ${diceResults.elementAt(random3)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // during the animation, use default dice image
                    binding.diceImg3.setImageResource(R.drawable.dice_0)
                    binding.diceImg33.setImageResource(R.drawable.dice_0)
                    binding.diceImg333.setImageResource(R.drawable.dice_0)
                    binding.diceImg3333.setImageResource(R.drawable.dice_0)
                }
            })
        } else if (binding.diceImg4.visibility == View.VISIBLE) {
            // start animation
            val random1 = Random.nextInt(numDice)
            val random2 = Random.nextInt(numDice)
            val random3 = Random.nextInt(numDice)
            val random4 = Random.nextInt(numDice)

            binding.diceImg4.startAnimation(anim)
            binding.diceImg44.startAnimation(anim)
            binding.diceImg444.startAnimation(anim)
            binding.diceImg4444.startAnimation(anim)
            binding.diceImg44444.startAnimation(anim)

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    //unused
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // set dice image to randomly generated value after animation
                    binding.diceImg4.setImageResource(diceImgs.elementAt(random))
                    binding.diceImg44.setImageResource(diceImgs.elementAt(random1))
                    binding.diceImg444.setImageResource(diceImgs.elementAt(random2))
                    binding.diceImg4444.setImageResource(diceImgs.elementAt(random3))
                    binding.diceImg44444.setImageResource(diceImgs.elementAt(random4))

                    result = "${diceResults.elementAt(random)}, ${diceResults.elementAt(random1)}, ${diceResults.elementAt(random2)}, ${diceResults.elementAt(random3)}, and a ${diceResults.elementAt(random4)}"
                }

                override fun onAnimationStart(p0: Animation?) {
                    // during the animation, use default dice image
                    binding.diceImg4.setImageResource(R.drawable.dice_0)
                    binding.diceImg44.setImageResource(R.drawable.dice_0)
                    binding.diceImg444.setImageResource(R.drawable.dice_0)
                    binding.diceImg4444.setImageResource(R.drawable.dice_0)
                    binding.diceImg44444.setImageResource(R.drawable.dice_0)
                }
            })
        }
    }

    // setup accelerometer
    private fun sensorSetup() {
        sensorManager = this.activity?.getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    // trigger dice roll with accelerometer
    override fun onSensorChanged(e: SensorEvent?) {
        if (e?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val hori = e.values[0]
            val vert = e.values[1]

            if (vert.toInt() > 1.5 && hori.toInt() > 1.5) { rollDice() }
        }
    }

    // unused
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p0 == binding.settingsSkbr) { // dice number
            when (p1) {
                0 -> {
                    binding.diceImg.visibility = View.VISIBLE

                    binding.diceImg1.visibility = View.INVISIBLE
                    binding.diceImg11.visibility = View.INVISIBLE
                    binding.diceImg2.visibility = View.INVISIBLE
                    binding.diceImg22.visibility = View.INVISIBLE
                    binding.diceImg222.visibility = View.INVISIBLE
                    binding.diceImg3.visibility = View.INVISIBLE
                    binding.diceImg33.visibility = View.INVISIBLE
                    binding.diceImg333.visibility = View.INVISIBLE
                    binding.diceImg3333.visibility = View.INVISIBLE
                    binding.diceImg4.visibility = View.INVISIBLE
                    binding.diceImg44.visibility = View.INVISIBLE
                    binding.diceImg444.visibility = View.INVISIBLE
                    binding.diceImg4444.visibility = View.INVISIBLE
                    binding.diceImg44444.visibility = View.INVISIBLE
                }
                1 -> {
                    binding.diceImg.visibility = View.INVISIBLE

                    binding.diceImg1.visibility = View.VISIBLE
                    binding.diceImg11.visibility = View.VISIBLE

                    binding.diceImg2.visibility = View.INVISIBLE
                    binding.diceImg22.visibility = View.INVISIBLE
                    binding.diceImg222.visibility = View.INVISIBLE
                    binding.diceImg3.visibility = View.INVISIBLE
                    binding.diceImg33.visibility = View.INVISIBLE
                    binding.diceImg333.visibility = View.INVISIBLE
                    binding.diceImg3333.visibility = View.INVISIBLE
                    binding.diceImg4.visibility = View.INVISIBLE
                    binding.diceImg44.visibility = View.INVISIBLE
                    binding.diceImg444.visibility = View.INVISIBLE
                    binding.diceImg4444.visibility = View.INVISIBLE
                    binding.diceImg44444.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.diceImg.visibility = View.INVISIBLE
                    binding.diceImg1.visibility = View.INVISIBLE
                    binding.diceImg11.visibility = View.INVISIBLE

                    binding.diceImg2.visibility = View.VISIBLE
                    binding.diceImg22.visibility = View.VISIBLE
                    binding.diceImg222.visibility = View.VISIBLE

                    binding.diceImg3.visibility = View.INVISIBLE
                    binding.diceImg33.visibility = View.INVISIBLE
                    binding.diceImg333.visibility = View.INVISIBLE
                    binding.diceImg3333.visibility = View.INVISIBLE
                    binding.diceImg4.visibility = View.INVISIBLE
                    binding.diceImg44.visibility = View.INVISIBLE
                    binding.diceImg444.visibility = View.INVISIBLE
                    binding.diceImg4444.visibility = View.INVISIBLE
                    binding.diceImg44444.visibility = View.INVISIBLE
                }
                3 -> {
                    binding.diceImg.visibility = View.INVISIBLE
                    binding.diceImg1.visibility = View.INVISIBLE
                    binding.diceImg11.visibility = View.INVISIBLE
                    binding.diceImg2.visibility = View.INVISIBLE
                    binding.diceImg22.visibility = View.INVISIBLE
                    binding.diceImg222.visibility = View.INVISIBLE

                    binding.diceImg3.visibility = View.VISIBLE
                    binding.diceImg33.visibility = View.VISIBLE
                    binding.diceImg333.visibility = View.VISIBLE
                    binding.diceImg3333.visibility = View.VISIBLE

                    binding.diceImg4.visibility = View.INVISIBLE
                    binding.diceImg44.visibility = View.INVISIBLE
                    binding.diceImg444.visibility = View.INVISIBLE
                    binding.diceImg4444.visibility = View.INVISIBLE
                    binding.diceImg44444.visibility = View.INVISIBLE
                }
                4 -> {
                    binding.diceImg.visibility = View.INVISIBLE
                    binding.diceImg1.visibility = View.INVISIBLE
                    binding.diceImg11.visibility = View.INVISIBLE
                    binding.diceImg2.visibility = View.INVISIBLE
                    binding.diceImg22.visibility = View.INVISIBLE
                    binding.diceImg222.visibility = View.INVISIBLE
                    binding.diceImg3.visibility = View.INVISIBLE
                    binding.diceImg33.visibility = View.INVISIBLE
                    binding.diceImg333.visibility = View.INVISIBLE
                    binding.diceImg3333.visibility = View.INVISIBLE

                    binding.diceImg4.visibility = View.VISIBLE
                    binding.diceImg44.visibility = View.VISIBLE
                    binding.diceImg444.visibility = View.VISIBLE
                    binding.diceImg4444.visibility = View.VISIBLE
                    binding.diceImg44444.visibility = View.VISIBLE
                }
            }
        } else if (p0 == binding.settingsSkbr1) { // side number
            numDice = p1 + 3
            Log.i("tag", "$numDice")

            when (p1) {
                0 -> {
                    binding.diceImg.setImageResource(R.drawable.dice_3)
                    binding.diceImg1.setImageResource(R.drawable.dice_3)
                    binding.diceImg11.setImageResource(R.drawable.dice_3)
                    binding.diceImg2.setImageResource(R.drawable.dice_3)
                    binding.diceImg22.setImageResource(R.drawable.dice_3)
                    binding.diceImg222.setImageResource(R.drawable.dice_3)
                    binding.diceImg3.setImageResource(R.drawable.dice_3)
                    binding.diceImg33.setImageResource(R.drawable.dice_3)
                    binding.diceImg333.setImageResource(R.drawable.dice_3)
                    binding.diceImg3333.setImageResource(R.drawable.dice_3)
                    binding.diceImg4.setImageResource(R.drawable.dice_3)
                    binding.diceImg44.setImageResource(R.drawable.dice_3)
                    binding.diceImg444.setImageResource(R.drawable.dice_3)
                    binding.diceImg4444.setImageResource(R.drawable.dice_3)
                    binding.diceImg44444.setImageResource(R.drawable.dice_3)
                }
                1 -> {
                    binding.diceImg.setImageResource(R.drawable.dice_4)
                    binding.diceImg1.setImageResource(R.drawable.dice_4)
                    binding.diceImg11.setImageResource(R.drawable.dice_4)
                    binding.diceImg2.setImageResource(R.drawable.dice_4)
                    binding.diceImg22.setImageResource(R.drawable.dice_4)
                    binding.diceImg222.setImageResource(R.drawable.dice_4)
                    binding.diceImg3.setImageResource(R.drawable.dice_4)
                    binding.diceImg33.setImageResource(R.drawable.dice_4)
                    binding.diceImg333.setImageResource(R.drawable.dice_4)
                    binding.diceImg3333.setImageResource(R.drawable.dice_4)
                    binding.diceImg4.setImageResource(R.drawable.dice_4)
                    binding.diceImg44.setImageResource(R.drawable.dice_4)
                    binding.diceImg444.setImageResource(R.drawable.dice_4)
                    binding.diceImg4444.setImageResource(R.drawable.dice_4)
                    binding.diceImg44444.setImageResource(R.drawable.dice_4)
                }
                2 -> {
                    binding.diceImg.setImageResource(R.drawable.dice_5)
                    binding.diceImg1.setImageResource(R.drawable.dice_5)
                    binding.diceImg11.setImageResource(R.drawable.dice_5)
                    binding.diceImg2.setImageResource(R.drawable.dice_5)
                    binding.diceImg22.setImageResource(R.drawable.dice_5)
                    binding.diceImg222.setImageResource(R.drawable.dice_5)
                    binding.diceImg3.setImageResource(R.drawable.dice_5)
                    binding.diceImg33.setImageResource(R.drawable.dice_5)
                    binding.diceImg333.setImageResource(R.drawable.dice_5)
                    binding.diceImg3333.setImageResource(R.drawable.dice_5)
                    binding.diceImg4.setImageResource(R.drawable.dice_5)
                    binding.diceImg44.setImageResource(R.drawable.dice_5)
                    binding.diceImg444.setImageResource(R.drawable.dice_5)
                    binding.diceImg4444.setImageResource(R.drawable.dice_5)
                    binding.diceImg44444.setImageResource(R.drawable.dice_5)
                }
                3 -> {
                    binding.diceImg.setImageResource(R.drawable.dice_6)
                    binding.diceImg1.setImageResource(R.drawable.dice_6)
                    binding.diceImg11.setImageResource(R.drawable.dice_6)
                    binding.diceImg2.setImageResource(R.drawable.dice_6)
                    binding.diceImg22.setImageResource(R.drawable.dice_6)
                    binding.diceImg222.setImageResource(R.drawable.dice_6)
                    binding.diceImg3.setImageResource(R.drawable.dice_6)
                    binding.diceImg33.setImageResource(R.drawable.dice_6)
                    binding.diceImg333.setImageResource(R.drawable.dice_6)
                    binding.diceImg3333.setImageResource(R.drawable.dice_6)
                    binding.diceImg4.setImageResource(R.drawable.dice_6)
                    binding.diceImg44.setImageResource(R.drawable.dice_6)
                    binding.diceImg444.setImageResource(R.drawable.dice_6)
                    binding.diceImg4444.setImageResource(R.drawable.dice_6)
                    binding.diceImg44444.setImageResource(R.drawable.dice_6)

                }
                4 -> {
                    binding.diceImg.setImageResource(R.drawable.dice_7)
                    binding.diceImg1.setImageResource(R.drawable.dice_7)
                    binding.diceImg11.setImageResource(R.drawable.dice_7)
                    binding.diceImg2.setImageResource(R.drawable.dice_7)
                    binding.diceImg22.setImageResource(R.drawable.dice_7)
                    binding.diceImg222.setImageResource(R.drawable.dice_7)
                    binding.diceImg3.setImageResource(R.drawable.dice_7)
                    binding.diceImg33.setImageResource(R.drawable.dice_7)
                    binding.diceImg333.setImageResource(R.drawable.dice_7)
                    binding.diceImg3333.setImageResource(R.drawable.dice_7)
                    binding.diceImg4.setImageResource(R.drawable.dice_7)
                    binding.diceImg44.setImageResource(R.drawable.dice_7)
                    binding.diceImg444.setImageResource(R.drawable.dice_7)
                    binding.diceImg4444.setImageResource(R.drawable.dice_7)
                    binding.diceImg44444.setImageResource(R.drawable.dice_7)
                }
                5 -> {
                    binding.diceImg.setImageResource(R.drawable.dice_8)
                    binding.diceImg1.setImageResource(R.drawable.dice_8)
                    binding.diceImg11.setImageResource(R.drawable.dice_8)
                    binding.diceImg2.setImageResource(R.drawable.dice_8)
                    binding.diceImg22.setImageResource(R.drawable.dice_8)
                    binding.diceImg222.setImageResource(R.drawable.dice_8)
                    binding.diceImg3.setImageResource(R.drawable.dice_8)
                    binding.diceImg33.setImageResource(R.drawable.dice_8)
                    binding.diceImg333.setImageResource(R.drawable.dice_8)
                    binding.diceImg3333.setImageResource(R.drawable.dice_8)
                    binding.diceImg4.setImageResource(R.drawable.dice_8)
                    binding.diceImg44.setImageResource(R.drawable.dice_8)
                    binding.diceImg444.setImageResource(R.drawable.dice_8)
                    binding.diceImg4444.setImageResource(R.drawable.dice_8)
                    binding.diceImg44444.setImageResource(R.drawable.dice_8)
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
