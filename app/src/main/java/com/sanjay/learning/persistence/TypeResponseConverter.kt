package com.sanjay.learning.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.sanjay.learning.data.TypeResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
/**
 * As room does not support storing list, this class is needed to support storing list
* */
@ProvidedTypeConverter
class TypeResponseConverter(
  private val moshi: Moshi
) {

  @TypeConverter
  fun fromString(value: String): List<TypeResponse>? {
    val listType = Types.newParameterizedType(List::class.java, TypeResponse::class.java)
    val adapter: JsonAdapter<List<TypeResponse>> = moshi.adapter(listType)
    return adapter.fromJson(value)
  }

  @TypeConverter
  fun fromInfoType(type: List<TypeResponse>?): String {
    val listType = Types.newParameterizedType(List::class.java, TypeResponse::class.java)
    val adapter: JsonAdapter<List<TypeResponse>> = moshi.adapter(listType)
    return adapter.toJson(type)
  }
}
