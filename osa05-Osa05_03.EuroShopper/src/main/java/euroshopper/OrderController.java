package euroshopper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {


    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ShoppingCart shoppingCart;
    
    

    @RequestMapping("/orders")
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }

    @PostMapping("/orders")
    public String order(@RequestParam String name, @RequestParam String address) {

        Order order = new Order();
        order.setName(name);
        order.setAddress(address);
        List<OrderItem> itemsList = new ArrayList<OrderItem>();
        for (Map.Entry<Item, Long> item : shoppingCart.getItems().entrySet()) {
            OrderItem orderItem = new OrderItem(item.getKey(), item.getValue());
            itemsList.add(orderItem);
        }
        order.setOrderItems(itemsList);

        orderRepository.save(order);
        shoppingCart.setEmpty();
        

        return "redirect:/orders";
    }
}
