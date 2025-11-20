package com.kgav.gw.config

import com.kgav.gw.user.model.UserDetailsImpl
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class CurrentUserArgumentResolver : HandlerMethodArgumentResolver {


    override fun supportsParameter(parameter: MethodParameter): Boolean {
        println("supportsParameter 진입")
        return parameter.getParameterAnnotation(CurrentUser::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): Any? {
        println("resolveArgument 진입")
        val auth = SecurityContextHolder.getContext().authentication
        return if (auth != null && auth.principal is UserDetailsImpl) {
            auth.principal as UserDetailsImpl
        } else null
    }
}
