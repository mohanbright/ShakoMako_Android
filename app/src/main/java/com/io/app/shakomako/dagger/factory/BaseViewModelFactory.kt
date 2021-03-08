package com.io.app.shakomako.dagger.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class BaseViewModelFactory  : ViewModelProvider.Factory{

    private var creators: Map<Class<out ViewModel>, Provider<ViewModel>>? =
        null

    @Inject
    fun BaseViewModelFactory(creators: Map<Class<out ViewModel>, Provider<ViewModel>>?) {
        this.creators = creators
    }




    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var creator: Provider<out ViewModel?>? = creators!![modelClass]
        if (creator == null) {
            for ((key, value) in creators!!) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        requireNotNull(creator) { "unknown model class $modelClass" }
        return try {
            (creator.get() as T?)!!
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}