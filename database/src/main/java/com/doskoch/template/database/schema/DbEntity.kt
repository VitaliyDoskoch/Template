package com.doskoch.template.database.schema

import androidx.room.Entity
import com.doskoch.template.database.common.BaseEntity

@Entity
data class DbEntity(val id: String) : BaseEntity()
