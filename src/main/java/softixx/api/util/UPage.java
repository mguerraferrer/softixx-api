package softixx.api.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.val;

public class UPage<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Page toPage(final List list, final Pageable pageable) {
		if(list.isEmpty() || pageable == null) {
			return Page.empty();
		} else if (pageable.getOffset() >= list.size()) {
			return Page.empty();
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
		
		val subList = list.subList(startIndex, endIndex);
		return new PageImpl(subList, pageable, list.size());
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static int totalPages(final List list, final int page, final int pageSize) {
		val pageable = PageRequest.of(page, pageSize);
		val pageImpl = toPage(list, pageable);
		return pageImpl.getTotalPages();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static boolean isFirstPage(final List list, final int page, final int pageSize) {
		val pageable = PageRequest.of(page, pageSize);
		val pageImpl = toPage(list, pageable);
		return pageImpl.isFirst();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static boolean isLastPage(final List list, final int page, final int pageSize) {
		val pageable = PageRequest.of(page, pageSize);
		val pageImpl = toPage(list, pageable);
		return pageImpl.isLast();
	}
}