package com.ramadan.api.services.categorie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ramadan.api.helpers.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.categorie.model.CategorieRequestDto;
import com.ramadan.api.dto.categorie.model.CategorieResponceDto;
import com.ramadan.api.dto.categorie.model.CategorieUpdateDto;
import com.ramadan.api.dto.categorie.model.CategoryChildDto;
import com.ramadan.api.dto.categorie.service.ICategrieMapperService;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.product.ProductCategory;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.company.CompanyRepository;
import com.ramadan.api.repository.product.CategorieRepository;

@Service
@Transactional
public class CategorieService implements ICategorieService{

	  @Autowired
	    private CategorieRepository categorieRepository;

	    @Autowired
	    private MapClassWithDto<ProductCategory, CategorieRequestDto> mapClassWithRequestDto;

        @Autowired
        private MapClassWithDto<ProductCategory, CategoryChildDto> mapClassWithchildDto;

	    @Autowired
	    private MapClassWithDto<ProductCategory, CategorieResponceDto> mapClassWithResponseDto;

	    @Autowired
	    private CompanyRepository companyRepository;


    /**
     * Finds a category by its UUID.
     *
     * @param uuid the UUID of the category
     * @return the found category as a CategorieResponceDto
     * @throws APIErrorException if the category is not found
     */
    @Override
    public CategorieResponceDto findByUuid(String uuid) throws APIErrorException {
        ProductCategory categorie = categorieRepository.findByUuid(uuid);
        if (categorie != null) {
            return mapClassWithResponseDto.convertToDto(categorie, CategorieResponceDto.class);
        }
        else throw new APIErrorException(ErrorCode.A008);
    }

