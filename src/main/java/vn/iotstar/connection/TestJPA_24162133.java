package vn.iotstar.connection;

import vn.iotstar.model.Book_24162133;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.impl.BookServiceImpl_24162133;
import java.util.List;

public class TestJPA_24162133 {
    public static void main(String[] args) {
        BookService_24162133 service = new BookServiceImpl_24162133();
        System.out.println(">>> Tổng số sách: " + service.count());
        System.out.println(">>> Tổng số trang (6/trang): " + service.getTotalPages(6));
        List<Book_24162133> list = service.findAll(1, 6);
        for (Book_24162133 b : list) {
            System.out.println("  " + b.getBookId() + " - " + b.getTitle());
        }
    }
}