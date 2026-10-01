package jsp.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jsp.springboot.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRAE(NoRecordAvailableException e){
		
		ResponseStructure<String> res =new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException e){
		
		ResponseStructure<String> res =new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
}
