package alex.android.lab.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class Request(val id: String) {
    override fun toString() = "Request(id='$id')"
}

class Response(val id: String) {
    override fun toString() = "Response(id='$id')"
}

interface LegacyApi {
    fun doRequest(request: Request, onComplete: (Response) -> Unit)
}

class LegacyApiImpl : LegacyApi {
    override fun doRequest(request: Request, onComplete: (Response) -> Unit) {
        // Имитация асинхронного запроса со случайной задержкой
        val delay = Random.nextLong(100, 500)
        Thread.sleep(delay)
        val response = Response(request.id)
        onComplete(response)
    }
}

class BatchHelper(private val legacyApi: LegacyApi) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun doRequests(requests: List<Request>, onComplete: (List<Response>) -> Unit) {
        scope.launch {
            val deferredResponses = requests.mapIndexed { index, request ->
                async {
                    // Обёртка для колбэка в suspend функцию
                    suspendCoroutine<Response> { continuation -> // suspend fun doRequest(request: Request): Response // suspendCoroutine
                        legacyApi.doRequest(request) { response ->
                            println("Received ${response.id}")
                            continuation.resume(response)
                        }
                    }


                }
            }

            val responses = deferredResponses.awaitAll()
            onComplete(responses)
        }


//        scope.launch {
//            val responses = requests.map { request -> // map to List<<Response>
//                async {
//                    var response: Response? = null
//                    legacyApi.doRequest(request) {
//                        response = it
//                    }
//                    response
//                }
//            }.awaitAll()
//                // - - -
//                // - 2 -
//                // - 2 3
//                // 1 2 3
//                .filterNotNull()
//
//            onComplete(responses)
//        }
    }
}

fun main() {
    val batchHelper = BatchHelper(LegacyApiImpl())

    val requests = listOf("1", "2", "3", "4", "5").map { Request(it) }

    println("Execution order:")
    requests.forEach { println("Sent ${it.id}") }

    batchHelper.doRequests(requests) { responses ->
        println("\nFinal order:")
        responses.forEach { println(it.id) } // 1 2 3 4 5
    }

    // Ожидаем завершения корутин
    Thread.sleep(2000)
}


//Мы хотим вызвать такси своему другу.
//Для этого нужно реализовать нечёткий поиск по списку контактов пользователя.
//
//Условия задачи:
//
//Входные данные: строка поиска (input) и список контактов (dataSet).
//Нужно найти в списке контактов такие строки, которые максимально похожи на строку поиска.
//Похожесть определяется на основе частичного совпадения символов и подстрок (нечёткий поиск).
//
//Примеры:
//
//Контакты: "John Smith", "Mike Marley", "Hillary Cosplay", "Mark Johnson"
//Ищем: "Joh"
//Результат: \["John Smith", "Mark Johnson"\]
//
//Контакты: "John Smith", "Mike Marley", "Hillary Cosplay", "Mark Johnson"
//Ищем: "rk John"
//Результат: \["Mark Johnson"\]
//
//Контакты: "John Smith", "Mike Marley", "Hillary Cosplay", "Mark Johnson", "Kamil Englo", "Mjohn Kengsman", "Mjohn Keng"
//Ищем: "keng"
//Результат: \["Kamil Englo", "Mjohn Kengsman", "Mjohn Keng"\]
//

fun search(input: String, dataSet: List<String>): List<String> {
    val result: ArrayList<String> = ArrayList()
    val inputLowCase = input.trim().lowercase()
    dataSet.forEach { person ->
        val personLowCase = person.trim().lowercase()
        var resultLength: Int = 0
        for (i in 0..<personLowCase.length) {
            if (personLowCase[i] == (inputLowCase[resultLength])) {
                resultLength++
            }
        }
        if (resultLength == inputLowCase.length) {
            result.add(person)
        }
    }
    return result
}

//------
//Задача 2
//
//Дан массив целых чисел x длиной n.
//Массив упорядочен по возрастанию.
//Необходимо написать функцию, которая из этого
//массива получит массив квадратов чисел, упорядоченный по возрастанию.
//
//Пример: [-3, 2, 4] -> [4, 9, 16]
//[] -> []
//[-3, -2, -1] -> [1, 4, 9]


fun doSquare(input: IntArray): IntArray {
    val result = IntArray(input.size)
    val square = input.map{it*it}


    var start: Int = 0
    var end: Int = input.size - 1
    var current: Int = input.size - 1

    while (start <= end) {
        if (square[start] > square[end]) {
            result[current] = square[start]
            start++
        } else {
            result[current] = square[end]
            end--
        }
        current--
    }

    return result
}

//Задача 3
//Записка для выкупа
//Загадочник захотел сочинить очередную записку для выкупа
//У него есть лишь один журнал и желаемая записка
//
//Нужно написать функцию, которая принимает две строки ransomNote и magazine.
//Функция вернёт true, если из букв в строке magazine можно составить строку ransomeNote
//Иначе вернёт false
//
//Ограничения:
//- Каждая буква из magazine может быть использована только один раз
//- 1 <= ransomNote. length, magazine.length <= 105
//- ransomeNote и magazine содержат только латинские символы в lowercase. Пробелов нет.

//fun canConstruct(ransomNote: String, magazine: String): Boolean {
//
//}

