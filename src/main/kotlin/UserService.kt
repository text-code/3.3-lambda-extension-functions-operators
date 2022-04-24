import ChatService.chats
import ChatService.getChatList
import ChatService.addMessage
import ChatService.removeMessage
import ChatService.addChat
import ChatService.removeChat

object UserService {
    fun User<Int, String>.getChats(): MutableMap<Pair<Int, Int>, Int> {
        val chats = getChatList(this)

        val key = mutableMapOf<Pair<Int, Int>, Int>()
        var counter = 0

        for (chat in chats) {
            for (message in chat.value) {
                if (!message.readability) counter++
            }
            key[chat.key] = counter
            counter = 0
        }
        return key

//        val result = chat.values.forEach { value -> value.forEach { if (!it.readability) counter++ } }
//        return "Количество чатов с непрочитанными сообщениями: $chatIndex\nКоличество непрочитанных сообщений: $counter"

//        return chat.values.filter { value -> value.any { !it.readability } }
    }

    fun User<Int, String>.getMessagesIsChat(
        secondId: Int,
        idLastReadMessage: Int,
        amountMessage: Int
    ): List<Message<Int, String>> {
        val chatList = chats[Pair(this.id, secondId)]
        val messageList = chatList!!.filterIndexed { index, message ->
            index in idLastReadMessage until amountMessage + idLastReadMessage
        }
        messageList.forEach { it.readability = true }
        return messageList
    }

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