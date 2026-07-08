package com.example.cp.data.local.model

import com.example.cp.data.local.entity.DescriptionEntity
import com.example.cp.data.local.entity.TitleEntity

data class InitialTopic(
    val title: TitleEntity,
    val descriptions: List<DescriptionEntity>
)