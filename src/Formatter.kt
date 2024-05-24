import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Formatter {

    companion object {
        var pattern: String = "yyyy-MM-dd"

        fun formatDate(year: Int, month: Int, day: Int): String {
            val formatter = SimpleDateFormat(pattern)
            return formatter.format(GregorianCalendar(year, month - 1, day).time)
        }

        fun isValidDate(date: String): Boolean {
            val regex = "^\\d{4}-\\d{2}-\\d{2}$"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(date)
            return matcher.matches()
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            val regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|(\\d{3})\\d{3}-?\\d{4}"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(phoneNumber)
            return matcher.matches()
        }
    }
}