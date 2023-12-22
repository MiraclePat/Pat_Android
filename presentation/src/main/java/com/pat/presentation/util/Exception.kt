package com.pat.presentation.util

import com.orhanobut.logger.Logger
import com.pat.domain.model.exception.BadRequestException
import com.pat.domain.model.exception.ForbiddenException
import com.pat.domain.model.exception.NotFoundException

fun resultException(error: Throwable?) {
    if (error is BadRequestException) {
        Logger.t("code").i("${error.message}")
    }
    if (error is NotFoundException) {
        Logger.t("code").i("${error.message}")
    }
    if (error is ForbiddenException) {
        Logger.t("code").i("${error.message}")
    }
}