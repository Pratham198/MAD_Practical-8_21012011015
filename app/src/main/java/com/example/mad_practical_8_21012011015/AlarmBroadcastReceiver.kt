package com.example.mad_practical_8_21012011015

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver : BroadcastReceiver() {
    companion object{
        val Alarmkey="service"
        val AlarmStart="Start"
        val AlarmStop="Stop"
    }
    override fun onReceive(context: Context, intent: Intent) {

        if (intent != null){
            val data=intent.getStringExtra(Alarmkey)
            if (data !=null){
                val intentservice=Intent(context,AlarmService::class.java)
                if(data == AlarmStart){
                    context.startService(intentservice)
                }
                else{
                    context.stopService(intentservice)
                }
            }
            }

        }
}