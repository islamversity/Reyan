package com.islamversity.domain.model.surah

import com.islamversity.db.model.BismillahType
import com.islamversity.db.model.BismillahTypeFlag

enum class BismillahRepoType {
    FIRST_AYA,
    NEEDED,
    NONE,
    ;
}

fun BismillahRepoType.toEntity() =
    when (this) {
        BismillahRepoType.FIRST_AYA -> BismillahTypeFlag.FIRST_AYA
        BismillahRepoType.NEEDED -> BismillahTypeFlag.NEEDED
        BismillahRepoType.NONE -> BismillahTypeFlag.NONE
    }

fun BismillahTypeFlag.toRepo() =
    when (this) {
        BismillahTypeFlag.FIRST_AYA -> BismillahRepoType.FIRST_AYA
        BismillahTypeFlag.NEEDED -> BismillahRepoType.NEEDED
        BismillahTypeFlag.NONE -> BismillahRepoType.NONE
    }