package com.prgrms.clone.cloneproject.product.controller;

import com.prgrms.clone.cloneproject.product.domain.Product;
import com.prgrms.clone.cloneproject.product.domain.ProductDTO;
import com.prgrms.clone.cloneproject.product.service.ProductProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.prgrms.clone.cloneproject.product.domain.util.Category.getAllCategories;
import static com.prgrms.clone.cloneproject.product.domain.util.Color.getAllColors;

@Controller
@RequestMapping("/shop/products/new")
public class ProductAddController {
    private final ProductProvider productProvider;

    public ProductAddController(ProductProvider productProvider) {
        this.productProvider = productProvider;
    }

    @GetMapping
    public String addProduct(Model model){
        model.addAttribute("categories", getAllCategories());
        model.addAttribute("colors", getAllColors());
        model.addAttribute("productDTO", new ProductDTO());
        return "product/addForm";
    }

    @PostMapping
    public String saveProduct(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes, Model model){
        Product newProduct = productProvider.save(productDTO);
        redirectAttributes.addAttribute("productId", newProduct.getId());

        return "redirect:shop/products/{productId}";
    }
}