    /**
     * Finds all categories belonging to a company identified by UUID.
     *
     * @param uuid the UUID of the company
     * @return a list of categories as CategorieResponceDtos
     * @throws APIErrorException if the company is not found or if there are no categories for the company
     */
    @Override
    public Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort) throws APIErrorException {
        Company company = companyRepository.findByUuid(uuid);
        if (company != null) {
            List<Sort.Order> orders = Utils.getListOrderBySort(sort);
            Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
            Page<ProductCategory> categoriesPage = categorieRepository.findAllByCompany(uuid, pageable);
            if (categoriesPage.hasContent()) {
                List<CategorieResponceDto> categories = categoriesPage.getContent().stream()
                        .map(category -> mapClassWithResponseDto.convertToDto(category, CategorieResponceDto.class))
                        .collect(Collectors.toList());
    
                Map<String, Object> response = new HashMap<>();
                response.put("categories", categories);
                response.put("currentPage", categoriesPage.getNumber());
                response.put("totalItems", categoriesPage.getTotalElements());
                response.put("totalPages", categoriesPage.getTotalPages());
    
                return response;
            } else {
                throw new APIErrorException(ErrorCode.A508);
            }
        } else {
            throw new APIErrorException(ErrorCode.A004);
        }
    }
    

    /**
     * Finds all categories that are children of a parent category identified by UUID.
     *
     * @param uuid the UUID of the parent category
     * @return a list of child categories as CategorieResponceDtos
     * @throws APIErrorException 
     */
    @Override
    public Map<String, Object> findAllByCategorieParente(String uuid, int page, int size, String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<ProductCategory> categoryPage = categorieRepository.findAllByCategoryParente(uuid, pageable);

        if (categoryPage.hasContent()) {
            List<CategorieResponceDto> categories = categoryPage.getContent().stream()
                    .map(category -> mapClassWithResponseDto.convertToDto(category, CategorieResponceDto.class))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("currentPage", categoryPage.getNumber());
            response.put("totalItems", categoryPage.getTotalElements());
            response.put("totalPages", categoryPage.getTotalPages());

            return response;
        } else {
            throw new APIErrorException(ErrorCode.A006);
        }
    }

    /**
     * Saves a new category entity based on the provided CategorieRequestDto.
     *
     * @param requestDto the category data transfer object containing the information to be saved
     * @return the saved category as a CategorieResponceDto
     */
    @Override
    public CategorieResponceDto saveEntity(CategorieRequestDto requestDto) throws APIErrorException {
        if (requestDto==null){
            throw new APIErrorException(ErrorCode.A507);
        }
        else {
            Company company = companyRepository.findByUuid(requestDto.getCompanyUuid());
            if (company== null) {
               throw new APIErrorException(ErrorCode.A010);
            }
            else {
                ProductCategory categorie = mapClassWithRequestDto.convertToEntity(requestDto, ProductCategory.class);
                categorie.setCompany(company);
                ProductCategory savedCategorie = categorieRepository.save(categorie);
                return mapClassWithResponseDto.convertToDto(savedCategorie, CategorieResponceDto.class);
            }

        }
    }

    @Override
    public CategorieResponceDto saveEntityChild(CategoryChildDto requestDto) throws APIErrorException {
        if (requestDto==null){
            throw new APIErrorException(ErrorCode.A507);
        }
        else {
            Company company = companyRepository.findByUuid(requestDto.getCompanyUuid());
            if (company== null) {
                throw new APIErrorException(ErrorCode.A010);
            }
            else {
                if (requestDto.getParentUuid()==null){
                    throw new APIErrorException(ErrorCode.A506);
                }
                else {
                    ProductCategory categorie = mapClassWithchildDto.convertToEntity(requestDto, ProductCategory.class);
                    categorie.setCompany(company);
                    ProductCategory savedCategorie = categorieRepository.save(categorie);
                    return mapClassWithResponseDto.convertToDto(savedCategorie, CategorieResponceDto.class);
                }
            }

        }
    }

    /**
     * Updates an existing category identified by UUID with the information provided in CategorieUpdateDto.
     *
     * @param uuid the UUID of the category to update
     * @param updateDto the category data transfer object containing the updated information
     * @return the updated category as a CategorieResponceDto, or null if the category does not exist
     * @throws APIErrorException 
     */
    @Override
    public CategorieResponceDto updateEntity(String uuid, CategorieUpdateDto updateDto) throws APIErrorException {
        ProductCategory existingCategorie = categorieRepository.findByUuid(uuid);
        if (existingCategorie == null)
        {
            throw new APIErrorException(ErrorCode.A008);
        }
        else {
            // Update only non-null and non-empty fields
            if (updateDto.getName() != null && !updateDto.getName().isEmpty()) {
                existingCategorie.setName(updateDto.getName());
            }
            if (updateDto.getDescription() != null && !updateDto.getDescription().isEmpty()) {
                existingCategorie.setDescription(updateDto.getDescription());
            }

            ProductCategory updatedCategorie = categorieRepository.save(existingCategorie);
            return mapClassWithResponseDto.convertToDto(updatedCategorie, CategorieResponceDto.class);
        }
    }

    /**
     * Marks a category as deleted by its UUID.
     *
     * @param uuid the UUID of the category to mark as deleted
     * @throws APIErrorException 
     */
    @Override
    public void deleteCategorie(String uuid) throws APIErrorException {
        ProductCategory categorie = categorieRepository.findByUuid(uuid);
        if (categorie == null)  {
            throw new APIErrorException(ErrorCode.A008);
        }
        else{
            categorie.setDelete(true);
            categorieRepository.save(categorie);
        }
        
    }

    @Override
    public Map<String, Object> getAllCategories(int offset, int pageSize, String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(offset, pageSize,Sort.by(orders));
        Page<ProductCategory> categoriesPage = categorieRepository.findAll(pageable);

            List<CategorieResponceDto> categories = categoriesPage.getContent().stream()
                    .map(category -> mapClassWithResponseDto.convertToDto(category, CategorieResponceDto.class))
                    .collect(Collectors.toList());
    
            Map<String, Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("currentPage", categoriesPage.getNumber());
            response.put("totalItems", categoriesPage.getTotalElements());
            response.put("totalPages", categoriesPage.getTotalPages());
    
            return response;
       
    }
    

}
