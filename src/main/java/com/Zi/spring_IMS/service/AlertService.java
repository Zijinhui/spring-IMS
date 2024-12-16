package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    private final EmailService emailService;

    @Autowired
    public AlertService(EmailService emailService) { this.emailService = emailService; }

    @Async
    public void checkAndSendReorderAlert(Product product) {
        System.out.println("Product Quantity: " + product.getQuantity());
        System.out.println("Reorder Level: " + product.getReorder_level());
        if (product.getQuantity() <= product.getReorder_level()) {
            String subject = "Reorder Alert for Product: " + product.getName();
            String message = String.format(
                    "Product: %s" +
                            "\nCurrent Quantity: %d" +
                            "\nReorder Level: %d" +
                            "\n Reorder Quantity(Recommend): %d" +
                            "\n\nPlease reorder this product ASAP!",
                    product.getName(), product.getQuantity(), product.getReorder_level(), product.getReorder_quantity()
            );

            emailService.sendReorderAlert(subject, message);
        }
    }
}
