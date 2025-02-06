package online.abhijeetadarsh.hms.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestId = UUID.randomUUID().toString();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        // Log request details
        LocalDateTime startTime = LocalDateTime.now();
        log.info("API Request - [{}] Started at {} - {} {} - Client IP: {}",
                requestId,
                startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        // Log request parameters if any
        if (request.getQueryString() != null) {
            log.info("Query Parameters - [{}]: {}", requestId, request.getQueryString());
        }

        // Log request body for POST/PUT requests
        if (joinPoint.getArgs().length > 0 &&
                (request.getMethod().equals("POST") || request.getMethod().equals("PUT"))) {
            try {
                String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs()[0]);
                log.info("Request Body - [{}]: {}", requestId, requestBody);
            } catch (Exception e) {
                log.warn("Could not log request body - [{}]", requestId);
            }
        }

        try {
            // Execute the actual method
            Object result = joinPoint.proceed();

            // Log response
            LocalDateTime endTime = LocalDateTime.now();
            log.info("API Response - [{}] Completed at {} - Duration: {} ms",
                    requestId,
                    endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    endTime.minusNanos(startTime.getNano()).getNano() / 1_000_000);

            return result;
        } catch (Exception e) {
            // Log error
            log.error("API Error - [{}] - Exception: {} - Message: {}",
                    requestId,
                    e.getClass().getSimpleName(),
                    e.getMessage());
            throw e;
        }
    }
}