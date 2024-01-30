package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.dto.order.OrderItemsCreateDto;
import mindera.backendProject.bookStore.exception.*;
import mindera.backendProject.bookStore.service.bookService.BookOrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderItemsController {

    private final BookOrderServiceImpl bookOrderServiceImpl;

    public OrderItemsController(BookOrderServiceImpl bookOrderServiceImpl){
        this.bookOrderServiceImpl = bookOrderServiceImpl;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderItemsCreateDto>> getBookOrders(){
        return ResponseEntity.ok(bookOrderServiceImpl.getAll());
    }



}
