package cn.edu.njnu.curriculumdesign.common;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMessage("success");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode("200");
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static Result fail(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
