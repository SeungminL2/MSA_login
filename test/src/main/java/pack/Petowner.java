package pack;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Petowner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "petowner_id", updatable = false)
	private Long petownerId;
	
	@Column(name = "name", nullable = false)
    private String name;
	
	@Column(name = "phone", nullable = false, unique = true)
    private String phone;
	
	@Column(name = "email", nullable = false, unique = true)
    private String email;
	
	@Column(name = "password", nullable = false)
    private String password;
	
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public static Petowner toPetownerEntity(PetownerDTO petownerDTO) {
    	Petowner petowner = new Petowner();
    	petowner.setName(petownerDTO.getName());
    	petowner.setPhone(petownerDTO.getPhone());
    	petowner.setEmail(petownerDTO.getEmail());
    	petowner.setPassword(petownerDTO.getPassword());
    	return petowner;
    }
    
    public static Petowner toUpdatePetowner(PetownerDTO petownerDTO) {
    	Petowner petowner = new Petowner();
    	petowner.setName(petownerDTO.getName());
    	petowner.setPhone(petownerDTO.getPhone());
    	petowner.setEmail(petownerDTO.getEmail());
    	petowner.setPassword(petownerDTO.getPassword());
    	return petowner;
    }
}
