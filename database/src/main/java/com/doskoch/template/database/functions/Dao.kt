package com.doskoch.template.database.functions

import com.doskoch.template.database.common.BaseDao

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
fun <D : BaseDao<E>, E : Any, K : Any> D.update(
    old: List<E>,
    new: List<E>,
    getId: (entity: E) -> K
) {
    val oldIds = old.map(getId).toSet()
    val newIds = new.map(getId).toSet()

    val toDelete = oldIds.subtract(newIds).let { result -> old.filter { getId(it) in result } }
    val toUpdate = oldIds.intersect(newIds).let { result -> new.filter { getId(it) in result } }
    val toInsert = newIds.subtract(oldIds).let { result -> new.filter { getId(it) in result } }

    database.runInTransaction {
        if (toDelete.isNotEmpty()) delete(toDelete)
        if (toUpdate.isNotEmpty()) update(toUpdate)
        if (toInsert.isNotEmpty()) insert(toInsert)
    }
}
