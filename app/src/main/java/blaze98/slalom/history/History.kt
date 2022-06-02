package blaze98.slalom.history

import blaze98.slalom.game.GameStatus
import java.time.LocalDateTime

data class History(val name: String, val status: GameStatus, val date: LocalDateTime)