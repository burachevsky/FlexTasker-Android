package com.github.flextasker.core.data.repository

import com.github.flextasker.core.domain.repository.UserRepository
import com.github.flextasker.core.model.UserInfo
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

) : UserRepository {

    override suspend fun getCurrentUserInfo(): UserInfo? {
        return null/*UserInfo(
            id = 0,
            email = "lol@kek.com",
            defaultListId = 0,
        )*/
    }
}