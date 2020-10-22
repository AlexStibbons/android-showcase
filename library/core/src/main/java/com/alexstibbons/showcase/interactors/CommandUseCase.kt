package com.alexstibbons.showcase.interactors

import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import kotlinx.coroutines.*


/**
 * @author: N. Vuksic github @ Vuksa
 *
 * I'll leave it like this for now, though I'm not sure about this particular solution
 * A "flaw" is the fact we must remember to clear the use cases in view model's onClear
 * Also, using async doesn't make much sense since it's no different
 * if we used simply ` withContext` and used `viewModelScope` withing the view models
 *
 */
abstract class CommandUseCase<in Params> {

    private val mainJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + mainJob)

    abstract suspend fun run(params: Params?): Response<Failure, Unit>

    operator fun invoke(
        params: Params? = null,
        onResult: (Response<Failure, Unit>) -> Unit = {}
    ) {
        val backgroundJob = uiScope.async(Dispatchers.IO) { run(params) }
        uiScope.launch { onResult(backgroundJob.await()) }
    }

    open fun cancel() {
        mainJob.cancel()
    }
}