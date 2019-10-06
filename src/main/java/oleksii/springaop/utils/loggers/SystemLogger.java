package oleksii.springaop.utils.loggers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static oleksii.springaop.utils.Constants.DEBUG_PROFILE_NAME;

@Service
@Profile(DEBUG_PROFILE_NAME)
public class SystemLogger implements Logger{
    public void Log(String logString){
        System.out.println(logString);
    }
}
