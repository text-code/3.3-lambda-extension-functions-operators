import UserService.addChat
import UserService.addMessage
import UserService.getChats
import UserService.getMessages
import UserService.removeChat
import UserService.removeMessage
import org.junit.After
import org.junit.Test

import org.junit.Assert.*

class UserServiceTest {

    @After
    fun tearDown() {
        ChatService.chats.clear()
    }

    @Test
    fun getChats() {
        val user = User(1, "user1")
        val userSecond = User(2, "user2")
        val userThird = User(3, "user3")
        user.addMessage(userSecond.id, "1 2")
        userSecond.addMessage(user.id, "2 1")
        user.addMessage(userThird.id, "1 3")

        val expected = "Чат: (1, 2) непрочитанных сообщений: 2\nЧат: (1, 3) непрочитанных сообщений: 1"

        val actual = user.getChats()
        assertEquals(expected, actual)
    }

    @Test
    fun getMessages() {
        val user = User(1, "user1")
        val userSecond = User(2, "user2")
        user.addMessage(userSecond.id, "(1 2) 1")
        userSecond.addMessage(user.id, "(2 1) 1")
        user.addMessage(userSecond.id, "(1 2) 2")
        userSecond.addMessage(user.id, "(2 1) 2")

        val expected: ArrayList<Message<Int, String>> = arrayListOf(
            Message(user.id, "(1 2) 1"),
            Message(userSecond.id, "(2 1) 1"),
            Message(user.id, "(1 2) 2"),
            Message(userSecond.id, "(2 1) 2")
        )

        val actual = user.getMessages(userSecond.id, 0, 4)
        assertEquals(expected.size, actual.size)
    }

    @Test
    fun addMessage() {
        val user = User(1, "user1")
        val userSecond = User(2, "user2")
        user.addMessage(userSecond.id, "test")

        val expected = "test"

        val actual = ChatService.chats[Pair(user.id, userSecond.id)]?.get(0)

        if (actual != null) {
            assertEquals(expected, actual.text)
        }
    }

    @Test
    fun removeMessage() {
        val user = User(1, "user1")
        val userSecond = User(2, "user2")
        user.addMessage(userSecond.id, "(1 2) 1")
        userSecond.addMessage(user.id, "(2 1) 1")

        val expected = 1

        user.removeMessage(userSecond.id, 0)
        val actual = ChatService.chats[Pair(user.id, userSecond.id)]?.size

        assertEquals(expected, actual)

        user.removeMessage(userSecond.id, 0)
        val actualSecond = ChatService.chats[Pair(user.id, userSecond.id)]

        assert(actualSecond == null)
    }

    @Test
    fun addChat() {
        val user = User(1, "user1")
        val userSecond = User(2, "user2")
        user.addChat(userSecond.id, "test")

        val expected = 1

        val actual = ChatService.chats[Pair(user.id, userSecond.id)]?.size

        assertEquals(expected, actual)
    }

    @Test
    fun removeChat() {
        val user = User(1, "user1")
        val userSecond = User(2, "user2")
        user.addChat(userSecond.id, "test")

        user.removeChat(userSecond.id)
        val actual = ChatService.chats[Pair(user.id, userSecond.id)]
        assert(actual == null)
    }
}