package com.ramadan.api.controller;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ramadan.api.dto.categorie.model.CategorieRequestDto;
import com.ramadan.api.dto.categorie.model.CategorieResponceDto;
import com.ramadan.api.dto.categorie.model.CategorieUpdateDto;
import com.ramadan.api.dto.categorie.model.CategoryChildDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.categorie.ICategorieService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController


@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved categories", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieResponceDto.class))
        }),

        @ApiResponse(responseCode = "404", description = "Categories not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
})

@RequestMapping("${URL-BASE}/category")
@Tag(name = "Categories", description = "Operations pertaining to categories")
public class CategoryController {
    @Autowired
    ICategorieService categorieService ;

/**
 * Retrieves a list of all users with pagination and sorting options.
 *
 * @param page The page number for pagination (default: 0).
 * @param size The number of items per page for pagination (default: 10).
 * @param sort An array of sorting parameters in the format "field,direction" (default: "uuid,desc").
 * @return A {@link ResponseEntity} containing a map of user information and pagination details if successful.
 * @throws APIErrorException if an error occurs while retrieving the user information.
 */
    @ApiOperation(value = "Get a category by UUID", notes = "Get category details by providing its UUID.")
    @GetMapping("/{uuid}")
    public ResponseEntity<CategorieResponceDto> getCategoryByUuid(@PathVariable String uuid)
            throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(categorieService.findByUuid(uuid));
    }

    /**
 * Get a category by UUID.
 * 
 * @param uuid The UUID of the category to retrieve.
 * @return ResponseEntity containing the category details.
 * @throws APIErrorException if an error occurs during the operation.
 */
    @ApiOperation(value = "Create a new category", notes = "Create a new category with provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieResponceDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            })
    })
    @PostMapping
    public ResponseEntity<CategorieResponceDto> createCategory(@Valid @RequestBody CategorieRequestDto categorieRequest) throws APIErrorException {
        // Placeholder
        return new ResponseEntity<>(categorieService.saveEntity(categorieRequest), HttpStatus.CREATED);
    }

    /**
 * Create a new child category with the provided details.
 * 
 * @param requestDto The request body containing the details of the child category to be created.
 * @return ResponseEntity containing the details of the created child category.
 */
    @ApiOperation(value = "Create a new child category", notes = "Create a new child  category with provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieResponceDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            })
    })
    @PostMapping("/child")
    public ResponseEntity<CategorieResponceDto> createCategoryChild(@RequestBody @Valid CategoryChildDto requestDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.saveEntityChild(requestDto));
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
/**
 * Update an existing category with the provided UUID.
 * 
 * @param uuid The UUID of the category to be updated.
 * @param categorieUpdate The request body containing the updated details of the category.
 * @return ResponseEntity containing the updated category details if successful.
 * @throws APIErrorException if an error occurs during the operation.
 */
    @ApiOperation(value = "Update an existing category", notes = "Update an existing category with provided UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CategorieResponceDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            }),

    })
    @PutMapping
    public ResponseEntity<CategorieResponceDto> updateCategory( @Valid @RequestBody CategorieUpdateDto categorieUpdate) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(categorieService.updateEntity(categorieUpdate.getUuid(),categorieUpdate));
    }
    /**
 * Get all categories by company.
 * 
 * Retrieves a list of all categories of a company with pagination and sorting options.
 * 
 * @param uuid The UUID of the company.
 * @param page The page number for pagination (default: 0).
 * @param size The number of items per page for pagination (default: 10).
 * @param sort An array of sorting parameters in the format "field,direction" (default: "uuid,desc").
 * @return A {@link ResponseEntity} containing a map of category information and pagination details if successful.
 *         If an error occurs, it returns a {@link ResponseEntity} with an error message and status code.
 */
    @ApiOperation(value = "Get all categories by company", notes = "Get a list of all categories of a company.")
    @GetMapping("/categories-company-list/{uuid}")
    public ResponseEntity<Map<String, Object>> getCategoriesByCompanyWithPagination(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,desc") String[] sort) {
        try {
            Map<String, Object> response = categorieService.findAllByCompany(uuid, page, size,sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    /**
 * Get all categories of a parent category with pagination and sorting options.
 * 
 * Retrieves a list of all categories of a parent category with pagination and sorting options.
 * 
 * @param uuid The UUID of the parent category.
 * @param page The page number for pagination (default: 0).
 * @param size The number of items per page for pagination (default: 10).
 * @param sort An array of sorting parameters in the format "field,direction" (default: "uuid,desc").
 * @return A {@link ResponseEntity} containing a map of category information and pagination details if successful.
 *         If an error occurs, it returns a {@link ResponseEntity} with an error message and status code.
 */

   @ApiOperation(value = "Get all categories", notes = "Get a list of all categories of a parent category.")
   @GetMapping("/categories-parent-list/{uuid}")
   public ResponseEntity<Map<String, Object>> getAllCategoriesByParent(
           @PathVariable String uuid,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size,
           @RequestParam(defaultValue = "uuid,desc") String[] sort
   ) {
       try {
           Map<String, Object> response = categorieService.findAllByCategorieParente(uuid, page, size,sort);
           return ResponseEntity.ok(response);
       } catch (APIErrorException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
   }
   /**
 * Delete a category by UUID.
 * 
 * This method is used to delete a category by providing its UUID.
 * 
 * @param uuid The UUID of the category to be deleted.
 * @return ResponseEntity with no content if the category is deleted successfully.
 * @throws APIErrorException if an error occurs during the deletion operation.
 */
    @ApiOperation(value = "Delete a category by UUID", notes = "Delete a category by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String uuid) throws APIErrorException {
        categorieService.deleteCategorie(uuid);
        return ResponseEntity.noContent().build();
    }

/**
 * Get all categories.
 * 
 * Retrieves a list of all categories with pagination and sorting options.
 * 
 * @param page The page number for pagination (default: 0).
 * @param size The number of items per page for pagination (default: 10).
 * @param sort An array of sorting parameters in the format "field,direction" (default: "uuid,desc").
 * @return A {@link ResponseEntity} containing a map of category information and pagination details if successful.
 *         If an error occurs, it returns a {@link ResponseEntity} with an error message and status code.
 */
@ApiOperation(value = "Get all categories", notes = "Get a list of all categories.")
@GetMapping("/all")
public ResponseEntity<Map<String, Object>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "uuid,desc") String[] sort) {
    try {
        Map<String, Object> response = categorieService.getAllCategories(page, size,sort);
        return ResponseEntity.ok(response);
    } catch (APIErrorException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", e.getMessage()));
    }
}
}

