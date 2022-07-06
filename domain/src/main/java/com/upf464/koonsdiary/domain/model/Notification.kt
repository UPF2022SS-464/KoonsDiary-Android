package com.upf464.koonsdiary.domain.model

sealed interface Notification {

    data class GroupInvite(
        val group: ShareGroup,
        val isAccepted: Boolean?,
    ) : Notification

    data class CottonReaction(
        val answer: QuestionAnswer,
        val reaction: Reaction,
    ) : Notification

    data class NewDiary(
        val group: ShareGroup,
        val diary: ShareDiary,
    ) : Notification

    data class DiaryComment(
        val diary: ShareDiary,
        val comment: Comment
    ) : Notification
}
