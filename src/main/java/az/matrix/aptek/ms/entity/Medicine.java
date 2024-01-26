package az.matrix.aptek.ms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Medicines",schema ="public")
@ToString
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;

    @JoinColumn(name="branch_id", referencedColumnName = "id")
    @ManyToOne
    private Branch branch;

    private Integer quantity;
    private Float price;

    @JoinColumn(name="category_id", referencedColumnName = "id")
    @ManyToOne
    private Category category;

    private String dosage;
    private Date createdDate;

    @OneToMany(mappedBy = "medicine")
    private List<Rating> ratings;
}

