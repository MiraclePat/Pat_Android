package com.pat.presentation.util

import com.orhanobut.logger.Logger
import com.pat.domain.model.exception.BadRequestException
import com.pat.domain.model.exception.ForbiddenException
import com.pat.domain.model.exception.NotFoundException

fun resultException(error: Throwable?) {
    when (error) {
        is BadRequestException -> {
            Logger.t("errorCode").i("${error.message}")
        }

        is NotFoundException -> {
            Logger.t("errorCode").i("${error.message}")
        }

        is ForbiddenException -> {
            Logger.t("errorCode").i("${error.message}")
        }
        else -> {
            Logger.t("code").i("${error?.message}")
        }
    }
}