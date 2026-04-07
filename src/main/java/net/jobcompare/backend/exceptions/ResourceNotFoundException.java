package net.jobcompare.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        // 'super()' invokes the constructor of the immediate parent class ...
        // ... in this case this is calling the constructor from 'RunetimeException' class since it extends it in the class signature
        super(message);
    }
}
