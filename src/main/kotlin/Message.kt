data class Message<A, B>(
    val id: A,
    val text: B,
    var readability: Boolean = false,
) {
    override fun toString(): String {
        return "Пользователь: $id\n $text\n"
    }
}