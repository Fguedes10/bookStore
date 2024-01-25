package mindera.backendProject.bookStore.controller;

import mindera.backendProject.bookStore.model.BookOrder;
import mindera.backendProject.bookStore.service.BookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookOrder")
public class BookOrderController {

    private final BookOrderService bookOrderService;
    @Autowired
    public BookOrderController(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOrder> getBookOrder(@PathVariable Long id) {
        return bookOrderService.getBookOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
