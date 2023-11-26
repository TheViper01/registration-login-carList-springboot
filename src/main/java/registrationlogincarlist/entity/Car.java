package registrationlogincarlist.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false, unique=true)
    private String licensePlate;

    @Column(nullable=false)
    private String brand;
    @Column(nullable=false)
    private String model;
    @Column(nullable=false)
    private String color;
    @Column(nullable=false)
    private int manufactureYear;
    @Column(nullable=false)
    private float engineCapacity;
    @Column(nullable=false)
    private String fuel;
    @Column(nullable=false)
    private float horsepower;
    @Column(nullable=false)
    private float torque;
    @Column(nullable=false)
    private float trunkVolume;
    @Column(nullable=false)
    private float price;

    @ManyToOne
    @JoinColumn(name="user", nullable=false)
    private User user;
}
