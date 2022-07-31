package com.doskoch.movies.database

/**
 * Performs:
 * 1) delete operation for items from the old List, which ids are not found in the new List;
 * 2) update operation for items from the new List, which ids are present in the old List;
 * 3) insert operation for items from the new List, which ids are not found in the old List.
 * @param [old] list of current items.
 * @param [new] list of new items.
 * @param [getId] function, which retrieves field or combination of fields,
 * which should be unique per entity and will be used for comparison.
 */
fun <D : BaseDao<E>, E : Any, K : Any> D.updateRelations(
    old: List<E>,
    new: List<E>,
    getId: (entity: E) -> K
) {
    val oldIds = old.map(getId)
    val newIds = new.map(getId)

    oldIds.subtract(newIds).let { result ->
        old.filter { getId(it) in result }.let { if (it.isNotEmpty()) delete(it) }
    }
    oldIds.intersect(newIds).let { result ->
        new.filter { getId(it) in result }.let { if (it.isNotEmpty()) update(it) }
    }
    newIds.subtract(oldIds).let { result ->
        new.filter { getId(it) in result }.let { if (it.isNotEmpty()) insert(it) }
    }
}
