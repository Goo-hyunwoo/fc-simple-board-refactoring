package udm.pixo.board.common;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Api<T> {
	private String resultCode;
	private String resultMessage;
	@Valid
	private T data;
	private Error error;

	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Error {
		private List<String> errorMessage;
	}
	
	
	
	
	public static <T> Api<T> success(T t) {
		return Api.<T>builder().resultCode(String.valueOf(HttpStatus.OK.value()))
				.resultMessage(HttpStatus.OK.name()).data(t).build();
	}
	
	public static Api error(List<String> errorStack, HttpStatus status) {
        var error = Api.Error.builder()
                .errorMessage(errorStack)
                .build();

		return Api.builder()
                .resultCode(String.valueOf(status.value()))
                .resultMessage(status.getReasonPhrase())
                .error(error)
                .build();
	}
}
