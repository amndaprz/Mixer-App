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
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentMagicBallBinding
import kotlin.random.Random

class MagicBallFragment : Fragment(), SensorEventListener {
    private var _binding: FragmentMagicBallBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private val ballResponses = mutableListOf<Int>()
    private val ballResponsesShare = mutableListOf<String>()
    private lateinit var animJitter: Animation
    private lateinit var animFade: Animation
    private lateinit var animBlink: Animation

    private lateinit var result: String

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMagicBallBinding.inflate(inflater, container, false)

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
                    I asked the Magic 8-Ball and got '$result' as a response!
                    
                    Try Mixer now through this link: dlsu.edu.ph
                """

            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to "))
        }

        binding.shakeBtn.setOnClickListener { shakeBall() }

        // add all dice images to ballResponses list
        ballResponses.add(R.drawable.ball_certain)
        ballResponses.add(R.drawable.ball_definitely)
        ballResponses.add(R.drawable.ball_yes)
        ballResponses.add(R.drawable.ball_try)
        ballResponses.add(R.drawable.ball_doubt)
        ballResponses.add(R.drawable.ball_no)
        ballResponses.add(R.drawable.ball_quite)

        // initialize String list
        ballResponsesShare.add("It is certain.")
        ballResponsesShare.add("Definitely.")
        ballResponsesShare.add("Yes.")
        ballResponsesShare.add("Try again.")
        ballResponsesShare.add("Very doubtful.")
        ballResponsesShare.add("No.")
        ballResponsesShare.add("Not quite.")

        animJitter = AnimationUtils.loadAnimation(requireContext(), R.anim.jitter)
        animFade = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        animBlink = AnimationUtils.loadAnimation(requireContext(), R.anim.blink)

        sensorSetup()

        return binding.root
    }

    private fun shakeBall() {
        binding.ballResponse.visibility = View.INVISIBLE
        // get random value from 1-7
        val random = Random.nextInt(7)

        // start animation
        binding.ballMain.startAnimation(animJitter)

        animJitter.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
                // unused
            }

            override fun onAnimationEnd(p0: Animation?) {
                // get random response and fade in
                binding.ballResponse.setImageResource(ballResponses.elementAt(random))
                binding.ballResponse.startAnimation(animFade)
                binding.ballResponse.visibility = View.VISIBLE
                result = ballResponsesShare.elementAt(random)
            }

            override fun onAnimationStart(p0: Animation?) {
                // blink sparkles
                binding.ballLeft.startAnimation(animBlink)
                binding.ballRight.startAnimation(animBlink)
            }
        })
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

    // trigger magic 8-ball with accelerometer
    override fun onSensorChanged(e: SensorEvent?) {
        if (e?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val hori = e.values[0]
            val vert = e.values[1]

            if (vert.toInt() > 1.5 && hori.toInt() > 1.5) { shakeBall() }
        }
    }

    // unused
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}