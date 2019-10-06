package oleksii.springaop.service;

import oleksii.springaop.utils.cache.Cached;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SecretService {
    @Cached(cacheTime = 10*1000)
    public String getSecret(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
