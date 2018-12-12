package com.github.lyd.common.exception;

import com.github.lyd.common.constants.ResultEnum;
import com.github.lyd.common.utils.SpringContextHolder;
import com.github.lyd.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 统一异常管理
 *
 * @author LYD
 * @date 2017/7/3
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class OpenExceptionHandler {
    /**
     * 国际化配置
     */
    private static Locale locale = LocaleContextHolder.getLocale();
    private static final String KEY = "x.servlet.exception.code";

    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public static ResultBody authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultEnum code = ResultEnum.ERROR;
        if (ex instanceof UsernameNotFoundException) {
            code = ResultEnum.USER_NOT_EXIST;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (ex instanceof BadCredentialsException) {
            code = ResultEnum.USER_LOGIN_FAIL;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (ex instanceof AccountExpiredException) {
            code = ResultEnum.USER_ACCOUNT_EXPIRED;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (ex instanceof LockedException) {
            code = ResultEnum.USER_LOCKED;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (ex instanceof DisabledException) {
            code = ResultEnum.USER_DISABLED;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (ex instanceof CredentialsExpiredException) {
            code = ResultEnum.CREDENTIALS_EXPIRED;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (ex instanceof InsufficientAuthenticationException) {
            code = ResultEnum.UNAUTHORIZED;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        //放入请求域
        request.setAttribute(KEY, code);
        return buildBody(ex, request, response);
    }

    /**
     * OAuth2Exception
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OAuth2Exception.class,InvalidTokenException.class})
    public static ResultBody oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultEnum code = ResultEnum.UNAUTHORIZED;
        if (ex instanceof InvalidClientException) {
            code = ResultEnum.INVALID_CLIENT;
        } else if (ex instanceof UnauthorizedClientException) {
            code = ResultEnum.UNAUTHORIZED_CLIENT;
        } else if (ex instanceof InvalidGrantException) {
            code = ResultEnum.INVALID_GRANT;
        } else if (ex instanceof InvalidScopeException) {
            code = ResultEnum.INVALID_SCOPE;
        } else if (ex instanceof InvalidTokenException) {
            code = ResultEnum.INVALID_TOKEN;
        } else if (ex instanceof InvalidRequestException) {
            code = ResultEnum.INVALID_REQUEST;
        } else if (ex instanceof RedirectMismatchException) {
            code = ResultEnum.REDIRECT_URI_MISMATCH;
        } else if (ex instanceof UnsupportedGrantTypeException) {
            code = ResultEnum.UNSUPPORTED_GRANT_TYPE;
        } else if (ex instanceof UnsupportedResponseTypeException) {
            code = ResultEnum.UNSUPPORTED_RESPONSE_TYPE;
        } else if (ex instanceof UserDeniedAuthorizationException) {
            code = ResultEnum.ACCESS_DENIED;
        } else {
            code = ResultEnum.INVALID_REQUEST;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //放入请求域
        request.setAttribute(KEY, code);
        return buildBody(ex, request, response);
    }

    /**
     * 自定义异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OpenException.class})
    public static ResultBody openException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultEnum code = ResultEnum.ERROR;
        if (ex instanceof OpenMessageException) {
            code = ResultEnum.ALERT_ERROR;
        }
        if (ex instanceof OpenSignatureException) {
            code = ResultEnum.SIGNATURE_DENIED;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        //放入请求域
        request.setAttribute(KEY, code);
        return buildBody(ex, request, response);
    }

    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({Exception.class})
    public static ResultBody exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultEnum code = ResultEnum.ERROR;
        if (ex instanceof HttpMessageNotReadableException || ex instanceof TypeMismatchException || ex instanceof MissingServletRequestParameterException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            code = ResultEnum.BAD_REQUEST;
        } else if (ex instanceof NoHandlerFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            code = ResultEnum.NOT_FOUND;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            code = ResultEnum.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            code = ResultEnum.MEDIA_TYPE_NOT_ACCEPTABLE;
        } else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            code = ResultEnum.PARAMETER_ERROR;
            return ResultBody.failed(code.getCode(), bindingResult.getFieldError().getDefaultMessage());
        } else if (ex instanceof IllegalArgumentException) {
            //参数错误
            code = ResultEnum.PARAMETER_ERROR;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (ex instanceof AccessDeniedException) {
            code = ResultEnum.ACCESS_DENIED;
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        request.setAttribute(KEY, code);
        return buildBody(ex, request, response);
    }


    /**
     * 解析异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    public static ResultBody resolveException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = null;
        if (ex instanceof AuthenticationException) {
            resultBody = authenticationException(ex, request, response);
        } else if (ex instanceof OAuth2Exception) {
            resultBody = oauth2Exception(ex, request, response);
        } else if (ex instanceof OpenException) {
            resultBody = openException(ex, request, response);
        } else {
            resultBody = exception(ex, request, response);
        }
        return resultBody;
    }

    private static ResultBody buildBody(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        String exception = ex.getMessage();
        String path = request.getRequestURI();
        String method = request.getMethod();
        ResultEnum resultCode = (ResultEnum) request.getAttribute(KEY);
        if (resultCode == null) {
            resultCode = ResultEnum.ERROR;
        }
        //提示消息
        String message = "";
        if (resultCode.getCode() == ResultEnum.ALERT_ERROR.getCode() || resultCode.getCode() == ResultEnum.SIGNATURE_DENIED.getCode()|| resultCode.getCode() == ResultEnum.PARAMETER_ERROR.getCode()) {
            message = ex.getMessage();
        } else {
            message = i18n(resultCode);
        }
        log.error("错误解析:method={},path={},code={},message={},exception={}", method, path, resultCode.getCode(), message, exception,ex);
        return ResultBody.failed(resultCode.getCode(), message).setPath(path);
    }

    private static String i18n(ResultEnum httpCode) {
        MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
        return messageSource.getMessage(httpCode.getMessage(), null, httpCode.getMessage(), locale);
    }
}
