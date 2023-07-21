package web.digitallibrary.contollers;

/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023
    @author Bereznev Nikita @CreativeWex
    =====================================
 */
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.digitallibrary.DAO.*;
import web.digitallibrary.model.Book;
import web.digitallibrary.model.Order;
import web.digitallibrary.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final GenreDAO genreDAO;
    private final BookValidator bookValidator;
    private final AuthorDAO authorDAO;
    private final ClientDAO clientDAO;
    private final OrderDAO orderDAO;

    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator, GenreDAO genreDAO, AuthorDAO authorDAO,
                          ClientDAO clientDAO, OrderDAO orderDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.genreDAO = genreDAO;
        this.authorDAO = authorDAO;
        this.clientDAO = clientDAO;
        this.orderDAO = orderDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("bookList", bookDAO.getAll());
        return "books/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getById(id);
        model.addAttribute("book", bookDAO.getById(id));
        model.addAttribute("genreList", genreDAO.getAll());
        model.addAttribute("clientList", clientDAO.getAll());
        if (book.getStatus().equals("Взята")) {
            Order foundOrder = bookDAO.findOrder(id);
            model.addAttribute("order", foundOrder);
            model.addAttribute("bookOwner", clientDAO.getById(bookDAO.findOrder(id).getClientId()));
        } else {
            Order order = new Order();
            order.setBookId(book.getId());
            order.setBookName(book.getName());
            model.addAttribute("order", order);
        }
        return "books/showById";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("genreList", genreDAO.getAll());
        model.addAttribute("authorList", authorDAO.getAll());
        return "books/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model) {
        bookValidator.validate(book, bindingResult);
        model.addAttribute("genreList", genreDAO.getAll());
        model.addAttribute("authorList", authorDAO.getAll());
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getById(id));
        model.addAttribute("genreList", genreDAO.getAll());
        model.addAttribute("authorList", authorDAO.getAll());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @PathVariable("id") int id, Model model) {
        bookValidator.validate(book, bindingResult);
        model.addAttribute("genreList", genreDAO.getAll());
        model.addAttribute("authorList", authorDAO.getAll());
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
