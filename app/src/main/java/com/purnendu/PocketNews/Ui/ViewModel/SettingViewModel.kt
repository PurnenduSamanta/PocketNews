package com.purnendu.PocketNews.Ui.ViewModel

import androidx.lifecycle.ViewModel
import com.purnendu.PocketNews.Repository

class SettingViewModel(private val repository: Repository) : ViewModel() {

    var countryCode: String = ""

    var isSpinnerFirstCall = true

    fun changeSpinnerStatus()
    {
        isSpinnerFirstCall=false
    }

    private suspend fun setCountry(countryCode: String): Boolean = repository.setCountryCode(countryCode)

    suspend fun clearBookmarks() = repository.clearBookmark()


    suspend fun clearNews() = repository.clearAllNews()

    fun setNightModeState(state: Boolean): Boolean = repository.setNightMode(state)


    fun setJavaScriptEnablingState(state: Boolean): Boolean = repository.setJavaScriptStatus(state)

    val country = arrayOf(
        "Argentina",
        "Austria",
        "Australia",
        "Belgium",
        "Bulgaria",
        "Brazil",
        "Canada",
        "Colombia",
        "Cuba",
        "Czech Republic",
        "Egypt",
        "France",
        "Germany",
        "Great Britain(UK)",
        "Greece",
        "Hong kong",
        "Hungary",
        "Indonesia",
        "Ireland",
        "Israel",
        "India",
        "Italy",
        "Japan",
        "Korea(South)",
        "Lithuania",
        "Latvia",
        "Morocco",
        "Mexico",
        "Malaysia",
        "Nigeria",
        "Netherlands",
        "Norway",
        "New Zealand",
        "Philippines",
        "Poland",
        "Portugal",
        "Romania",
        "Russian Federation",
        "Serbia",
        "Saudi Arabia",
        "Sweden",
        "Singapore",
        "Slovenia",
        "Slovakia",
        "Switzerland",
        "Thailand",
        "Turkey",
        "Taiwan",
        "Ukraine",
        "United States",
        "Venezuela",
        "Zambia"
    )

    suspend fun setCountryCode( position: Int):Boolean {
        when (country[position]) {
            "Argentina" -> this.countryCode = "ar"
            "Austria" -> this.countryCode = "at"
            "Australia" -> this.countryCode = "au"
            "Belgium" -> this.countryCode = "be"
            "Bulgaria" -> this.countryCode = "bg"
            "Brazil" -> this.countryCode = "br"
            "Canada" -> this.countryCode = "ca"
            "Colombia" -> this.countryCode = "co"
            "Cuba" -> this.countryCode = "cu"
            "Czech Republic" -> this.countryCode = "cz"
            "Egypt" -> this.countryCode = "eg"
            "France" -> this.countryCode = "fr"
            "Germany" -> this.countryCode = "de"
            "Great Britain(UK)" -> this.countryCode = "gb"
            "Greece" -> this.countryCode = "gr"
            "Hong kong" -> this.countryCode = "hk"
            "Hungary" -> this.countryCode = "hu"
            "Indonesia" -> this.countryCode = "id"
            "Ireland" -> this.countryCode = "ie"
            "Israel" -> this.countryCode = "il"
            "India" -> this.countryCode = "in"
            "Italy" -> this.countryCode = "it"
            "Japan" -> this.countryCode = "jp"
            "Korea(South)" -> this.countryCode = "kr"
            "Lithuania" -> this.countryCode = "lt"
            "Latvia" -> this.countryCode = "lv"
            "Morocco" -> this.countryCode = "ma"
            "Mexico" -> this.countryCode = "mx"
            "Malaysia" -> this.countryCode = "my"
            "Nigeria" -> this.countryCode = "ng"
            "Netherlands" -> this.countryCode = "nl"
            "Norway" -> this.countryCode = "no"
            "New Zealand" -> this.countryCode = "nz"
            "Philippines" -> this.countryCode = "ph"
            "Poland" -> this.countryCode = "pl"
            "Portugal" -> this.countryCode = "pt"
            "Romania" -> this.countryCode = "ro"
            "Russian Federation" -> this.countryCode = "ru"
            "Serbia" -> this.countryCode = "rs"
            "Saudi Arabia" -> this.countryCode = "sa"
            "Sweden" -> this.countryCode = "se"
            "Singapore" -> this.countryCode = "sg"
            "Slovenia" -> this.countryCode = "si"
            "Slovakia" -> this.countryCode = "sk"
            "Switzerland" -> this.countryCode = "ch"
            "Thailand" -> this.countryCode = "th"
            "Turkey" -> this.countryCode = "tr"
            "Taiwan" -> this.countryCode = "tw"
            "Ukraine" -> this.countryCode = "ua"
            "United States" -> this.countryCode = "us"
            "Venezuela" -> this.countryCode = "ve"
            "Zambia" -> this.countryCode = "za"
        }
        return setCountry(countryCode)
    }
}