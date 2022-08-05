package com.example.novelreader

sealed class BottomNavItem(var title:Int, var icon:Int, var screen_route:String) {

    object Library : BottomNavItem(R.string.nav_item_library, R.drawable.ic_book,"library")
    object Updates : BottomNavItem(R.string.nav_item_updates, R.drawable.ic_new_release,"updates")
    object History : BottomNavItem(R.string.nav_item_history, R.drawable.ic_history,"history")
    object Explore : BottomNavItem(R.string.nav_item_sources, R.drawable.ic_compas,"explore")
    object Others : BottomNavItem(R.string.nav_item_others, R.drawable.ic_dots,"others")
}
