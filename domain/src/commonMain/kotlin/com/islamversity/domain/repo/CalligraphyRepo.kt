package com.islamversity.domain.repo

import com.islamversity.core.Mapper
import com.islamversity.core.mapWith
import com.islamversity.core.mapWithNullable
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.CalligraphyId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.islamversity.db.Calligraphy as CalligraphyEntity

interface CalligraphyRepo {
    fun getAllSurahNameCalligraphies(): Flow<List<Calligraphy>>
    fun getAllAyaCalligraphies(): Flow<List<Calligraphy>>

    fun getCalligraphy(id: CalligraphyId): Flow<Calligraphy?>

    fun getMainAyaCalligraphy(): Flow<Calligraphy>
}

class CalligraphyRepoImpl(
    private val ds: CalligraphyLocalDataSource,
    private val mapper: Mapper<CalligraphyEntity, Calligraphy>
) : CalligraphyRepo {
    override fun getAllSurahNameCalligraphies(): Flow<List<Calligraphy>> =
        ds.getAllSurahNameCalligraphies()
            .map {
                it.map {
                    mapper.map(it)
                }
            }

    override fun getAllAyaCalligraphies(): Flow<List<Calligraphy>> =
        ds.getAllAyaCalligraphies()
            .map {
                it.map {
                    mapper.map(it)
                }
            }

    override fun getCalligraphy(id: CalligraphyId): Flow<Calligraphy?> =
        ds.getCalligraphyById(com.islamversity.db.model.CalligraphyId(id.id))
            .mapWithNullable(mapper)

    override fun getMainAyaCalligraphy(): Flow<Calligraphy> =
        ds.getArabicSimpleAyaCalligraphy()
            .mapWith(mapper)

}