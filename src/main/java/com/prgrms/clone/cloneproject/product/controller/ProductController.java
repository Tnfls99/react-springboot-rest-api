package com.prgrms.clone.cloneproject.product.controller;

import com.prgrms.clone.cloneproject.product.domain.Product;
import com.prgrms.clone.cloneproject.product.domain.ProductDTO;
import com.prgrms.clone.cloneproject.product.service.ProductProvider;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.prgrms.clone.cloneproject.product.domain.util.Category.getAllCategories;
import static com.prgrms.clone.cloneproject.product.domain.util.Color.getAllColors;

@Controller
@RequestMapping("/shop")
public class ProductController {

    private final ProductProvider productProvider;

    public ProductController(ProductProvider productProvider) {
        this.productProvider = productProvider;
    }


    @GetMapping("/products")
    public String getAllProduct(@RequestParam @Nullable String category, Model model){
        List<Product> productList = productProvider.getAll(category);
        model.addAttribute("products", productList);
        return "product/list";
    }

    @GetMapping("/products/{productId}")
    public String getOneProduct(@PathVariable Integer productId, Model model){
        Product product = productProvider.getOne(productId);

        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model){
        model.addAttribute("categories", getAllCategories());
        model.addAttribute("colors", getAllColors());
        model.addAttribute("productDTO", new ProductDTO());
        return "product/addForm";
    }

    @PostMapping("/products/add")
    public String saveProduct(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes, Model model){
        Product newProduct = productProvider.save(productDTO);
        redirectAttributes.addAttribute("productId", newProduct.getId());

        return "redirect:shop/products/{productId}";
    }
}
