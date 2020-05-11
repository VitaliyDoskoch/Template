package com.doskoch.movies.core.components.ui.base.pager.behavior

import androidx.fragment.app.FragmentPagerAdapter

enum class PagerAdapterBehavior {
    BEHAVIOR_SET_USER_VISIBLE_HINT {
        override val constant: Int = FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT
    },
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT {
        override val constant: Int = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    };

    abstract val constant: Int
}