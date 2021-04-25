/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chani.mylibrarykt.util

import android.util.Log

object AppLog {
    const val TAG = "dncks1525"

    var inspection: Inspection = Inspection.DEBUG

    fun v(msg: String) { if (inspection == Inspection.DEBUG) vF(msg) }

    fun d(msg: String) { if (inspection == Inspection.DEBUG) dF(msg) }

    fun i(msg: String) { if (inspection == Inspection.DEBUG) iF(msg) }

    fun w(msg: String) { if (inspection == Inspection.DEBUG) wF(msg) }

    fun e(msg: String) { Log.e(TAG, log(msg)) }

    fun vF(msg: String) { Log.v(TAG, log(msg)) }

    fun dF(msg: String) { Log.d(TAG, log(msg)) }

    fun iF(msg: String) { Log.i(TAG, log(msg)) }

    fun wF(msg: String) { Log.w(TAG, log(msg)) }

    private fun log(msg: String): String {
        val ste = Thread.currentThread().stackTrace[5]
        val fileName = ste.fileName
        val name = fileName.substring(0, fileName.length - 3)
        val method = ste.methodName
        return "[$name::$method] $msg"
    }

    enum class Inspection {
        DEBUG,
        RELEASE,
    }
}