package id.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Item")
public class Item extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "ItemSequence", sequenceName = "item_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "ItemSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "itemId")
    public Long id;
    @Column(name = "name")
    public String name;
    @Column(name = "count")
    public String count;
    @Column(name = "price")
    public String price;
    @Column(name = "type")
    public String type;
    @Column(name = "description")
    public String description;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    public LocalDateTime updateAt;
}
