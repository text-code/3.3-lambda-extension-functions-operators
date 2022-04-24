object ChatService {
    var chats = mutableMapOf<Pair<Int, Int>, MutableList<Message<Int, String>>>()

    fun addChat(writeId: Int, readId: Int, text: String) {
        val key = if (writeId < readId) Pair(writeId, readId) else Pair(readId, writeId)
        val chat = mutableListOf(Message(writeId, text))
        chats[key] = chat
    }

    fun removeChat(firstId: Int, secondId: Int) {
        val key = if (firstId < secondId) Pair(firstId, secondId) else Pair(firstId, secondId)
        chats.forEach { chat -> if (chat.key == key) chats.remove(key) }
    }

//    fun getChat(firstId: Int, secondId: Int): MutableList<Message<Int, String>>? {
//        val key = if (firstId < secondId) Pair(firstId, secondId) else Pair(firstId, secondId)
//        return chats[key]
//    }

    fun getChatList(user: User<Int, String>): Map<Pair<Int, Int>, MutableList<Message<Int, String>>> {
        return chats.filterKeys { key -> key.first == user.id || key.second == user.id }
    }

    fun addMessage(writeId: Int, readId: Int, text: String) {
        val key = if (writeId < readId) Pair(writeId, readId) else Pair(readId, writeId)

        if (chats.containsKey(key)) {
            chats.forEach { (k, v) -> if (k == key) v.add(Message(writeId, text)) }
        } else {
            addChat(writeId, readId, text)
        }
    }

    fun removeMessage(firstId: Int, secondId: Int, messageId: Int) {
        val key = if (firstId < secondId) Pair(firstId, secondId) else Pair(firstId, secondId)
        when {
            (chats[key]?.size!! > 1 && chats[key]?.size!! > messageId) ->
                chats.forEach { (k, v) -> if (k == key) v.removeAt(messageId) }
            (chats[key]?.size == 1) ->
                chats.remove(key)
            else -> throw NotFoundException("Message id not found")
        }
    }

//    fun getMessage(firstId: Int, secondId: Int, idMessage: Int): Message<Int, String> {
//        val key = if (firstId < secondId) Pair(firstId, secondId) else Pair(firstId, secondId)
//        return chats[key]?.get(idMessage) ?: throw NotFoundException("Message id not found")
//    }

//    fun getMessageList(firstId: Int, secondId: Int): MutableList<Message<Int, String>>? {
//        val key = if (firstId < secondId) Pair(firstId, secondId) else Pair(firstId, secondId)
//        return chats[key]
//    }
}