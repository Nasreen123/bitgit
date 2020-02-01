import java.security.MessageDigest

object Hasher {

    fun hash(input: String): String {
        val bytes = input.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-1")

        return messageDigest.digest(bytes)
                .joinToString("") { "%02x".format(it) }
    }

}