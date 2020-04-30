package pl.michal.tretowicz.ui.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the [_view] that
 * can be accessed from the children classes by calling [view].
 */
open class BasePresenter<T : MvpView> : Presenter<T> {

    var subscriptions = CompositeDisposable()

    private var _view: T? = null
    val view: T
        get() { return _view ?: throw MvpViewNotAttachedException()
        }

    override fun attachView(view: T) {
        _view = view
        subscriptions.clear()
    }

    override fun detachView() {
        _view = null
        subscriptions.clear()
    }

    class MvpViewNotAttachedException : RuntimeException(
            "Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
