package oleksii.springaop.utils.authorization;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String requiredRole, String currentRole){
        super(String.format("Request was rejected because required role is %s but user had role %s", requiredRole, currentRole));
    }
}
