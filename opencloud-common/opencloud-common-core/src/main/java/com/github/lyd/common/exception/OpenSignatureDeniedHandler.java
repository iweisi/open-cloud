package com.github.lyd.common.exception;

import com.github.lyd.common.model.ResultBody;
import com.github.lyd.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义签名错误处理器
 *
 * @author liuyadu
 */
@Slf4j
public class OpenSignatureDeniedHandler implements SignatureDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       Exception exception) throws IOException, ServletException {
        ResultBody responseData = OpenExceptionHandler.resolveException(exception, request, response);
        WebUtils.writeJson(response, responseData);
    }
}