package com.evaluable.navigation

sealed class AppScreens(val route: String) {
    object MainMenu: AppScreens("main_menu")
    object AddBlade: AppScreens("add_blade")
    object DeleteBlade: AppScreens("delete_blade")
    object ModifyBlade: AppScreens("modify_blade")
    object ListBlades: AppScreens("list_blades")
    object SearchBlade: AppScreens("search_blade")
}