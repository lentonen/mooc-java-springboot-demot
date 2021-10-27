/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todoapplication;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Henri
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item extends AbstractPersistable<Long> {

    private String name;
    private int checked;

    public Item(String name) {
        this.name = name;
    }
    
    public Item kasvataCounter() {
        this.checked ++;
        return this;
    }
    
}
