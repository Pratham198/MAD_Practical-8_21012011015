package com.example.mad_practical_8_21012011015

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addAlarm : Button = findViewById(R.id.create)
        val card : MaterialCardView = findViewById(R.id.card2)
        card.visibility=View.GONE
        addAlarm.setOnClickListener {
            TimePickerDialog(this,{tp,hour,minute->setAlarmTime(hour,minute)},Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), false).show()
            card.visibility=View.VISIBLE
        }
        val cancelAlarm : MaterialButton=findViewById(R.id.cancel)
        cancelAlarm.setOnClickListener{
            stop()
            card.visibility = View.GONE
        }
    }

    fun setAlarmTime(hour:Int,minute:Int){
        //card.visibility=View.VISIBLE
        val alarmtime = Calendar.getInstance()
        val year=alarmtime.get(Calendar.YEAR)
        val month=alarmtime.get(Calendar.MONTH)
        val date = alarmtime.get(Calendar.DATE)
        val hour = alarmtime.get(Calendar.HOUR_OF_DAY)
        val minute = alarmtime.get(Calendar.MINUTE)
        alarmtime.set(year,month,date,hour,minute,0)
        setAlarm(alarmtime.timeInMillis,AlarmBroadcastReceiver.AlarmStart)
    }
    fun stop() {
        setAlarm(-1,AlarmBroadcastReceiver.AlarmStop)
    }
    fun setAlarm(militime:Long ,action:String){
        val intentAlarm = Intent(applicationContext,AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.Alarmkey, action)
        val pendingintent = PendingIntent.getBroadcast(applicationContext,4356,intentAlarm,   PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT  )
        val alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        if (action==AlarmBroadcastReceiver.AlarmStart) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, militime, pendingintent)
        }
        else if (action==AlarmBroadcastReceiver.AlarmStop) {
            alarmManager.cancel(pendingintent)
            sendBroadcast(intentAlarm)

        }
    }
}

