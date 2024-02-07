package mindera.backendProject.bookStore.util;

public class Messages {

    // GENERAL SWAGGER

    public static final String OK = "200";

    public static final String CREATED = "201";
    public static final String DOCUMENT_FAILED = "400";
    public static final String CONFLICT = "409";

    public static final String NOT_FOUND = "404";

    //AUTHOR CONTROLLER SWAGGER ANNOTATIONS

    public static final String AUTHOR_TAG_NAME = "Author";

    public static final String AUTHOR_TAG_DESCRIPTION = "Author endpoints";
    public static final String AUTHORS_FOUND = "Authors found";
    public static final String AUTHOR_FOUND = "Author found";
    public static final String AUTHOR_NOT_FOUND = "Author not found";
    public static final String AUTHOR_CREATED = "Author created";
    public static final String GET_ALL_EXIST_AUTHORS = "Get all existing authors";
    public static final String GET_AUTHOR_BY_ID = "Get author by id";
    public static final String GET_AUTHOR_BY_NAME = "Get author by name";
    public static final String ADD_NEW_AUTHOR = "Add new author";
    public static final String DELETE_AUTHOR_BY_ID = "Delete author by id";
    public static final String AUTHOR_DELETED = "Author deleted";
    public static final String ADD_MULTIPLE_AUTHORS = "Add multiple authors";


    //BOOK CONTROLLER SWAGGER ANNOTATIONS

    public static final String BOOK_TAG_NAME = "Book";

    public static final String BOOK_TAG_DESCRIPTION = "Book endpoints";
    public static final String GET_GOOGLE_BOOK_BY_TITLE = "Get google book by title";
    public static final String GET_GOOGLE_BOOK_FROM_API = "Get a book from google api";
    public static final String GET_ALL_EXIST_BOOKS = "Get all existing books";
    public static final String GET_BOOK_BY_ID = "Get book by id";
    public static final String GET_BOOK_BY_TITLE = "Get book by title";
    public static final String ADD_NEW_BOOK = "Add new book";
    public static final String UPDATE_BOOK = "Update book";
    public static final String UPDATE_BOOK_PRICE = "Update book price";
    public static final String DELETE_BOOK_BY_ID = "Delete book by id";
    public static final String BOOK_DELETED = "Book deleted";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_FOUND = "Book found";
    public static final String BOOKS_FOUND = "Books found";
    public static final String GET_CUSTOMERS_WHO_FAVORITE = "Get customers who favorite book";
    public static final String BOOK_CREATED = "Book created";
    public static final String NOT_FOUND_AUTHOR_PUBLISHER_TRANSLATION = "Author or publisher or genre or translation not found";
    public static final String BOOK_UPDATED = "Book updated";
    public static final String GET_BOOKS_BY_RELEASE_YEAR = "Get books by release year";
    public static final String GET_BOOKS_BY_TRANSLATION = "Get books by translation";

    //GENRE CONTROLLER SWAGGER ANNOTATIONS

    public static final String GENRE_TAG_NAME = "Genre";

    public static final String GENRE_TAG_DESCRIPTION = "Genre endpoints";

    public static final String GET_ALL_EXIST_GENRES = "Get all existing genres";
    public static final String GET_GENRE_BY_ID = "Get genre by id";
    public static final String GET_GENRE_BY_NAME = "Get genre by name";
    public static final String ADD_NEW_GENRE = "Add new genre";
    public static final String ADD_MULTIPLE_GENRES = "Add multiple genres";
    public static final String DELETE_GENRE_BY_ID = "Delete genre by id";
    public static final String GENRE_DELETED = "Genre deleted";
    public static final String GENRE_NOT_FOUND = "Genre not found";
    public static final String GENRE_FOUND = "Genre found";
    public static final String GENRES_FOUND = "Genres found";
    public static final String GENRES_CREATED = "Genres created";
    public static final String GENRE_CREATED = "Genre created";

    //PUBLISHER CONTROLLER SWAGGER ANNOTATIONS

    public static final String PUBLISHER_TAG_NAME = "Publisher";

