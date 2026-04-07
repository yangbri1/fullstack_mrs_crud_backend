/* WHY???
** Why encapsulate Bad Request and Resource Not FOund exceptions into their own dedicated class when i nthe class itself it just makes a call to the RuntimeException superclass parent's constructor? 
** ANS:
** This acts as a label, not overengineering as it would provide the same contents ...
** ... also no controller code changes req. in the future from changes ...
** Plus, custom exception naming allows may allow easier debugging */
package net.jobcompare.backend.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
        
}
