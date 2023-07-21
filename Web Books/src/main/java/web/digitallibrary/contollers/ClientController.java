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
import web.digitallibrary.DAO.ClientDAO;
import web.digitallibrary.DAO.GenreDAO;
import web.digitallibrary.model.Client;
import web.digitallibrary.util.ClientValidator;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientDAO clientDAO;
    private final GenreDAO genreDAO;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientDAO clientDAO, GenreDAO genreDAO, ClientValidator clientValidator) {
        this.clientDAO = clientDAO;
        this.genreDAO = genreDAO;
        this.clientValidator = clientValidator;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("clientList", clientDAO.getAll());
        return "clients/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientDAO.getById(id));
        model.addAttribute("orderList", clientDAO.getOrders(id));
        return "clients/showById";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("client") Client client, Model model) {
        model.addAttribute("genreList", genreDAO.getAll());
        return "clients/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult, Model model) {
        clientValidator.validate(client, bindingResult);
        model.addAttribute("genreList", genreDAO.getAll());
        if (bindingResult.hasErrors()) {
            return "clients/new";
        }
        clientDAO.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientDAO.getById(id));
        model.addAttribute("genreList", genreDAO.getAll());
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
        @PathVariable("id") int id, Model model) {
        clientValidator.validate(client, bindingResult);
        model.addAttribute("genreList", genreDAO.getAll());
        if (bindingResult.hasErrors()) {
            return "clients/edit";
        }
        clientDAO.update(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientDAO.delete(id);
        return "redirect:/clients";
    }
}
