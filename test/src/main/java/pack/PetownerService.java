package pack;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetownerService {
	
	private final PetownerRepository petownerRepository;
	
	public void signup(PetownerDTO petownerDTO) {
		Petowner petowner = Petowner.toPetownerEntity(petownerDTO);
		petownerRepository.save(petowner);
	}
	
	public String emailCheck(String petownerEmail) {
        Optional<Petowner> byEmail = petownerRepository.findByEmail(petownerEmail);
        if (byEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }
	
	// ------------------
	
	public PetownerDTO findById(Long petownerId) {
		Optional<Petowner> optionalPetowner = petownerRepository.findById(petownerId);
		if (optionalPetowner.isPresent()) {
			return PetownerDTO.toPetownerDTO(optionalPetowner.get());
		}
		else {
			return null;
		}
	}
	
	// 추가
	public PetownerDTO findByEmail(String petownerEmail) {
		Optional<Petowner> byEmail = petownerRepository.findByEmail(petownerEmail);
		if (byEmail.isPresent()) {
			return PetownerDTO.toPetownerDTO(byEmail.get());
		}
		else {
			return null;
		}
	}
	
	// ------------------
	
	public PetownerDTO login(PetownerDTO petownerDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<Petowner> byEmail = petownerRepository.findByEmail(petownerDTO.getEmail());
        if (byEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
        	Petowner petowner = byEmail.get();
            if (petowner.getPassword().equals(petownerDTO.getPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
            	PetownerDTO petownerDTO2 = PetownerDTO.toPetownerDTO(petowner);
                return petownerDTO2;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }
	
	
	// ------------
	
	public PetownerDTO updateForm(String petownerEmail) {
        Optional<Petowner> optionalPetowner = petownerRepository.findByEmail(petownerEmail);
        if (optionalPetowner.isPresent()) {
            return PetownerDTO.toPetownerDTO(optionalPetowner.get());
        } else {
            return null;
        }
    }

//    public void update(PetownerDTO petownerDTO) {
//    	petownerRepository.save(Petowner.toUpdatePetowner(petownerDTO));
//    }
	public void update(PetownerDTO petownerDTO) {
	    // 기존 데이터를 조회
	    Optional<Petowner> optionalPetowner = petownerRepository.findById(petownerDTO.getPetownerId());
	    
	    if (optionalPetowner.isPresent()) {
	        // 기존 데이터가 존재하는 경우에만 업데이트 수행
	        Petowner existingPetowner = optionalPetowner.get();
	        existingPetowner.setPassword(petownerDTO.getPassword());
	        existingPetowner.setName(petownerDTO.getName());
	        existingPetowner.setPhone(petownerDTO.getPhone());
	        // 필요한 필드 업데이트

	        petownerRepository.save(existingPetowner); // 기존 엔티티를 저장하여 업데이트
	    } else {
	        throw new RuntimeException("해당 사용자가 존재하지 않습니다.");
	    }
	}

}
