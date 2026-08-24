package FakeCommerceApp.demo.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity{
    
    @Column(nullable = false)
    private String name;
}
