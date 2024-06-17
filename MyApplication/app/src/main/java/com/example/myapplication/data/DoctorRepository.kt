package com.example.myapplication.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class DoctorRepository @Inject constructor() {
    suspend fun getAllDoctors(isAdultBranch: Boolean): List<String> {
        delay(1000)
        return if (isAdultBranch) listOf(
            "Терапевт",
            "Доктор",
            "Художник",
            "Учительница",
            "Космонавт"
        )
        else listOf("Детский доктор", "Хирург", "Мама", "Математичка", "Пожарный")
    }

    suspend fun getDoctorServices(isAdultBranch: Boolean, doctor: String): List<DoctorServices> {
        val services: String
        val price: Int
        if (isAdultBranch) {
            when (doctor) {
                "Терапевт" -> {
                    services = "Терапевт чтото сделает"
                    price = 129
                }

                "Доктор" -> {
                    services = "Доктор полечит зубы"
                    price = 2999
                }

                "Художник" -> {
                    services = "Нарисует вам портрет"
                    price = 679
                }

                "Учительница" -> {
                    services = "Поставит вам двойку"
                    price = 0
                }

                "Космонавт" -> {
                    services = "Полетит в космос"
                    price = 2_987_127
                }

                else -> {
                    services = "мы не нашли такого доктора"
                    price = -1
                }
            }
        } else {
            when (doctor) {
                "Детский доктор" -> {
                    services = "Пропишет больничный"
                    price = 100
                }

                "Хирург" -> {
                    services = "Вправит коленку"
                    price = 2649
                }

                "Мама" -> {
                    services = "Приготовит борщ"
                    price = 300
                }

                "Математичка" -> {
                    services = "Сложит 2 и 2"
                    price = 5
                }

                "Пожарный" -> {
                    services = "Птушит пажар"
                    price = 30000
                }
                else -> {
                    services = "мы не нашли такого доктора"
                    price = -1
                }
            }
        }
        val listOfServices = List<DoctorServices>(Random.nextInt(1, 5)) { DoctorServices(services, price, "https://cdn-icons-png.flaticon.com/512/5604/5604732.png") }
        return listOfServices
    }
}