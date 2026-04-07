/* What is the reason for including a whole dedicated class for 'ResourceNotFound' exception ...
... b/c Spring DN care how the exception was created but it does care what class it is ...
... Java excepton handling is type-based, not constructor-based */
package net.jobcompare.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        // 'super()' invokes the constructor of the immediate parent class ...
        // ... in this case this is calling the constructor from 'RunetimeException' class since it extends it in the class signature
        super(message);
    }
}
