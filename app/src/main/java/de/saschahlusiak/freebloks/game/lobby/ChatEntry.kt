package de.saschahlusiak.freebloks.game.lobby

import android.content.Context
import android.graphics.Color
import de.saschahlusiak.freebloks.model.GameMode
import de.saschahlusiak.freebloks.model.StoneColor
import de.saschahlusiak.freebloks.model.colorOf
import java.io.Serializable

class ChatEntry(
    // the sending client, or null if server message
    private val client: Int?,
    // the player this message is about
    private val player: Int?,
    // the message
    private val text: String,
    // the name of the player, or null if generic message
    private val name: String?
) : Serializable {
    fun isServerMessage() = (client == null)

    fun getColor(context: Context, gameMode: GameMode): Int {
        return if (player == null) {
            if (client == null) Color.WHITE else extraColors[client % extraColors.size]
        } else {
            val playerColor = gameMode.colorOf(player)

            context.resources.getColor(playerColor.foregroundColorId)
        }
    }

    override fun toString(): String {
        return when (client) {
            null -> "* $text"
            else -> "$name: $text"
        }
    }

    companion object {
        // some additional colors for the clients that do not have players
        private val extraColors = intArrayOf(
            Color.CYAN,
            Color.MAGENTA,
            Color.LTGRAY,
            Color.WHITE
        )

        fun clientMessage(client: Int, player: Int, text: String, name: String): ChatEntry {
            return ChatEntry(client, if (player < 0) null else player, text, name)
        }

        fun serverMessage(player: Int, text: String, name: String): ChatEntry {
            return ChatEntry(null, player, text, name)
        }

        fun genericMessage(text: String): ChatEntry {
            return ChatEntry(null, null, text, null)
        }
    }
}