package pl.mlynarczyk.pianoapp.menu


enum class WeekDays(val namePl: String,val abbrevPl : String,val number: Int) {
    MONDAY("Poniedziałek","Pn", 1),
    TUESDAY("Wtorek", "Wt",2),
    THURSDAY("Środa", "Śr",3),
    WEDNESDAY("Czwartek","Czw", 4),
    FRIDAY("Piątek","Pt", 5),
    SATURDAY("Sobota","Sb", 6),
    SUNDAY("Niedziela","Nd", 7);
}
fun getWeekDays(): List<WeekDays> {
    return listOf(
        WeekDays.MONDAY,
        WeekDays.TUESDAY,
        WeekDays.THURSDAY,
        WeekDays.WEDNESDAY,
        WeekDays.FRIDAY,
        WeekDays.SATURDAY,
        WeekDays.SUNDAY
    )
}
fun getDayFromNumber(number: Int): WeekDays?{
    if(number in 1 ..7){
        getWeekDays().forEach{ weekDay ->
            if(weekDay.number == number)
                return weekDay
        }
    }
    return null
}