package oleksii.springaop.utils.loggers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static oleksii.springaop.utils.Constants.NOT_DEBUG_PROFILE_NAME;

@Service
@Profile(NOT_DEBUG_PROFILE_NAME)
public class FileLogger implements Logger{
    public void Log(String logString){
        System.out.println("Oh my, logging to file..");
        //TODO add logging to file
    }
}