    public static final String PUBLISHER_TAG_DESCRIPTION = "Publisher endpoints";
    public static final String GET_ALL_EXIST_PUBLISHERS = "Get all existing publishers";
    public static final String GET_PUBLISHER_BY_ID = "Get publisher by id";
    public static final String GET_PUBLISHER_BY_NAME = "Get publisher by name";
    public static final String ADD_NEW_PUBLISHER = "Add new publisher";
    public static final String ADD_MULTIPLE_PUBLISHERS = "Add multiple publishers";
    public static final String DELETE_PUBLISHER_BY_ID = "Delete publisher by id";
    public static final String PUBLISHER_DELETED = "Publisher deleted";
    public static final String PUBLISHER_NOT_FOUND = "Publisher not found";
    public static final String PUBLISHER_FOUND = "Publisher found";
    public static final String PUBLISHERS_FOUND = "Publishers found";
    public static final String PUBLISHERS_CREATED = "Publishers created";
    public static final String PUBLISHER_CREATED = "Publisher created";

    //RATING CONTROLLER SWAGGER ANNOTATIONS

    public static final String RATING_TAG_NAME = "Rating";

    public static final String RATING_TAG_DESCRIPTION = "Rating endpoints";
    public static final String GET_RATING_BY_ID = "Get rating by id";
    public static final String GET_RATING_BY_VALUE = "Get rating by value";
    public static final String DELETE_RATING_BY_ID = "Delete rating by id";
    public static final String RATING_NOT_FOUND = "Rating not found";
    public static final String RATING_FOUND = "Rating found";


    //REVIEW CONTROLLER SWAGGER ANNOTATIONS

    public static final String REVIEW_TAG_NAME = "Review";
    public static final String REVIEW_TAG_DESCRIPTION = "Review endpoints";
    public static final String GET_ALL_EXIST_REVIEWS = "Get all existing reviews";
    public static final String GET_ALL_REVIEWS = "Get all reviews";
    public static final String GET_REVIEW_BY_ID = "Get review by id";
    public static final String ADD_NEW_REVIEW = "Add new review";
    public static final String DELETE_REVIEW_BY_ID = "Delete review by id";
    public static final String REVIEW_NOT_FOUND = "Review not found";
    public static final String REVIEW_FOUND = "Review found";
    public static final String REVIEW_CREATED = "Review created";
    public static final String REVIEW_DELETED = "Review deleted";


    //TRANSLATION CONTROLLER SWAGGER ANNOTATIONS
    public static final String TRANSLATION_TAG_NAME = "Translation";
    public static final String TRANSLATION_TAG_DESCRIPTION = "Translation endpoints";

    public static final String GET_ALL_EXIST_TRANSLATIONS = "Get all existing translations";
    public static final String GET_TRANSLATION_BY_ID = "Get translation by id";
    public static final String GET_TRANSLATION_BY_NAME = "Get translation by name";
    public static final String ADD_NEW_TRANSLATION = "Add new translation";
    public static final String ADD_MULTIPLE_TRANSLATIONS = "Add multiple translations";
    public static final String DELETE_TRANSLATION_BY_ID = "Delete translation by id";
    public static final String TRANSLATION_DELETED = "Translation deleted";
    public static final String TRANSLATION_NOT_FOUND = "Translation not found";
    public static final String TRANSLATION_FOUND = "Translation found";
    public static final String TRANSLATIONS_FOUND = "Translations found";
    public static final String TRANSLATIONS_CREATED = "Translations created";
    public static final String TRANSLATION_CREATED = "Translation created";

