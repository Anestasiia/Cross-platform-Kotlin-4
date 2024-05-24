import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


val birthDates: MutableList<BirthDate> = mutableListOf()

fun main() {

    readFromFile()

    while (true) {
        println("Виберіть опцію:")
        println("1. Показати всі дні народження")
        println("2. Показати всі дні народження за ім'ям")
        println("3. Показати всі дні народження за прізвищем")
        println("4. Показати всі дні народження за місяцем")
        println("5. Ввести новий запис про день народження ")
        println("6. Видалити всі дні народження за ім'ям")
        println("7. Видалити всі дні народження за прізвищем")
        println("8. Оновити запис про дні народження за прізвищем")
        println("9. Завантажити всі дні народження з файлу")
        println("10. Зберегти всі дні народження у файл")
        println("11. Вихід")

        print("Введіть операцію: ")
        val choice: Int = readln().toInt()

        when (choice) {
            1 -> getAll()
            2 -> getByFirstName()
            3 -> getByLastName()
            4 -> getByMonth()
            5 -> post()
            6 -> deleteByFirstName()
            7 -> deleteByLastName()
            8 -> putByLastName()
            9 -> readFromFile()
            10 -> writeToFile()
            11 -> {
                writeToFile()
                break
            }
            else -> println("Некоректний вибір. Спробуйте знову.")
        }
    }
}

private fun writeToFile() {
    val filename = "BirthDates.txt"

    try {
        val writer = BufferedWriter(FileWriter(filename))

        for (birthDate in birthDates) {
            writer.write(birthDate.toString())
            writer.newLine()
        }
        writer.close()
        println("Дані збережені у файл $filename")
    } catch (e: IOException) {
        System.err.println("Виникла помилка при записі даних про оплати у файл $filename")
    }
}

private fun readFromFile() {
    birthDates.clear()
    val fileName = "BirthDates.txt"

    try {
        val lines: List<String> = File(fileName).readLines()
        lines.forEachIndexed { _, line ->
            val parts = line.split(", ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (parts.size == 4) {
                val firstName = parts[0]
                val lastName = parts[1]
                val phoneNumber = parts[2]
                val date = parts[3]

                val birthDate = BirthDate(firstName, lastName, phoneNumber, date)
                birthDates.add(birthDate)
            }
        }
    } catch (e: IOException) {
        System.err.println("Виникла помилка при зчитувані даних про оплати з файлу $fileName")
    }
}

private fun getAll() {
    println("Дані про дні народження:")

    var found = false

    var i = 0

    while (i < birthDates.size){
        println(birthDates[i])
        found = true
        i++
    }

    if (!found) {
        println("Немає записів про дні народження.")
    }
}

private fun getByFirstName() {
    println("Введіть ім'я:")
    val firstName = readlnOrNull()
    var found = false

    var i = 0

    while (i < birthDates.size){
        if (birthDates[i].firstName == firstName) {
            println(birthDates[i])
            found = true
        }
        i++
    }

    if (!found) {
        println("Немає записів про дні народження за вказаним ім'ям.")
    }
}

private fun getByLastName() {
    println("Введіть прізвище:")
    val lastName = readln()

    var found = false

    var i = 0

    while (i < birthDates.size){
        if (birthDates[i].lastName == lastName) {
            println(birthDates[i])
            found = true
        }
        i++
    }

    if (!found) {
        println("Немає записів про дні народження за вказаним прізвищем.")
    }
}

private fun getByMonth() {
    println("Введіть місяць:")
    val month = readln().toInt()

    var found = false

    var i = 0

    while (i < birthDates.size){
        if (birthDates[i].month == month) {
            println(birthDates[i])
            found = true
        }
        i++
    }

    if (!found) {
        println("Немає записів про дні народження у заданому місяці.")
    }
}

private fun post() {
    println("Введіть ім'я:")
    val firstName = readln()

    println("Введіть прізвище:")
    val lastName = readln()

    var phoneNumber: String
    var date: String

    do {
        println("Введіть номер телефону ((***)-***-****)")
        phoneNumber = readln()
    } while (Formatter.isValidPhoneNumber(phoneNumber))

    do {
        println("Введіть дату (формат: рррр-мм-дд):")
        date = readln()
        if (Formatter.isValidDate(date)) {
            try {
                val validator = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val dateValidation = LocalDate.parse( date , validator)
                if (Period.between(dateValidation, LocalDate.now()).years in 1..99) {
                    break
                } else {
                    println("Дата народження мусить бути не пізніше поточного часу та не більше 100 років тому")
                }
            } catch (e: DateTimeParseException) {
                println("Дата не в правильному форматі")
            }
        }
    } while (true)

    val birthDate = BirthDate(firstName, lastName, phoneNumber, date)
    birthDates.add(birthDate)
}

private fun deleteByFirstName() {
    println("Введіть ім'я, за яким потрібно видалити дні народження:")
    val firstName = readln()

    var found = false

    var i = 0

    while (i < birthDates.size){
        if (birthDates[i].firstName == firstName) {
            birthDates.remove(birthDates[i])
            found = true
        }
        else{
            i++
        }
    }

    if (!found) {
        println("Не було знайдено днів народження з заданим ім'ям")
    }
}

private fun deleteByLastName() {
    println("Введіть прізвище, за яким потрібно видалити дні народження:")
    val lastName = readln()

    var found = false

    var i = 0

    while (i < birthDates.size){
        if (birthDates[i].lastName == lastName) {
            birthDates.remove(birthDates[i])
            found = true
        }
        else{
            i++
        }
    }

    if (!found) {
        println("Не було знайдено днів народження з заданим прізвищем")
    }
}

private fun putByLastName() {
    println("Введіть прізвище, за яким проводити пошук:")
    val lastNameToChange = readln()

    println("Введіть ім'я:")
    val firstName = readln()

    println("Введіть прізвище:")
    val lastName = readln()

    var phoneNumber: String
    var date: String

    do {
        println("Введіть номер телефону ((***)-***-****)")
        phoneNumber = readln()
    } while (Formatter.isValidPhoneNumber(phoneNumber))

    do {
        println("Введіть дату (формат: рррр-мм-дд):")
        date = readln()
        if (Formatter.isValidDate(date)) {
            try {
                val validator = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val dateValidation = LocalDate.parse( date , validator)
                if (Period.between(dateValidation, LocalDate.now()).years in 1..99) {
                    break
                } else {
                    println("Дата народження мусить бути не пізніше поточного часу та не більше 100 років тому")
                }
            } catch (e: DateTimeParseException) {
                println("Дата не в правильному форматі")
            }
        }
    } while (true)

    val birthDateToPut = BirthDate(firstName, lastName, phoneNumber, date)

    var found = false

    var i = 0

    while (i < birthDates.size){
        if (birthDates[i].lastName == lastNameToChange) {
            birthDates.remove(birthDates[i])
            birthDates.addFirst(birthDateToPut)
            found = true
        }
        i++
    }

    if (!found) {
        println("Не було знайдено дні народження з заданим прізвищем")
    }
}