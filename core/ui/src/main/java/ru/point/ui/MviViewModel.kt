package ru.point.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class MviViewModel<UIState, Action, Event>(initialState: UIState) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    private val _actions = MutableSharedFlow<Action>(replay = 10, extraBufferCapacity = 10)
    val actions get() = _actions.asSharedFlow()

    private val _events = MutableSharedFlow<Event>()
    val events get() = _events.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            launch {
                _actions.collect { action ->
                    _state.update { reduce(action, state.value) }
                }
            }
        }
    }

    fun onAction(action: Action) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    fun onEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    protected open fun reduce(action: Action, state: UIState) = state

    inline fun <reified T : Action> handleAction(crossinline block: suspend (T) -> Unit) {
        viewModelScope.launch {
            actions.filterIsInstance<T>()
                .collect {
                    block(it)
                }
        }
    }
}