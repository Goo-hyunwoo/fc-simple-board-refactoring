package udm.pixo.board.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import udm.pixo.board.common.Api;

@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Api> exception(Exception e) {
		
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorMessageList = Arrays.asList( e.getStackTrace() )
                .stream()
                .map(it -> {
                    String format = 
                    		"ClassLoader: %s || Class : %s || FileName : %s || LineNumber : %s || "
                    		+ "MethodName : %s || ModuleName : %s || ModuleVersion : %s";
                    var message = String.format(
                    		format, 
                    		it.getClassLoaderName(), 
                    		it.getClassName(), 
                    		it.getFileName(),
                    		it.getLineNumber(),
                    		it.getMethodName(),
                    		it.getModuleName(),
                    		it.getModuleVersion()
                    	);
                    return message;
                }).collect(Collectors.toList());
        errorMessageList.add(0, e.getMessage());
        
        var errorResponse = Api.error(errorMessageList, status);

        return ResponseEntity.status(status).body(errorResponse);
	}
	
}
