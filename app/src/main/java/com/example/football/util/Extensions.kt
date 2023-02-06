package com.example.football.util

import java.util.*
import kotlin.Comparator

class Extensions {

    fun getSortedMap(originalMap: Map<String?, Any?>?): SortedMap<String?, Any?> {
        val tmpMap: SortedMap<String?, Any?> = TreeMap<String, Any>(
            Comparator<String?> { key1, key2 ->
                Util.getDateOnly(key2!!).compareTo(key1!!)
            })
        tmpMap.putAll(originalMap!!)
        return tmpMap
    }
}