    //CUSTOMER CONTROLLER SWAGGER ANNOTATIONS
    public static final String CUSTOMER_TAG_NAME = "Customer";
    public static final String CUSTOMER_TAG_DESCRIPTION = "Customer endpoints";
    public static final String GET_ALL_EXIST_CUSTOMERS = "Get all existing customers";
    public static final String GET_CUSTOMER_BY_ID = "Get customer by id";
    public static final String GET_CUSTOMER_BY_USERNAME = "Get customer by username";
    public static final String GET_CUSTOMER_FAVORITE_GENRES_BY_ID = "Get customer favorite genres by id";
    public static final String GET_CUSTOMER_FAVORITE_BOOKS_BY_ID = "Get customer favorite books by id";
    public static final String ADD_NEW_CUSTOMER = "Add new customer";
    public static final String ADD_MULTIPLE_CUSTOMERS = "Add multiple customers";
    public static final String ADD_FAVORITE_BOOKS = "Add books to customer favorites";
    public static final String CUSTOMER_OR_BOOK_NOT_FOUND = "Customer or book not found";
    public static final String REPEATED_FAVORITE_BOOK = "Customer already has this book in favorites";
    public static final String UPDATE_CUSTOMER = "Update customer";
    public static final String DELETE_CUSTOMER_BY_ID = "Delete customer by id";
    public static final String CUSTOMER_DELETED = "Customer deleted";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_FOUND = "Customer found";
    public static final String CUSTOMERS_FOUND = "Customers found";
    public static final String CUSTOMERS_CREATED = "Customers created";
    public static final String CUSTOMER_CREATED = "Customer created";
    public static final String CUSTOMER_UPDATED = "Customer updated";
    public static final String CUSTOMER_ALREADY_EXISTS = "Customer already exists";


    //DOWNLOAD CONTROLLER SWAGGER ANNOTATIONS
    public static final String DOWNLOAD_TAG_NAME = "Download";
    public static final String DOWNLOAD_TAG_DESCRIPTION = "Download endpoints";
    public static final String GET_ALL_EXIST_DOWNLOADS = "Get all existing downloads";
    public static final String GET_DOWNLOAD_BY_ID = "Get download by id";
    public static final String GET_DOWNLOAD_BY_ORDER = "Get download by order";
    public static final String ADD_NEW_DOWNLOAD = "Add new download";
    public static final String DOWNLOAD_NOT_FOUND = "Download not found";
    public static final String DOWNLOAD_OR_ORDER_NOT_FOUND = "Download or Order not found";
    public static final String DOWNLOAD_FOUND = "Download found";
    public static final String DOWNLOADS_FOUND = "Downloads found";
    public static final String DOWNLOAD_CREATED = "Download created";


    //INVOICE CONTROLLER SWAGGER ANNOTATIONS
    public static final String INVOICE_TAG_NAME = "Order";
    public static final String INVOICE_TAG_DESCRIPTION = "Order endpoints";
    public static final String GET_ALL_EXIST_INVOICES = "Get all existing invoices";
    public static final String GET_INVOICE_BY_ID = "Get invoice by id";
    public static final String GET_INVOICE_BY_CUSTOMER_BY_ID = "Get all customer invoices by id";
    public static final String DELETE_INVOICE_BY_ID = "Delete invoice by id";
    public static final String INVOICE_NOT_FOUND = "Invoice not found";
    public static final String INVOICE_OR_ORDER_NOT_FOUND = "Invoice or Order not found";
    public static final String INVOICE_FOUND = "Invoice found";
    public static final String INVOICE_DELETED = "Invoice deleted";
    public static final String INVOICES_FOUND = "Invoices found";
    public static final String INVOICE_CREATED = "Invoice created";


    //ORDER CONTROLLER SWAGGER ANNOTATIONS

    public static final String ORDER_TAG_NAME = "Order";
    public static final String ORDER_TAG_DESCRIPTION = "Order endpoints";
    public static final String GET_ALL_EXIST_ORDERS = "Get all existing orders";
    public static final String GET_ORDER_BY_ID = "Get order by id";
    public static final String GET_ORDER_BY_CUSTOMER_ID = "Get order by customer id";
    public static final String GET_ORDER_BY_BOOK_ID = "Get order by book id";
    public static final String DELETE_ORDER_BY_ID = "Delete order by id";
    public static final String ADD_ORDER = "Add new order";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String ORDER_FOUND = "Order found";
    public static final String ORDERS_FOUND = "Orders found";
    public static final String ORDER_CREATED = "Order created";
    public static final String DOCUMENT_FAILED_LOAD = "Document failed to load";
    public static final String CUSTOMER_OR_FILE_NOT_FOUND = "Customer or File not found";
    public static final String ORDER_DELETED = "Order deleted";
    public static final String CUSTOMER_OR_ORDER_NOT_FOUND = "Customer or order not found";
    public static final String BOOK_OR_ORDER_NOT_FOUND = "Book or order not found";


