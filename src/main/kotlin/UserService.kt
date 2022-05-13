import ChatService.chats
import ChatService.getChatList
import ChatService.addMessage
import ChatService.removeMessage
import ChatService.addChat
import ChatService.removeChat
import UserService.addMessage
import UserService.getChats
import UserService.getMessages

object UserService {
    fun User<Int, String>.getChats() =
        getChatList(this)
            .filterValues { (message) -> !message.readability }
            .toList()
            .joinToString("\n") { (key, value) ->
                "Чат: $key непрочитанных сообщений: ${value.size}"
            }

    fun User<Int, String>.getMessages(
        secondId: Int,
        idLastReadMessage: Int,
        amountMessage: Int
    ): List<Message<Int, String>> =
        chats.getOrDefault(Pair(id, secondId), mutableListOf())
            .asSequence()
            .drop(idLastReadMessage)
            .take(amountMessage)
            .onEach { it.readability = true }
            .toList()

    fun User<Int, String>.addMessage(secondUserId: Int, text: String) {
        addMessage(id, secondUserId, text)
    }

    fun User<Int, String>.removeMessage(secondUserId: Int, messageId: Int) {
        removeMessage(id, secondUserId, messageId)
    }

    fun User<Int, String>.addChat(secondUserId: Int, message: String) {
        addChat(id, secondUserId, message)
    }

    fun User<Int, String>.removeChat(secondUserId: Int) {
        removeChat(id, secondUserId)
    }
}


fun main() {
    val user = User(1, "user1")
    val userSecond = User(2, "user2")
    val userThread = User(3, "user3")
    user.addMessage(userSecond.id, "(1 2) 1")
    userSecond.addMessage(user.id, "(2 1) 1")
    user.addMessage(userSecond.id, "(1 2) 2")
    userSecond.addMessage(user.id, "(2 1) 2")
    userThread.addMessage(user.id, "(3 1) 1")

//    println(user.getMessages(userSecond.id, 0, 2))

    println(user.getChats())
}