package no.uia.ikt205.tictactoemu

import android.app.Application

class ApplicationInterface:Application() {

    companion object{
        lateinit var context: ApplicationInterface private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }


}