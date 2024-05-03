package com.okrama.recipesbook.base

import android.database.sqlite.SQLiteConstraintException
import timber.log.Timber

object DatabaseUtils {
    inline fun safeLaunch(block: () -> Unit) {
        try {
            block()
        } catch (e: SQLiteConstraintException) {
            Timber.e(e,"Constraint fail $e")
        } catch (e : Exception){
            Timber.e(e,"Database unknown exception: $e")
        }
    }
}