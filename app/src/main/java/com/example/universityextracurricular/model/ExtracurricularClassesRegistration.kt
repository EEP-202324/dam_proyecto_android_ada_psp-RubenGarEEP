import com.example.universityextracurricular.model.Deporte

data class ExtracurricularClassesRegistration(
    val nombre: String,
    val edad: Int,
    val deporte: Deporte,
    val horario: String,
)

data class Deporte(
    val id: Long? = null,
    val nombre: String
)
