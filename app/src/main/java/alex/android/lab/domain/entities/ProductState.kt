package alex.android.lab.domain.entities

sealed class ProductState<T> {

    class Idle<T> : ProductState<T>()

    class Loading<T> : ProductState<T>()

    data class Loaded<T>(val data: T) : ProductState<T>()

    data class Error<T>(val error: String) : ProductState<T>()
}