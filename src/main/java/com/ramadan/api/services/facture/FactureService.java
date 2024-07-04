package com.ramadan.api.services.facture;
import java.util.HashMap;
import java.util.Map;
import com.ramadan.api.entity.product.Product;
import com.ramadan.api.repository.product.ProduitRepository;

import net.sf.jasperreports.engine.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class FactureService {
    @Autowired
    ProduitRepository produitRepository;
    public byte[] generateInvoicePdf(String uuid) {
        try {
            Product product = produitRepository.findByUuid(uuid);
            // Load and compile the JasperReport template from resources
            File file = ResourceUtils.getFile("classpath:Invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            
            // Prepare data source for the invoice
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("product", product); // Pass the product as a parameter

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Export to PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            
            return outputStream.toByteArray();     
           } 
           catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}


