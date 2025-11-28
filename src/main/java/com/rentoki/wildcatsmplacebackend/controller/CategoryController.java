package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.Category;
import com.rentoki.wildcatsmplacebackend.model.CategoryRequest;
import com.rentoki.wildcatsmplacebackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "4. Categories", description = "Category management APIs")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Get all categories",
            description = "Retrieves a complete list of all categories in the system, including main categories and their subcategories."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all categories",
                    content = @Content(schema = @Schema(implementation = Category.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Get main categories",
            description = "Retrieves all top-level categories that do not have a parent category."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved main categories",
                    content = @Content(schema = @Schema(implementation = Category.class))
            )
    })
    @GetMapping("/main")
    public ResponseEntity<List<Category>> getMainCategories() {
        List<Category> categories = categoryService.getMainCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Get subcategories",
            description = "Retrieves all subcategories that belong to a specific parent category."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved subcategories",
                    content = @Content(schema = @Schema(implementation = Category.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Category not found\"}")
                    )
            )
    })
    @GetMapping("/{id}/subcategories")
    public ResponseEntity<List<Category>> getSubcategories(
            @Parameter(description = "ID of the parent category", required = true)
            @PathVariable Integer id
    ) {
        List<Category> subcategories = categoryService.getSubcategories(id);
        return ResponseEntity.ok(subcategories);
    }

    @Operation(
            summary = "Get active categories",
            description = "Retrieves all categories where isActive is set to true."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved active categories",
                    content = @Content(schema = @Schema(implementation = Category.class))
            )
    })
    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories() {
        List<Category> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Retrieves detailed information about a specific category using its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the category",
                    content = @Content(schema = @Schema(implementation = Category.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Category not found\"}")
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @Parameter(description = "ID of the category to retrieve", required = true)
            @PathVariable Integer id
    ) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category or subcategory. To create a main category, omit parentCategory. " +
                    "To create a subcategory, include parentCategory with a valid categoryId. " +
                    "Example for main category: {\"categoryName\": \"Electronics\", \"description\": \"Electronic items\", \"isActive\": true}. " +
                    "Example for subcategory: {\"categoryName\": \"Laptops\", \"description\": \"Laptop computers\", \"isActive\": true, \"parentCategory\": {\"categoryId\": 1}}"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category successfully created",
                    content = @Content(schema = @Schema(implementation = Category.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Category not found\"}")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @Parameter(description = "Category object to be created", required = true)
            @RequestBody CategoryRequest categoryRequest
    ) {
        Category createdCategory = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Operation(
            summary = "Update a category",
            description = "Updates an existing category with new information. All provided fields will be updated."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category successfully updated",
                    content = @Content(schema = @Schema(implementation = Category.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Category not found\"}")
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @Parameter(description = "ID of the category to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Updated category information", required = true)
            @RequestBody CategoryRequest categoryRequest
    ) {
        Category updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(
            summary = "Delete a category",
            description = "Permanently deletes a category from the system. Use with caution as this action cannot be undone."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Category not found\"}")
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Integer id
    ) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Toggle category status",
            description = "Toggles the isActive status of a category between active and inactive. This is useful for temporarily hiding categories without deleting them."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category status successfully toggled",
                    content = @Content(schema = @Schema(implementation = Category.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Category not found\"}")
                    )
            )
    })
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Category> toggleCategoryStatus(
            @Parameter(description = "ID of the category to toggle", required = true)
            @PathVariable Integer id
    ) {
        Category category = categoryService.toggleCategoryStatus(id);
        return ResponseEntity.ok(category);
    }
}