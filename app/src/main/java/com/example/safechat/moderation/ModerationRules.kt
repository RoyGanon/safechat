package com.example.safechat.moderation

object ModerationRules {

    private val badPhrases = listOf(
        "idiot",
        "stupid",
        "hate",
        "shut up",
        "ugly",
        "loser",
        "mean",
        "dumb",
        "moron"
    )

    private fun normalize(text: String): String {
        return text
            .lowercase()
            .replace(Regex("[^a-zA-Z\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    fun shouldWarn(text: String): Boolean {
        val normalized = normalize(text)
        if (normalized.isBlank()) return false

        return badPhrases.any { phrase ->
            normalized.contains(phrase)
        }
    }

    fun riskScore(text: String): Int {
        val original = text.trim()
        val normalized = normalize(text)

        if (normalized.isBlank()) return 0

        var score = 0

        if (badPhrases.any { normalized.contains(it) }) {
            score += 80
        }

        if (original.count { it == '!' } >= 2) {
            score += 10
        }

        val lettersOnly = original.filter { it.isLetter() }
        if (lettersOnly.isNotBlank() && lettersOnly.length >= 4 && lettersOnly == lettersOnly.uppercase()) {
            score += 10
        }

        return score.coerceAtMost(100)
    }
}