package com.example.calendarik

import android.animation.Animator
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.*


class MainActivity : AppCompatActivity() {

    var key = ""
    private val password = "1234"
    var maxKeyLength = password.length

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transition = LayoutTransition()
        transition.setStartDelay(LayoutTransition.APPEARING, 0)
        transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0)

        transition.setInterpolator(LayoutTransition.CHANGE_APPEARING, DecelerateInterpolator())
        transition.setInterpolator(LayoutTransition.CHANGE_DISAPPEARING, AccelerateInterpolator())

        val animDown = ObjectAnimator.ofPropertyValuesHolder(null as View?, PropertyValuesHolder.ofFloat("scaleX", 1f, 0f), PropertyValuesHolder.ofFloat("scaleY", 1f, 0f))
        animDown.duration = 300
        animDown.interpolator = LinearInterpolator()

        val animUp = ObjectAnimator.ofPropertyValuesHolder(null as View?, PropertyValuesHolder.ofFloat("scaleX", 0f, 1f), PropertyValuesHolder.ofFloat("scaleY", 0f, 1f))
        animUp.duration = 300
        animUp.interpolator = LinearInterpolator()

        transition.setAnimator(LayoutTransition.APPEARING, animUp)
        transition.setAnimator(LayoutTransition.DISAPPEARING, animDown)

        pswd.layoutTransition = transition

        button_backspace.setOnLongClickListener {
            clear()
            return@setOnLongClickListener true
        }
    }

    fun buttonClick(v: View){
        if(v.tag == "back") {
            if(key.isNotEmpty()) {
                key = key.substring(0, key.length - 1)
                pswd.removeViewAt(pswd.childCount - 1)
            }
        } else {
            if(key.length < maxKeyLength){
                val view = LayoutInflater.from(this).inflate(R.layout.pass_view, pswd, false)
                pswd.addView(view)
                key += v.tag
            }
        }
        textView.text = key
        tryPassword()
    }

    private fun clear(){
        key = ""
        textView.text = key
        for(i in pswd.childCount-1 downTo 1)
            pswd.removeViewAt(i)
    }

    private fun tryPassword(){
        Log.i("ggg", "$password $key")
        if(password.length == key.length){
            Log.i("ggg", "length")
            if(password == key){
                Log.i("ggg", "all")
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                val off = 20f

                val dur = 50L

                val startAnim = TranslateAnimation(0f, -off, 0f, 0f)
                startAnim.interpolator = DecelerateInterpolator()
                startAnim.duration = dur / 2

                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(300)
                }

                startAnim.setAnimationListener(object: Animation.AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {
                        val anim = TranslateAnimation(-off, off, 0f, 0f)
                        anim.interpolator = AccelerateDecelerateInterpolator()
                        anim.duration = dur
                        anim.repeatMode = Animation.REVERSE
                        anim.repeatCount = 5

                        shake.startAnimation(anim)

                        anim.setAnimationListener(object: Animation.AnimationListener{
                            override fun onAnimationRepeat(animation: Animation?) {}

                            override fun onAnimationEnd(animation: Animation?) {
                                val endAnim = TranslateAnimation(-off, 0f, 0f, 0f)
                                endAnim.interpolator = AccelerateInterpolator()
                                endAnim.duration = dur / 2

                                clear()

                                shake.startAnimation(endAnim)
                            }

                            override fun onAnimationStart(animation: Animation?) {}
                        })
                    }
                    override fun onAnimationStart(animation: Animation?) {}
                })
                shake.startAnimation(startAnim)
            }
        }
    }
}
