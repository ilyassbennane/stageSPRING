package com.ramadan.api.services.brand;
import com.ramadan.api.dto.MapClassWithDto;

import com.ramadan.api.dto.brand.BrandDto;
import com.ramadan.api.dto.brand.BrandRequestDto;
import com.ramadan.api.dto.brand.BrandResponseDto;
import com.ramadan.api.dto.brand.BrandSearchRequestDto;
import com.ramadan.api.dto.brand.BrandUpdateDto;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.product.Brand;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.product.BrandRepository;
import com.ramadan.api.services.company.CompanyService;
import com.ramadan.api.services.keycloak.IKeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandService implements IBrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MapClassWithDto<Brand, BrandRequestDto> mapBrandWithRequestDto;

    @Autowired
    private MapClassWithDto<Brand, BrandResponseDto> mapBrandWithResponseDto;
    
    @Autowired
    private MapClassWithDto<Brand, BrandDto> mapBrandWithBrandDto;


	@Autowired
	private IKeycloakService keycloakService;
    
    @Override
    public BrandDto getBrandByUuid(String uuid) throws APIErrorException {
    	if (Objects.isNull(uuid) || uuid.trim().isEmpty()) {
			throw new APIErrorException(ErrorCode.B002);
		}
    		Brand obrand = checkBrandUuid(uuid);
            return mapBrandWithBrandDto.convertToDto(obrand, BrandDto.class);
        
    }

    @Override
    public Map<String, Object> getAllByCompany(String companyUuid, int page, int size, String[] sort) throws APIErrorException {
        Company company = companyService.checkCompanyUuid(companyUuid);
        if (Objects.nonNull(company)) {
            List<Sort.Order> orders = Utils.getListOrderBySort(sort);
            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

            Page<Brand> brandPage = brandRepository.findAllByCompany(companyUuid, pageable);
            if (brandPage.hasContent()) {
                List<BrandResponseDto> brands = brandPage.getContent().stream()
                        .map(brand -> mapBrandWithResponseDto.convertToDto(brand, BrandResponseDto.class))
                        .collect(Collectors.toList());

                Map<String, Object> response = new HashMap<>();
                response.put("brands", brands);
                response.put("currentPage", brandPage.getNumber());
                response.put("totalItems", brandPage.getTotalElements());
                response.put("totalPages", brandPage.getTotalPages());

                return response;
            } else {
                throw new APIErrorException(ErrorCode.B003);
            }
        } else {
            throw new APIErrorException(ErrorCode.A010);
        }
    }

    @Override
    public BrandResponseDto saveBrand(BrandRequestDto brandRequestDto) throws APIErrorException {
        if (Objects.isNull(brandRequestDto)){
        	 throw new APIErrorException(ErrorCode.A507);
        }else {
        	User userConnected = keycloakService.getUserConnecter();
			Company oCompany = userConnected.getCompany();
			if (Objects.isNull(oCompany)) {
				throw new APIErrorException(ErrorCode.A0040);
			}
                Brand brand = mapBrandWithRequestDto.convertToEntity(brandRequestDto, Brand.class);
                brand.setCompany(oCompany);
                return mapBrandWithResponseDto.convertToDto(brandRepository.save(brand), BrandResponseDto.class);
        }
    }

    @Override
public BrandResponseDto updateBrand(BrandUpdateDto brandUpdateDto) throws APIErrorException {
    
    if (Objects.isNull(brandUpdateDto)) {
        throw new APIErrorException(ErrorCode.A507);
    } else {
    	Brand oBrand = checkBrandUuid(brandUpdateDto.getUuid());
        if (Objects.nonNull(brandUpdateDto.getName())  && !brandUpdateDto.getName().isEmpty()) {
        	oBrand.setName(brandUpdateDto.getName());
        }
        if (Objects.nonNull(brandUpdateDto.getDescription())  && !brandUpdateDto.getDescription().isEmpty()) {
        	oBrand.setDescription(brandUpdateDto.getDescription());
        }
        if (Objects.nonNull(brandUpdateDto.getRang()) ) {
        	oBrand.setRang(brandUpdateDto.getRang());
        }

        Brand updatedBrand = brandRepository.save(oBrand);
        return mapBrandWithResponseDto.convertToDto(updatedBrand, BrandResponseDto.class);
    }
}



    @Override
    public void deleteBrand(String uuid) throws APIErrorException {
        Brand brand = checkBrandUuid(uuid) ;
        if (Objects.nonNull(brand)) {
            brand.setDelete(true);
            brandRepository.save(brand);
        } else {
            throw new APIErrorException(ErrorCode.B001);
        }
    }
    
    
    
	@Override
    public Brand checkBrandUuid(String uuid) throws APIErrorException {
	    if (Objects.isNull(uuid)) {
            throw new APIErrorException(ErrorCode.B006);
        }
	    Brand oBrand= brandRepository.findByUuid(uuid);
        if (Objects.isNull(oBrand)) {
            throw new APIErrorException(ErrorCode.B001);
        }       
        return oBrand;
    }
    

    @Override
    public Map<String, Object> getAllBrands(int page, int size, String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<Brand> brandPage = brandRepository.findAll(pageable);

        List<BrandDto> brands = brandPage.getContent().stream()
                .map(brand -> mapBrandWithBrandDto.convertToDto(brand, BrandDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("brands", brands);
        response.put("currentPage", brandPage.getNumber());
        response.put("totalItems", brandPage.getTotalElements());
        response.put("totalPages", brandPage.getTotalPages());

        return response;
    }

	@Override
	public Map<String, Object> search(BrandSearchRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException {

		List<Order> orders = Utils.getListOrderBySort(sort);

		List<Brand> lBrand = new ArrayList<Brand>();

		Pageable oPageable = PageRequest.of(page, size, Sort.by(orders));

		Page<Brand> pBrand;

		if (Objects.nonNull(searchRequestDto)) {
			BrandSpecification specification = new BrandSpecification(searchRequestDto);
			pBrand = brandRepository.findAll(specification,oPageable);

		}else pBrand = brandRepository.findAll(oPageable);

		lBrand = pBrand.getContent();
		List<BrandResponseDto> lBrandDto = mapBrandWithResponseDto.convertListToListDto(lBrand, BrandResponseDto.class);
		Map<String, Object> lBrandMap = new HashMap<>();
		lBrandMap.put("result", lBrandDto);
		lBrandMap.put("currentPage", pBrand.getNumber());
		lBrandMap.put("totalItems", pBrand.getTotalElements());
		lBrandMap.put("totalPages", pBrand.getTotalPages());

		return lBrandMap;
	}
}
