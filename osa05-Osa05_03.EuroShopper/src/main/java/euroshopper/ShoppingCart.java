package euroshopper;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {
    private Map<Item, Long> items = new HashMap<Item, Long>(); 
    
    /**
     * Laittaa yhden kappaleen tuotteita ostoskoriin
     * @param item tuote joka lisätään
     */
    public void addToCart(Item item) {
        Long lkm = 0L;
        if (items.containsKey(item) && !items.isEmpty()) {
            lkm = items.get(item);
        }
        lkm++;
        items.put(item, lkm);
    }
    
    /**
     * Palautaa ostoskorin kokonaissumman
     * @return ostoskorin kokonaissumma
     */
    public double getSum(){
        double sum = 0L;
        for (Map.Entry<Item, Long> item : items.entrySet()) {
            sum += item.getValue()*item.getKey().getPrice();
        }
        return sum;
    }
    
    
    public void setEmpty() {
        items.clear();
    }
}
