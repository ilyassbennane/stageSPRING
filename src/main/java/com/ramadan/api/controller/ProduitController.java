package com.ramadan.api.controller;

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

import com.ramadan.api.dto.produit.model.ProduitRequestDto;
import com.ramadan.api.dto.produit.model.ProduitResponseDto;
import com.ramadan.api.dto.produit.model.ProduitUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.produit.IProduitService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "404", description = "Produits not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
})
@RestController
@RequestMapping("${URL-BASE}/product")
@Tag(name = "Produits", description = "Operations pertaining to produits")
public class ProduitController {
    @Autowired
    IProduitService produitService ;

/**
 * Retrieves all products based on the provided pagination and sorting parameters.
 *
 * @param page the page number for pagination (default is 0 if not provided)
 * @param size the number of items per page for pagination (default is 10 if not provided)
 * @param sort an array of strings representing the sorting order (default is "uuid,desc" if not provided)
 * @return a ResponseEntity containing a map of product details and pagination information
 */
    @ApiOperation(value = "Get all products", notes = "Get all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all products"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,desc") String[] sort) {
        try {
            Map<String, Object> response = produitService.findAll(page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
/**
 * Get all products belonging to a specific category.
 *
 * @param uuid the UUID of the category
 * @param page the page number for pagination (default is 0 if not provided)
 * @param size the number of items per page for pagination (default is 10 if not provided)
 * @param sort an array of strings representing the sorting order (default is "uuid,desc" if not provided)
 * @return a ResponseEntity containing a map of product details and pagination information
 */
    @ApiOperation(value = "Get all products by category", notes = "Get all products belonging to a specific category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all products by category"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/products-category-list/{uuid}")
    public ResponseEntity<Map<String, Object>> getAllProductsByCategory(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,desc") String[] sort) {
        try {
            Map<String, Object> response = produitService.findAllByCategorie(uuid, page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
/**
 * Get a produit by UUID.
 *
 * @param uuid the UUID of the produit
 * @return a ResponseEntity containing the details of the produit
 * @throws APIErrorException if an error occurs while retrieving the produit
 */
    @ApiOperation(value = "Get a produit by UUID", notes = "Get produit details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved produit", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponseDto.class))
            }),

    })
    @GetMapping("/{uuid}")
    public ResponseEntity<ProduitResponseDto> getProduitByUuid(@PathVariable String uuid)
            throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(produitService.findByUuid(uuid));
    }
/**
 * Create a new produit with provided details.
 *
 * @param produitRequestDTO the details of the produit to be created
 * @return a ResponseEntity containing the details of the created produit
 * @throws APIErrorException if an error occurs while creating the produit
 */
    @ApiOperation(value = "Create a new produit", notes = "Create a new produit with provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            })
    })
    @PostMapping
    public ResponseEntity<ProduitResponseDto> createProduit(@Valid @RequestBody ProduitRequestDto produitRequestDTO) throws APIErrorException {
        // Placeholder
        return new ResponseEntity<>(produitService.saveEntity(produitRequestDTO), HttpStatus.CREATED);
    }

    /**
 * Update an existing produit with the provided UUID.
 *
 * @param uuid the UUID of the produit to be updated
 * @param produitUpdateDTO the details of the produit to be updated
 * @return a ResponseEntity containing the details of the updated produit
 */
@ApiOperation(value = "Update an existing produit", notes = "Update an existing produit with provided UUID.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produit updated successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProduitResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
})
@PutMapping
public ResponseEntity<ProduitResponseDto> updateProduit(@Valid @RequestBody ProduitUpdateDto produitUpdateDTO) {
    // Placeholder
    return ResponseEntity.ok(produitService.updateEntity(produitUpdateDTO.getUuid(), produitUpdateDTO));
}
/**
 * Delete a produit by UUID.
 *
 * @param uuid the UUID of the produit to be deleted
 * @return a ResponseEntity with no content if the produit is deleted successfully
 * @throws APIErrorException if an error occurs while deleting the produit
 */
    @ApiOperation(value = "Delete a produit by UUID", notes = "Delete a produit by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produit deleted successfully"),})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteProduit(@PathVariable String uuid) throws APIErrorException {
        produitService.deleteProduit(uuid);
        return ResponseEntity.noContent().build();
    }

}
