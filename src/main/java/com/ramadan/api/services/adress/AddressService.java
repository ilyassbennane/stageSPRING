package com.ramadan.api.services.adress;
import java.util.HashMap;



import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.address.model.AddressCompanyRequestDto;
import com.ramadan.api.dto.address.model.AddressDepositRequestDto;
import com.ramadan.api.dto.address.model.AddressRequestDto;
import com.ramadan.api.dto.address.model.AddressDto;
import com.ramadan.api.dto.address.model.AddressUpdateDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.entity.address.Address;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.referentiel.Ville;
import com.ramadan.api.entity.stock.deposit.Deposit;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.global.AddressRepository;
import com.ramadan.api.repository.referentiel.VilleRepository;
import com.ramadan.api.services.agence.IAgenceService;
import com.ramadan.api.services.company.ICompanyService;

@Service
@Transactional
public class AddressService implements  IAddressService {
    

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IAgenceService agenceService;
  //  @Autowired
 //   private IDepotService depotService;
//    @Autowired
//    private ICostumerService iCostumerService;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private MapClassWithDto<Agency, AgenceResponseDto> mapAgenceWithResponseDto;
    @Autowired
    private MapClassWithDto<Company, CompanyResponseDto> mapCompanyWithResponseDto;
//    @Autowired
//    private MapClassWithDto<Costumer, CostumerResponseDto> mapCostumerWithResponseDto;
    @Autowired
    private MapClassWithDto<Deposit, DepotResponseDto> mapDepositWithResponseDto;

    @Autowired
    private MapClassWithDto<Address, AddressRequestDto> mapClassWithRequestDto;

    @Autowired
    private MapClassWithDto<Address, AddressDto> mapClassWithResponseDto;


    public AddressDto findByUuid(String uuid) throws APIErrorException {        Address address = addressRepository.findByUuid(uuid);        if (address != null) {
            throw new APIErrorException(ErrorCode.A001);        }
    else {            return mapClassWithResponseDto.convertToDto(address, AddressDto.class);
        }
    }

    public AddressDto saveAddressCompany(AddressCompanyRequestDto addressRequestDto) throws APIErrorException {
        if (addressRequestDto == null) {
            throw new APIErrorException(ErrorCode.A507);
        }
        else {
            CompanyResponseDto companyResponseDto = companyService.getCompanyByUuid(addressRequestDto.getCompanyUuid());
            if (companyResponseDto == null) {
                throw new APIErrorException(ErrorCode.A010);
            }
            else {
                Address address = mapClassWithRequestDto.convertToEntity(addressRequestDto, Address.class);
                Company company = mapCompanyWithResponseDto.convertToEntity(companyResponseDto, Company.class);
                address.setCompany(company);
                Address savedAddress = addressRepository.save(address);
                return mapClassWithResponseDto.convertToDto(savedAddress, AddressDto.class);
            }
              }
    }
//    public AddressDto saveAddressAgency(AddressAgencyRequestDto addressRequestDto) throws APIErrorException {
//        if (addressRequestDto == null) {
//            throw new APIErrorException(ErrorCode.A507);
//        }
//        else {
//            AgenceResponseDto agenceResponseDto = agenceService.findByUuid(addressRequestDto.getAgencyUuid());
//            if (agenceResponseDto == null) {
//                throw new APIErrorException(ErrorCode.A001);
//            }
//            else {
//                Address address = mapClassWithRequestDto.convertToEntity(addressRequestDto, Address.class);
//                Agency agency = mapAgenceWithResponseDto.convertToEntity(agenceResponseDto, Agency.class);
//                address.setAgency(agency);
//                Address savedAddress = addressRepository.save(address);
//                return mapClassWithResponseDto.convertToDto(savedAddress, AddressDto.class);
//            }
//        }
//    }
//    public AddressDto saveAddressDeposit(AddressDepositRequestDto addressRequestDto) throws APIErrorException {
//        if (addressRequestDto == null) {
//            throw new APIErrorException(ErrorCode.A507);
//        }
//        else {
//            DepotResponseDto depotResponseDto = depotService.findByUuid(addressRequestDto.getDepositUuid());
//            if (depotResponseDto == null) {
//                throw new APIErrorException(ErrorCode.A001);
//            }
//            else {
//                Address address = mapClassWithRequestDto.convertToEntity(addressRequestDto, Address.class);
//                Deposit deposit = mapDepositWithResponseDto.convertToEntity(depotResponseDto, Deposit.class);
//                address.setDeposit(deposit);
//                Address savedAddress = addressRepository.save(address);
//                return mapClassWithResponseDto.convertToDto(savedAddress, AddressDto.class);
//            }
//        }
//    }
//    public AddressDto saveAddressCostumer(AddressCostumerRequestDto addressRequestDto) throws APIErrorException {
//        if (addressRequestDto == null) {
//            throw new APIErrorException(ErrorCode.A507);
//        }
//        else {
//            CostumerResponseDto costumerResponseDto = iCostumerService.findByUuid(addressRequestDto.getCostumerUuid());
//            if (costumerResponseDto == null) {
//                throw new APIErrorException(ErrorCode.A001);
//            }
//            else {
//                Address address = mapClassWithRequestDto.convertToEntity(addressRequestDto, Address.class);
//                Costumer costumer = mapCostumerWithResponseDto.convertToEntity(costumerResponseDto, Costumer.class);
//                address.setCostumer(costumer);
//                Address savedAddress = addressRepository.save(address);
//                return mapClassWithResponseDto.convertToDto(savedAddress, AddressDto.class);
//            }
//        }
//    }
    public AddressDto updateAddress(String uuid,AddressUpdateDto addressRequestDto) throws APIErrorException {
        if (addressRequestDto == null){
            throw new APIErrorException(ErrorCode.A002);
        } 
         else {  
            Address existingAddress = addressRepository.findByUuid(uuid); 
             if (existingAddress == null) {
              throw new APIErrorException(ErrorCode.A001);
                } 
                else {
                     if (addressRequestDto.getAdresse1()!= null) {
                        existingAddress.setAdresse1(addressRequestDto.getAdresse1());
                    }
                    if (addressRequestDto.getAdresse2()!= null) {
                        existingAddress.setAdresse2(addressRequestDto.getAdresse2());
                    }
                    if (addressRequestDto.getAdresse3()!= null) {
                        existingAddress.setAdresse3(addressRequestDto.getAdresse3());
                    }
                    if (addressRequestDto.getCodeVille()!= null) {
                      Ville ville = villeRepository.findByCodeOrUuid(addressRequestDto.getCodeVille());
                      if (ville==null) {
                        throw new ApiKeyException(ErrorCode.A001);
                      }
                      else{
                        existingAddress.setCodeVille(addressRequestDto.getCodeVille());
                      }
                    }
                         
        Address savedAddress = addressRepository.save(existingAddress);
        return mapClassWithResponseDto.convertToDto(savedAddress, AddressDto.class);
    }
  }
}
   public List<AddressDto> findAllByCompany(String uuid) throws APIErrorException {
        List<Address> addresses = addressRepository.findAllByCompany(uuid);
        if (addresses.isEmpty()) {
            throw new APIErrorException(ErrorCode.A006);
        } else {
            return mapClassWithResponseDto.convertListToListDto(addresses, AddressDto.class);
        }
    }