    //ORDERITEM CONTROLLER SWAGGER ANNOTATIONS

    public static final String ORDERITEM_TAG_NAME = "OrderItem";
    public static final String ORDERITEM_TAG_DESCRIPTION = "OrderItem endpoints";
    public static final String GET_ALL_EXIST_ORDERITEMS = "Get all existing orderItems";
    public static final String GET_ORDERITEM_BY_ID = "Get orderItem by id";
    public static final String ADD_ORDERITEM = "Add new orderItem";
    public static final String ADD_MULTIPLE_ORDERITEMS = "Add multiple orderItems";
    public static final String DELETE_ORDERITEM_BY_ID = "Delete orderItem by id";
    public static final String ORDERITEM_NOT_FOUND = "OrderItem not found";
    public static final String ORDERITEM_FOUND = "OrderItem found";
    public static final String ORDERITEMS_FOUND = "OrderItems found";
    public static final String ORDERITEM_CREATED = "OrderItem created";
    public static final String ORDERITEM_DELETED = "OrderItem deleted";

    //PAYMENT CONTROLLER SWAGGER ANNOTATIONS
    public static final String PAYMENT_TAG_NAME = "Payment";
    public static final String PAYMENT_TAG_DESCRIPTION = "Payment endpoints";

    public static final String GET_ALL_EXIST_PAYMENTS = "Get all existing payments";
    public static final String GET_PAYMENT_BY_ID = "Get payment by id";
    public static final String ADD_NEW_PAYMENT = "Add new payment";
    public static final String DELETE_PAYMENT_BY_ID = "Delete payment by id";
    public static final String PAYMENT_NOT_FOUND = "Payment not found";
    public static final String PAYMENT_FOUND = "Payment found";
    public static final String PAYMENTS_FOUND = "Payments found";
    public static final String PAYMENT_CREATED = "Payment created";
    public static final String PAYMENT_DELETED = "Payment deleted";


    //CUSTOMER CONTROLLER
    public static final String CUSTOMER_WITH_ID = "Customer with id: ";
    public static final String DOESNT_EXIST = " doesn't exist";
    public static final String CUSTOMER_WITH_USERNAME = "Customer with username: ";
    public static final String CUSTOMER_WITH_EMAIL = "Customer with email: ";
    public static final String CUSTOMER_WITH_NIF = "Customer with Nif: ";
    public static final String ALREADY_EXISTS = " already exists.";
    public static final String CUSTOMER_EMAIL_ALREADY_EXISTS = "A customer with this email already exists";


    //CUSTOMER CREATEDTO
    public static final String INVALID_USERNAME = "Please input a valid username";
    public static final String MAX_CHAR_SIZE = "You exceed the max number of characters";
    public static final String INVALID_FIRSTNAME = "Please input a valid first name";
    public static final String INVALID_LASTNAME = "Please input a valid last name";
    public static final String INVALID_EMAIL = "Please input a valid email";
    public static final String INVALID_NIF = "Please input a valid nif";


    // BOOK CONTROLLER

    public static final String BOOK_WITH_ID = "Book with id: ";
    public static final String BOOK_ALREADY_EXISTS = "Book already exists.";
    public static final String BOOK_WITH_TITLE = "Book with tittle: ";


