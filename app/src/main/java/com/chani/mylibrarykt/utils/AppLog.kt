package com.chani.mylibrarykt.utils

import android.util.Log

object AppLog {
    const val TAG = "chani"
    var inspection: Inspection =
        Inspection.DEBUG

    fun v(msg: String) { if (inspection == Inspection.DEBUG) vF(
        msg
    )
    }
    fun d(msg: String) { if (inspection == Inspection.DEBUG) dF(
        msg
    )
    }
    fun i(msg: String) { if (inspection == Inspection.DEBUG) iF(
        msg
    )
    }
    fun w(msg: String) { if (inspection == Inspection.DEBUG) wF(
        msg
    )
    }
    fun e(msg: String) { Log.e(
        TAG,
        log(msg)
    ) }

    fun vF(msg: String) { Log.v(
        TAG,
        log(msg)
    ) }
    fun dF(msg: String) { Log.d(
        TAG,
        log(msg)
    ) }
    fun iF(msg: String) { Log.i(
        TAG,
        log(msg)
    ) }
    fun wF(msg: String) { Log.w(
        TAG,
        log(msg)
    ) }

    private fun log(msg: String): String {
        val ste = Thread.currentThread().stackTrace[5]
        val fileName = ste.fileName
        val name = fileName.substring(0, fileName.length-3)
        val method = ste.methodName
        return "[$name::$method] $msg"
    }

    enum class Inspection {
        DEBUG,
        RELEASE,
    }
}