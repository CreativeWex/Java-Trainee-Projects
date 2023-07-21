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
import web.digitallibrary.DAO.GenreDAO;
import web.digitallibrary.model.Genre;
import web.digitallibrary.util.GenreValidator;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private final GenreDAO genreDAO;
    private final GenreValidator genreValidator;

    @Autowired
    public GenreController(GenreDAO genreDAO, GenreValidator genreValidator) {
        this.genreValidator = genreValidator;
        this.genreDAO = genreDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("genreList", genreDAO.getAll());
        return "genres/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("genre", genreDAO.getById(id));
        model.addAttribute("numberOfFollowers", genreDAO.countPeopleForGenre(id));
        model.addAttribute("booksList", genreDAO.findByGenre(id));
        return "genres/showById";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("genre") Genre genre) {
        return "genres/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("genre") @Valid Genre genre, BindingResult bindingResult) {
        genreValidator.validate(genre, bindingResult);
        if (bindingResult.hasErrors()) {
            return "genres/new";
        }
        genreDAO.save(genre);
        return "redirect:/genres";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("genre", genreDAO.getById(id));
        return "genres/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("genre") @Valid Genre genre, BindingResult bindingResult,
            @PathVariable("id") int id) {
        genreValidator.validate(genre, bindingResult);
        if (bindingResult.hasErrors()) {
            return "genres/edit";
        }
        genreDAO.update(id, genre);
        return "redirect:/genres";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        genreDAO.delete(id);
        return "redirect:/genres";
    }
}
