package oleksii.springaop.aop;

import oleksii.springaop.utils.authorization.HasRole;
import oleksii.springaop.utils.authorization.NotAuthenticatedException;
import oleksii.springaop.utils.authorization.NotAuthorizedException;
import oleksii.springaop.utils.authorization.Roles;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static oleksii.springaop.utils.Constants.AUTH_COOKIE_NAME;

@Aspect
@Component
public class AuthorizationAspect {
    @Before("@annotation(oleksii.springaop.utils.authorization.HasRole)")
    public void before(JoinPoint point) throws IOException {
        HasRole annotation = ((MethodSignature)point.getStaticPart().getSignature()).getMethod().getAnnotation(HasRole.class);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if(!(attributes instanceof ServletRequestAttributes))
            throw new RuntimeException("The method was called outside of servlet request");
        Cookie[] cookies = ((ServletRequestAttributes) attributes).getRequest().getCookies();
        if(cookies == null)
            throw new NotAuthenticatedException();
        Optional<Cookie> authorizationCookie = Stream.of(cookies)
                .filter(cookie -> AUTH_COOKIE_NAME.equals(cookie.getName())).findFirst();
        if(authorizationCookie.isEmpty())
            throw new NotAuthenticatedException();
        Roles role = Roles.valueOf(authorizationCookie.get().getValue());
        if(role.ordinal()<annotation.role().ordinal())
            throw new NotAuthorizedException(role.toString(), annotation.role().toString());
    }
}
