import com.plcoding.spring_boot_crash_course.database.model.Note
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*
import java.time.Instant
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/notes")
class NoteController(
    private val repository: NoteRepository,
) {

    data class NoteRequest(
        val id: String?,
        val title: String,
        val content: String,
        val color: Long,
        val ownerId: String
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant,
    )

    private val logger = LoggerFactory.getLogger(NoteController::class.java)

    @PostMapping
    fun save(
         @RequestBody body: NoteRequest
    ): NoteResponse {
        logger.info("Saving note with title: ${body.title}")
        val note = repository.save(
            Note(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                createdAt = Instant.now(),
                ownerId = ObjectId(body.ownerId)
            )
        )

        logger.info("Note saved with ID: ${note.id}")
        return note.toResponse()
    }

    @GetMapping
    fun findByOwnerId(
        @RequestParam(required = false) ownerId: String?
    ): List<NoteResponse> {
        logger.info("Fetching notes for ownerId: $ownerId")
        return if (ownerId != null) {
            repository.findByOwnerId(ObjectId(ownerId)).map { it.toResponse() }
        } else {
            repository.findAll().map { it.toResponse() }
        }
    }

    @GetMapping("/test")
    fun testEndpoint(): String {
        return "Controller is working"
    }
}

private fun Note.toResponse(): NoteController.NoteResponse {
    return NoteController.NoteResponse(
        id = id.toHexString(),
        title = title,
        content = content,
        color = color,
        createdAt = createdAt
    )
}