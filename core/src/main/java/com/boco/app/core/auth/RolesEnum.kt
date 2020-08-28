package com.boco.app.core.auth

/**
 * Created by void on 2017/12/20.
 */
enum class RolesEnum(var value: String) {
    MAYOR("市长"),
    EMERGENCY("应急办"),
    RESCUE_TEAM("救援队伍队长"),
    MATERIALS("救援物资队长");
}