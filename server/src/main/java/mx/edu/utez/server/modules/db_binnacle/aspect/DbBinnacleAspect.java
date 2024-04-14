package mx.edu.utez.server.modules.db_binnacle.aspect;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.db_binnacle.service.DbBinnacleService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DbBinnacleAspect {
    private final DbBinnacleService dbBinnacleService;
    private String loggedUsername;

    @Before("execution(* mx.edu.utez.server.modules.*.service.*Service.save*(..)) || execution(* mx.edu.utez.server.modules.*.service.*Service.update*(..))")
    public void setLoggedUsername() {
        try {
            loggedUsername = Methods.getLoggedUsername();
        } catch (Exception e) {
            loggedUsername = "Unknown";
        }
    }

    @Async
    @AfterReturning(pointcut = "execution(* mx.edu.utez.server.modules.*.service.*Service.save*(..))", returning = "result")
    public void logAfterSaveOperation(JoinPoint joinPoint, ResponseApi<?> result) {
        String methodName = joinPoint.getSignature().getName();
        String serviceName = joinPoint.getTarget().getClass().getSimpleName();
        String tableName = Methods.getTableName(serviceName);

        if (result.getData() != null) {
            dbBinnacleService.log("CREATE", tableName, serviceName, methodName, loggedUsername);
        }
    }

    @Async
    @AfterReturning(pointcut = "execution(* mx.edu.utez.server.modules.*.service.*Service.update*(..))", returning = "result")
    public void logAfterUpdateOperation(JoinPoint joinPoint, ResponseApi<?> result) {
        String methodName = joinPoint.getSignature().getName();
        String serviceName = joinPoint.getTarget().getClass().getSimpleName();
        String tableName = Methods.getTableName(serviceName);

        if (result.getData() != null) {
            dbBinnacleService.log("UPDATE", tableName, serviceName, methodName, loggedUsername);
        }
    }

    @Async
    @AfterReturning(pointcut = "execution(* mx.edu.utez.server.modules.*.service.*Service.changeStatus*(..))", returning = "result")
    public void logAfterChangeStatusOperation(JoinPoint joinPoint, ResponseApi<?> result) {
        String methodName = joinPoint.getSignature().getName();
        String serviceName = joinPoint.getTarget().getClass().getSimpleName();
        String tableName = Methods.getTableName(serviceName);

        if (result.getData() != null) {
            dbBinnacleService.log("CHANGE STATUS", tableName, serviceName, methodName, loggedUsername);
        }
    }
}
