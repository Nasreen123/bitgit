import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestHash {

    @Test
    fun testHash() {
        val input = "12345"
        val expectedOutput = "8cb2237d0679ca88db6464eac60da96345513964"
        assertThat(Hasher.hash(input)).isEqualTo(expectedOutput)
    }

}