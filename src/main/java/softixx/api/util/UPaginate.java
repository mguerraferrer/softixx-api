package softixx.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;

public class UPaginate {
	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	public static int page(Optional<Integer> page) {
		if(page.isPresent()) {
			int pageNumber = page.get(); 
			return pageNumber > 0 ? pageNumber - 1 : pageNumber;
		}
		return DEFAULT_PAGE;
	}
	
	public static int pageSize(Optional<Integer> pageSize) {
		return pageSize.orElse(DEFAULT_PAGE_SIZE);
	}
	
	public static List<?> paginate(final List<?> list, final int page, final int pageSize) {
		if(list != null && !list.isEmpty()) {
			val offset = page * pageSize;
			if (offset >= list.size()) {
				return new ArrayList<>();
			}

			return list.stream()
					   .skip(offset)
					   .limit(pageSize)
					   .collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
}