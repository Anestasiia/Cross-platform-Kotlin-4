class BirthDate {
    val firstName: String
        get() = field
    val lastName: String
        get() = field
    val phoneNumber: String
        get() = field
    val day: Int
        get() = field
    val month: Int
        get() = field
    val year: Int
        get() = field

    constructor(firstName: String, lastName: String, phoneNumber: String, date: String) {
        this.firstName = firstName
        this.lastName = lastName
        this.phoneNumber = phoneNumber
        this.day = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2].toInt()
        this.month = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].toInt()
        this.year = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toInt()
    }

    private val birthDate: String
        get() = Formatter.formatDate(year, month, day)

    override fun toString(): String {
        return "$firstName, $lastName, $phoneNumber, $birthDate"
    }
}