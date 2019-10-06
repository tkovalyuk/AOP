package oleksii.springaop.aop;

import oleksii.springaop.utils.loggers.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static oleksii.springaop.utils.Constants.DEBUG_PROFILE_NAME;

@Aspect
@Component
@Profile(DEBUG_PROFILE_NAME)
public class StopwatchAspect {
    Consumer<String> consumer;

    public StopwatchAspect(final Logger logger){
        this.consumer = logger::Log;
    }

    @Around("within(oleksii.springaop.controllers.*)")
    public Object stopwatch(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.nanoTime();
        Object ret = point.proceed();
        long totalTime = System.nanoTime() - startTime;
        consumer.accept(String.format("Total time spent on method [%s] is %d", point.getStaticPart().toLongString(), totalTime));
        return ret;
    }
}
