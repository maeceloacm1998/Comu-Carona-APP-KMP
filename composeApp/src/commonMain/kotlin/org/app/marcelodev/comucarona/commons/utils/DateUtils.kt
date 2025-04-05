package org.app.marcelodev.comucarona.commons.utils

object DateUtils {
    /**
     * Valida se uma data está no formato dd/MM/yyyy e se os valores estão dentro dos limites.
     * @param date String contendo a data no formato dd/MM/yyyy
     * @return Boolean indicando se a data é válida
     */
    fun isValidBirthDate(date: String): Boolean {
        // Verifica se o formato básico está correto (dd/MM/yyyy)
        val regex = Regex("^\\d{2}/\\d{2}/\\d{4}$")
        if (!regex.matches(date)) return false

        // Extrai os componentes da data
        val components = date.split("/")
        val day = components[0].toInt()
        val month = components[1].toInt()
        val year = components[2].toInt()

        // Verifica limites básicos
        if (day !in 1..31) return false
        if (month !in 1..12) return false

        // Verifica dias por mês (incluindo ano bissexto)
        val daysInMonth = when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        return day <= daysInMonth
    }

    /**
     * Verifica se um ano é bissexto.
     * @param year Ano a ser verificado
     * @return Boolean indicando se o ano é bissexto
     */
    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    /**
     * Função que formata uma data de nascimento para o formato esperado pela API.
     * @param birthDate String contendo a data no formato dd/MM/yyyy
     * @return String contendo a data no formato yyyy-MM-dd, ou null se inválida
     */
    fun formatBirthDate(birthDate: String): String? {
        if (!isValidBirthDate(birthDate)) return null

        val components = birthDate.split("/")
        return "${components[2]}-${components[1]}-${components[0]}"
    }
}