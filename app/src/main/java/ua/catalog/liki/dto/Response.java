package ua.catalog.liki.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.catalog.liki.views.View;

public class Response<T> {

    public static final String STATUS_SUCCESS = "success";

    @JsonView(View.Public.class)
    public String status = STATUS_SUCCESS;

    @JsonView(View.Public.class)
    public String message;

    @JsonView(View.Public.class)
    public T data;

}
