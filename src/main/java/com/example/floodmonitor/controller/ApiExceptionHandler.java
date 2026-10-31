package com.example.floodmonitor.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
 public record ApiError(Instant timestamp,int status,String error,String message,String path) {}
 private ResponseEntity<Object> result(HttpStatusCode code,String message,String path){HttpStatus s=HttpStatus.valueOf(code.value());return ResponseEntity.status(code).body(new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),message,path));}
 @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<Object> missing(NoSuchElementException e,HttpServletRequest req){return result(HttpStatus.NOT_FOUND,e.getMessage(),req.getRequestURI());}
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<Object> bad(IllegalArgumentException e,HttpServletRequest req){return result(HttpStatus.BAD_REQUEST,e.getMessage(),req.getRequestURI());}
 @ExceptionHandler(IllegalStateException.class) public ResponseEntity<Object> conflict(IllegalStateException e,HttpServletRequest req){return result(HttpStatus.CONFLICT,e.getMessage(),req.getRequestURI());}
 @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException e,HttpHeaders h,HttpStatusCode s,org.springframework.web.context.request.WebRequest r){return result(s,"Ungültige Eingabe",path(r));}
 @Override protected ResponseEntity<Object> handleTypeMismatch(org.springframework.beans.TypeMismatchException e,HttpHeaders h,HttpStatusCode s,org.springframework.web.context.request.WebRequest r){return result(s,"Ungültiger Parameter: "+e.getPropertyName(),path(r));}
 @Override protected ResponseEntity<Object> handleHttpMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException e,HttpHeaders h,HttpStatusCode s,org.springframework.web.context.request.WebRequest r){return result(s,"Ungültiger Request-Body",path(r));}
 @Override protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(org.springframework.web.HttpMediaTypeNotAcceptableException e,HttpHeaders h,HttpStatusCode s,org.springframework.web.context.request.WebRequest r){return result(s,"Format nicht unterstützt",path(r));}
 @Override protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(org.springframework.web.HttpMediaTypeNotSupportedException e,HttpHeaders h,HttpStatusCode s,org.springframework.web.context.request.WebRequest r){return result(s,"Medientyp nicht unterstützt",path(r));}
 private String path(org.springframework.web.context.request.WebRequest r){return r.getDescription(false).replace("uri=","");}
}