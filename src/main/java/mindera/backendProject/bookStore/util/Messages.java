package mindera.backendProject.bookStore.util;

public class Messages {

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


    //REVIEW CONTROLLER
    public static final String REVIEW_WITH_ID = "Review with id: ";
    public static final String REVIEW_SAME_CUSTOMER = "You have already written a review about this book.";


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


    //ORDER RELATED DTO
    public static final String INVALID_CUSTOMER_ID = "Please input a valid customer Id";
    public static final String INVALID_ORDERITEM_ID = "Please input a valid order item Id ";
    public static final String INVALID_AMOUNT = "Please input a valid amount";
    public static final String INVALID_VAT = "Please input a valid Value Added Tax";
    public static final String INVALID_BOOK_ID = "Please input a valid book Id";
    public static final String NO_ORDER_WITH_BOOK = "No orders with book: ";
    public static final String NO_ORDER_WITH_CUSTOMER = "No orders with customer: ";

}
