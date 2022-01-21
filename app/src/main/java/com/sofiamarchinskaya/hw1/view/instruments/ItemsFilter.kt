package com.sofiamarchinskaya.hw1.view.instruments

import com.sofiamarchinskaya.hw1.models.entity.Note

class ItemsFilter : QueryFilter<Note> {
    override fun filter(query: String?, list: List<Note>?): List<Note>? =
        list?.filter {
            it.title.contains(query.toString(), ignoreCase = true)
                    || it.body.contains(query.toString().lowercase(), ignoreCase = true)
        }
}