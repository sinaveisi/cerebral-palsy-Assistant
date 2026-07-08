package com.example.cp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TitleWithDescription(
    @Embedded val parent: TitleEntity,
    @Relation(
        parentColumn = "titleId",
        entityColumn = "parentOwnerId"
    )
    val children: List<DescriptionEntity>
)