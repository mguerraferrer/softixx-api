package softixx.api.json;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JPaginationContent<T> {
	
	private Page<T> page;
	private Integer pageSize;
	
}