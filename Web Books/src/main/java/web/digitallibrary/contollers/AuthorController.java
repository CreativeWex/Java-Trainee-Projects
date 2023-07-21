package web.digitallibrary.contollers;

/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023
    @author Bereznev Nikita @CreativeWex
    =====================================
 */
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.digitallibrary.DAO.AuthorDAO;
import web.digitallibrary.model.Author;
import web.digitallibrary.util.AuthorValidator;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorDAO authorDAO;
    private final AuthorValidator authorValidator;

    @Autowired
    public AuthorController(AuthorDAO authorDAO, AuthorValidator authorValidator) {
        this.authorDAO = authorDAO;
        this.authorValidator = authorValidator;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("authorList", authorDAO.getAll());
        return "authors/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.getById(id));
        model.addAttribute("bookList", authorDAO.findBooks(id));
        return "authors/showById";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("author") Author author) {
        return "authors/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        authorValidator.validate(author, bindingResult);
        if (bindingResult.hasErrors()) {
            return "authors/new";
        }
        authorDAO.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.getById(id));
        return "authors/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult,
                       @PathVariable("id") int id) {
        authorValidator.validate(author, bindingResult);
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        authorDAO.update(id, author);
        return "redirect:/authors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        authorDAO.delete(id);
        return "redirect:/authors";
    }
}
