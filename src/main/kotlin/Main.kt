import kotlin.Pair

data class Chat<A, B>(val id: A, val text: B) {
    override fun toString(): String {
        return "Пользователь: $id\n $text\n"
    }
}

object ChatService {
    private var chats = mutableMapOf<Pair<Int, Int>, MutableList<Chat<Int, String>>>()

    fun addChat(writeId: Int, readId: Int, text: String = "Создает чат") {
        val chatId = if (writeId < readId) Pair(writeId, readId) else Pair(readId, writeId)
        val chat = mutableListOf(Chat(writeId, text))

        chats[chatId] = chat
    }

    fun getChat(firstId: Int, secondId: Int): MutableList<Chat<Int, String>>? {
        val chatId = if (firstId < secondId) Pair(firstId, secondId) else Pair(firstId, secondId)
        return chats[chatId]
    }

    fun getChatList() {
        chats.forEach { (chat, message) -> println("Chat: $chat\nMessage: $message") }
    }

    fun addMessage(writeId: Int, readId: Int, text: String) {
        val chatId = if (writeId < readId) Pair(writeId, readId) else Pair(readId, writeId)
        val message = Chat(writeId, text)
        chats[chatId]?.add(message)
    }
}

fun main() {
    ChatService.addChat(1, 2, "Hi")
    ChatService.addMessage(2, 1, "test")

    ChatService.addMessage(2, 1, "Hi")

//    val firstChat = ChatService.getChat(1, 2)
//    println(firstChat)

    ChatService.getChatList()
}