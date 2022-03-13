package com.wrx.codeplatform.utils.common;

import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.result.JsonResult;

/**
 * @author 魏荣轩
 * @date 2022/2/23 0:33
 */
public class ResultUtil {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
}
