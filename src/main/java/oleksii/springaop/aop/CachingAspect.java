package oleksii.springaop.aop;

import oleksii.springaop.utils.cache.Cached;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CachingAspect {
    Object cachedValue = null;
    long cachedAt;

    @Around("@annotation(oleksii.springaop.utils.cache.Cached) && within(oleksii.springaop..*)")
    public Object processCached(ProceedingJoinPoint point) throws Throwable {
        if(System.currentTimeMillis()-cachedAt>((MethodSignature)point.getSignature()).getMethod().getAnnotation(Cached.class).cacheTime())
            cachedValue = null;

        if(cachedValue==null){
            cachedValue = point.proceed();
            cachedAt = System.currentTimeMillis();
        }

        return cachedValue;
    }
}
