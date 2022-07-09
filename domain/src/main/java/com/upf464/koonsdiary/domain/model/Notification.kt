package com.upf464.koonsdiary.domain.model

sealed interface Notification {

    data class GroupInvite(
        val group: ShareGroup = ShareGroup(),
        val isAccepted: Boolean? = null,
    ) : Notification

    data class CottonReaction(
        val answer: QuestionAnswer = QuestionAnswer(),
        val reaction: Reaction = Reaction(),
    ) : Notification

    data class NewDiary(
        val diary: ShareDiary = ShareDiary(),
    ) : Notification

    data class DiaryComment(
        val diary: ShareDiary = ShareDiary(),
        val comment: Comment = Comment(),
    ) : Notification
}
