package softixx.api.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;
import softixx.api.util.UPage;
import softixx.api.util.UPaginate;

public class PageTest {

	public static void main(String[] args) {
		var page1 = -1;
		var page2 = -1;
		val pageSize = 3;
		
		int list1Pages = -1;
		boolean list1LastPage = false;
		
		int list2Pages = -1;
		boolean list2LastPage = false;
		
		while (!list1LastPage) {
			page1 ++;
			val offset = page1 * pageSize;
			Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				  .skip(offset)
				  .limit(pageSize)
		    	  .forEach(i -> System.out.print(i + " "));
			
			System.out.println();
			val list1 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
							  .collect(Collectors.toList());
			list1Pages = UPage.totalPages(list1, page1, pageSize) - page1;
			System.out.println("list1Pages: " + list1Pages);
			
			List<Integer> iList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			val piList = UPaginate.paginate(iList, page1, pageSize);
			System.out.println("piList: ");
			piList.stream().forEach(System.out::println);
			
			list1LastPage = UPage.isLastPage(list1, page1, pageSize);
			System.out.println("list1LastPage: " + list1LastPage);
			System.out.println();
		}
		
		while (!list2LastPage) {
			page2 ++;
			
			val list2 = Stream.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o")
					  		  .collect(Collectors.toList());
			list2Pages = UPage.totalPages(list2, page2, pageSize);
			System.out.println("list2Pages: " + list2Pages);
			
			val sList = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o");
			val psList = UPaginate.paginate(sList, page2, pageSize);
			System.out.println("psList: ");
			psList.stream().forEach(System.out::println);
			
			list2LastPage = UPage.isLastPage(list2, page2, pageSize);
			System.out.println("list2LastPage: " + list2LastPage);
			System.out.println();
		}
		
	}

}