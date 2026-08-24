package FakeCommerceApp.demo.schema;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class OrderSchema extends BaseEntity{
    @Column(name = "status")
    private OrderStatus orderStatus;

    // @ManyToMany
    // @JoinTable(
    //     name = "order_products",
    //     joinColumns = @JoinColumn(name = "order_id"), // Now this FK belongs to same schema ---- Order
    //     inverseJoinColumns = @JoinColumn( name = "product_id") // Now this FK belongs to other schema ---- Product
    // )
    // List<Product> products;
}
