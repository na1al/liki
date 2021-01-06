package ua.catalog.liki.dto;

public class Response<T> {

    public static final String STATUS_SUCCESS = "success";

    public String status = STATUS_SUCCESS;

    public String message;

    public T data;

}
