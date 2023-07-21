package web.digitallibrary.contollers;
/*
    =====================================
    @project DigitalLibrary
    @created 21/01/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.digitallibrary.DAO.BookDAO;
import web.digitallibrary.DAO.ClientDAO;
import web.digitallibrary.DAO.OrderDAO;
import web.digitallibrary.model.Order;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderDAO orderDAO;
    private final BookDAO bookDAO;
    private final ClientDAO clientDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO, BookDAO bookDAO, ClientDAO clientDAO) {
        this.orderDAO = orderDAO;
        this.bookDAO = bookDAO;
        this.clientDAO = clientDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("orderList", orderDAO.getAll());
        return "orders/showAll";
    }

    @PostMapping()
    public String add(@ModelAttribute("order") Order order) {
        order.setClientName(clientDAO.getById(order.getClientId()).getName());
        orderDAO.save(order);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.setFree(orderDAO.getById(id).getBookId());
        orderDAO.delete(id);
        return "redirect:/orders";
    }
}
