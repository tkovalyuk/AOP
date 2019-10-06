package oleksii.springaop.aop;

import oleksii.springaop.utils.loggers.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Aspect
@Component
public class LoggingAspect {
    Consumer<String> logger;

    public LoggingAspect(final Logger logger){
        this.logger = logger::Log;
    }

    @Around("@target(org.springframework.web.bind.annotation.RestController) && within(oleksii.springaop..*)")
    public Object defaultLogBehaviour(ProceedingJoinPoint method) throws Throwable {
        String methodName = method.getStaticPart().toLongString();
        try {
            logger.accept("Method "+methodName+" has started");
            Object returnValue = method.proceed();
            logger.accept("Method "+methodName+" has finished");
            return returnValue;
        }catch (Throwable e){
            logger.accept(String.format("Can't finish logging for method %s, got exception [%s]: %s", methodName, e.getClass().toString(), e.getMessage()));
            throw e;
        }
    }
}
