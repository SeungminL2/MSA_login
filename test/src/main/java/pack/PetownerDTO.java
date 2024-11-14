package pack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PetownerDTO {
	private Long petownerId;
	private String name;
	private String phone;
    private String email;
    private String password;
    
    public static PetownerDTO toPetownerDTO(Petowner petowner) {
    	PetownerDTO petownerDTO = new PetownerDTO();
    	petownerDTO.setPetownerId(petowner.getPetownerId());
    	petownerDTO.setName(petowner.getName());
    	petownerDTO.setEmail(petowner.getEmail());
    	petownerDTO.setPhone(petowner.getPhone());
    	petownerDTO.setPassword(petowner.getPassword());
    	return petownerDTO;
    }
}
