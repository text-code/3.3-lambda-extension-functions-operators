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
    fun User<Int, String>.getChats(): String {
//        val chats = getChatList(this)

//        val key = mutableMapOf<Pair<Int, Int>, Int>()
//        var counter = 0
//
//        for (chat in chats) {
//            for (message in chat.value) {
//                if (!message.readability) counter++
//            }
//            key[chat.key] = counter
//            counter = 0
//        }
//        return key

        return getChatList(this)
            .filterValues { (message) -> !message.readability }
            .toList()
            .joinToString("\n") { (key, value) ->
                "Чат: $key непрочитанных сообщений: ${value.size}"
            }
    }

    fun User<Int, String>.getMessages(
        secondId: Int,
        idLastReadMessage: Int,
        amountMessage: Int
    ): List<Message<Int, String>> =
        chats[Pair(id, secondId)]!!
            .asSequence()
            .drop(idLastReadMessage)
            .take(amountMessage)
            .toList()


//        val chatList = chats[Pair(this.id, secondId)]
//        val messageList = chatList!!.filterIndexed { index, message ->
//            index in idLastReadMessage until amountMessage + idLastReadMessage
//        }
//        messageList.forEach { it.readability = true }
//        return messageList


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


//fun main() {
//    val user = User(1, "user1")
//    val userSecond = User(2, "user2")
//    val userThread = User(3, "user3")
//    user.addMessage(userSecond.id, "(1 2) 1")
//    userSecond.addMessage(user.id, "(2 1) 1")
//    user.addMessage(userSecond.id, "(1 2) 2")
//    userSecond.addMessage(user.id, "(2 1) 2")
//    userThread.addMessage(user.id, "(3 1) 1")
//
//    println(user.getMessages(userSecond.id, 0, 2))
//
//    val result = user.getChats()
//    println(result)
//}