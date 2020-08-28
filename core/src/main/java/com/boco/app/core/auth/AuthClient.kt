package com.boco.app.core.auth

/**
 * Created by walle on 2017/9/27.
 */
object AuthClient {
    private var roles: ArrayList<String> = ArrayList()
    private fun setRoles(_roles: List<String>?): AuthClient {
        roles.clear()
        if (_roles != null)
            this.roles.addAll(_roles)
        return this
    }

    private var account: String? = null

    fun setAccount(account: String) {
        this.account = account
    }

    /**
     * 获取当前登陆账号
     */
    fun getAccount() = account

    private var userInfo: UserInfo? = null

    fun setUserInfo(userInfo: UserInfo) {
        this.userInfo = userInfo
        setRoles(userInfo.roles)
    }

    /**
     * 获取当前登陆者信息
     */
    fun getUserInfo() = userInfo

    /**
     * 判断当前登陆者是否有role的角色
     */
    fun hasRole(role:RolesEnum): Boolean = roles.contains(role.value)

}