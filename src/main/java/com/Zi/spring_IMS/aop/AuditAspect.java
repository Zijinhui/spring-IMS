package com.Zi.spring_IMS.aop;

import com.Zi.spring_IMS.model.dto.ProductDTO;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private final ProductRepository productRepository;

    @Autowired
    public AuditAspect(ProductRepository productRepository) { this.productRepository = productRepository; }

    // track changes when updates or deletions of products, and save audit logs
    @After("execution(* com.Zi.spring_IMS.service.ProductService.updateProductById(..))")
    public void logUpdateProduct(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Integer productId = (Integer)args[0];
        ProductDTO productDTO = (ProductDTO)args[1];
        // save audit details
        System.out.println("Product updated. ID: " + productId + ", New Data: " + productDTO);
    }

    @Around("execution(* com.Zi.spring_IMS.service.ProductService.deleteProductById(..))")
    public void logDeleteProduct(ProceedingJoinPoint joinPoint) throws Throwable{
        Object[] args = joinPoint.getArgs();
        Integer productId = (Integer)args[0];
        Product existingProduct = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found! Delete failed!!!"));

        Object result = joinPoint.proceed();

        //save audit details
        System.out.println("Result: " + result);
        System.out.println("Deleted Product Id: " + productId + ", Deleted Data: " + existingProduct);
    }

    // track changes when execute inbound/outbound transactions

}