    //BOOK RELATED DTO
    public static final String INVALID_TITLE = "Please input a valid title";
    public static final String INVALID_PUBLISHER = "Please input a valid publisher";
    public static final String INVALID_EDITION = "Please input a valid edition";
    public static final String INVALID_DATE = "Please input a valid date";
    public static final String INVALID_PRICE = "Please input a valid price";
    public static final String INVALID_ISBN = "Please input a valid isbn";
    public static final String INVALID_NAME_AUTHOR = "Please input a valid author's name";
    public static final String INVALID_BOOK_GENRE = "Please input a valid book genre";
    public static final String INVALID_BOOK_TRANSLATION = "Please input a valid book translation";
    public static final String INVALID_BOOK_REVIEW = "Please write a valid review";
    public static final String INVALID_AUTHOR_ID = "Please input a valid author Id";
    public static final String INVALID_PUBLISHER_ID = "Please input a valid publisher Id";
    public static final String INVALID_GENRE_ID = "Please input a valid genre Id";
    public static final String INVALID_TRANSLATION_ID = "Please input a valid translation Id";


    // AUTHOR CONTROLLER
    public static final String AUTHOR_WITH_ID = "Author with id: ";
    public static final String AUTHOR_WITH_NAME = "Author with name: ";
    public static final String AUTHOR_ALREADY_EXISTS = "Author already exists.";


    // GENRE CONTROLLER

    public static final String GENRE_WITH_ID = "Genre with id: ";
    public static final String GENRE_WITH_NAME = "Genre with name: ";
    public static final String GENRE_ALREADY_EXISTS = "Genre already exists.";


    // PUBLISHER CONTROLLER
    public static final String PUBLISHER_WITH_ID = "Publisher with id: ";
    public static final String PUBLISHER_WITH_NAME = "Publisher with name: ";
    public static final String PUBLISHER_ALREADY_EXISTS = "Publisher already exists.";


    //RATING CONTROLLER
    public static final String RATING_WITH_ID = "Rating with id: ";


    //REVIEW SERVICE
    public static final String REVIEW_WITH_ID = "Review with id: ";
    public static final String REVIEW_SAME_CUSTOMER = "You have already written a review about this book.";

    public static final String NO_REVIEW = "No reviews for this book yet";


    //TRANSLATION CONTROLLER
    public static final String TRANSLATION_WITH_ID = "Translation with id: ";
    public static final String TRANSLATION_WITH_NAME = "Translation with name: ";
    public static final String TRANSLATION_ALREADY_EXISTS = "Translation already exists.";


    // DELETE MESSAGE
    public static final String CANNOT_BE_DELETED = "Cannot de deleted because it is being used in another table";
    public static final String TOP_RATED_BOOK_ADDED = "Top rated books had been added";


    //ORDER
    public static final String ORDERMODEL_WITH_ID = "Order with id: ";
    public static final String ORDERMODEL_ALREADY_EXISTS = "Order already exists.";


    //ORDERITEM

    public static final String ORDERITEM_WITH_ID = "Order item with id: ";
    public static final String ORDERITEM_ALREADY_EXISTS = "Order item already exists.";


    //INVOICE

    public static final String INVOICE_WITH_ID = "Invoice with id: ";
    public static final String INVOICE_WITH_INVOICE_NUMBER = "Invoice with invoice number: ";
    public static final String INVOICE_ALREADY_EXISTS = "Invoice already exists.";
    public static final String NO_INVOICE_WITH_CUSTOMER = "No invoices with customer: ";


    //PAYMENT

    public static final String PAYMENT_WITH_ID = "Payment with id: ";
    public static final String PAYMENT_ALREADY_EXISTS = "Payment already exists.";

    // DOWNLOAD
    public static final String DOWNLOAD_WITH_ID = "Download with id: ";
    public static final String DOWNLOAD_ALREADY_EXISTS = "Download already exists.";
    public static final String NO_DOWNLOAD_WITH_ORDER = "No downloads with order: ";


    //ORDER RELATED DTO
    public static final String INVALID_CUSTOMER_ID = "Please input a valid customer Id";
    public static final String INVALID_ORDERITEM_ID = "Please input a valid order item Id ";
    public static final String INVALID_AMOUNT = "Please input a valid amount";
    public static final String INVALID_VAT = "Please input a valid Value Added Tax";
    public static final String INVALID_BOOK_ID = "Please input a valid book Id";
    public static final String NO_ORDER_WITH_BOOK = "No orders with book: ";
    public static final String NO_ORDER_WITH_CUSTOMER = "No orders with customer: ";


}
