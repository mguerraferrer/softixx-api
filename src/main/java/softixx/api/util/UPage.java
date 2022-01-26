package softixx.api.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.val;

public class UPage {
	private UPage() {
		throw new IllegalStateException("Utility class");
	}
	
	public static <T> Page<T> toPage(final List<T> list, final Pageable pageable) {
		if(list.isEmpty() || pageable == null || pageable.getOffset() >= list.size()) {
			return Page.empty();
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
		
		val subList = list.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, list.size());
	}
	
	public static <T> int totalPages(final List<T> list, final int page, final int pageSize) {
		val pageable = PageRequest.of(page, pageSize);
		val pageImpl = toPage(list, pageable);
		return pageImpl.getTotalPages();
	}
	
	public static <T> boolean isFirstPage(final List<T> list, final int page, final int pageSize) {
		val pageable = PageRequest.of(page, pageSize);
		val pageImpl = toPage(list, pageable);
		return pageImpl.isFirst();
	}
	
	public static <T> boolean isLastPage(final List<T> list, final int page, final int pageSize) {
		val pageable = PageRequest.of(page, pageSize);
		val pageImpl = toPage(list, pageable);
		return pageImpl.isLast();
	}
	
}