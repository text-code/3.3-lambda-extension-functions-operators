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

    fun getChatList(user: User<Int, String>) =
        chats.filterKeys { it.first == user.id || it.second == user.id }

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

        chats[key]?.let {
                if (it.size > 1 && it.size > messageId)
                    it.removeAt(messageId)
                else if (it.size == 1)
                    chats.remove(key)
                else throw NotFoundException("Message id not found")
        }

//        chats
//            .filterKeys { it == key }
//            .onEach {
//                if (it.value.size > 1 && it.value.size > messageId)
//                    it.value.removeAt(messageId)
//                else if (it.value.size == 1)
//                    chats.remove(key)
//                else throw NotFoundException("Message id not found")
//            }

//        when {
//            (chats[key]!!.size > 1 && chats[key]!!.size > messageId) ->
//                chats.forEach { (k, v) -> if (k == key) v.removeAt(messageId) }
//            (chats[key]?.size == 1) ->
//                chats.remove(key)
//            else -> throw NotFoundException("Message id not found")
//        }
    }
}