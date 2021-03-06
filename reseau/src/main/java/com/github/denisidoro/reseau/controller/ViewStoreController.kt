package com.github.denisidoro.reseau.controller

import android.support.annotation.CallSuper
import android.support.annotation.IdRes
import android.view.ViewGroup
import com.github.denisidoro.reseau.activity.BaseActivity
import com.github.denisidoro.reseau.redux.Reducer
import com.github.denisidoro.reseau.redux.RxStore
import com.github.denisidoro.reseau.viewbinder.ViewBinder
import com.github.denisidoro.reseau.viewmodel.ViewModel
import rx.Observable

abstract class ViewStoreController<S : Any, A : BaseActivity, M : ViewModel, B : ViewBinder<M>>(
        activity: A,
        rootView: ViewGroup
) : ViewController<A, M, B>(activity, rootView), HasState<S> {

    constructor(activity: A, @IdRes resourceId: Int) : this(activity, activity.findViewById(resourceId) as ViewGroup)
    constructor(activity: A) : this(activity, activity.rootView as ViewGroup)

    val store: RxStore<S> by lazy { RxStore(getInitialState(), getReducer()) }

    override var state: S = store.state
        get() = store.state

    override val stateObservable: Observable<S> by lazy { store.observable.startWith(store.state) }

    abstract fun getReducer(): Reducer<S>

    abstract fun getInitialState(): S

    override fun dispatchLocal(action: Any) = store.dispatch(action)

    @CallSuper
    override fun unsubscribe() {
        store.unsubscribe()
        super.unsubscribe()
    }

}