    public List<AddressDto> findAllByCostumer(String uuid) throws APIErrorException {
        List<Address> addresses = addressRepository.findAllByCostumer(uuid);
        if (addresses.isEmpty()) {
            throw new APIErrorException(ErrorCode.A006);
        } else {
            return mapClassWithResponseDto.convertListToListDto(addresses, AddressDto.class);
        }
    }

    public List<AddressDto> findAllByAgency(String uuid) throws APIErrorException {
        List<Address> addresses = addressRepository.findAllByAgency(uuid);
        if (addresses.isEmpty()) {
            throw new APIErrorException(ErrorCode.A006);
        } else {
            return mapClassWithResponseDto.convertListToListDto(addresses, AddressDto.class);
        }
    }

    public List<AddressDto> findAllByDeposit(String uuid) throws APIErrorException {
        List<Address> addresses = addressRepository.findAllByDeposit(uuid);
        if (addresses.isEmpty()) {
            throw new APIErrorException(ErrorCode.A006);
        } else {
            return mapClassWithResponseDto.convertListToListDto(addresses, AddressDto.class);
        }
    }

//    public List<AddressResponseDto> findAll() throws APIErrorException {
//        List<Address> addresses = addressRepository.findAll();
//        if (addresses.isEmpty()) {
//            throw new APIErrorException(ErrorCode.A006);
//        } else {
//            return mapClassWithResponseDto.convertListToListDto(addresses, AddressResponseDto.class);
//        }
//    }
//    
    
    public Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<Address> addressPage = addressRepository.findAll(pageable);

      
            List<AddressDto> addresses = addressPage.getContent().stream()
                    .map(address -> mapClassWithResponseDto.convertToDto(address, AddressDto.class))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("agences", addresses);
            response.put("currentPage", addressPage.getNumber());
            response.put("totalItems", addressPage.getTotalElements());
            response.put("totalPages", addressPage.getTotalPages());

            return response;
        
    }
    
    

    public void deleteAddress(String uuid) throws APIErrorException {
        Address address = addressRepository.findByUuid(uuid);
        if (address != null) {
            address.setDelete(true);
            addressRepository.delete(address);
        } else {
            throw new APIErrorException(ErrorCode.A001);
        }
    }

//	@Override
//	public AddressDto saveAddressCostumer(AddressCostumerRequestDto addressRequestDto) throws APIErrorException {
//		// TODO Auto-generated method stub
//		return null;
//	}
}

