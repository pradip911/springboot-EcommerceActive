package com.ecom.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

//@RequestMapping("/testapp")
//@Controller
@RestController
@CrossOrigin
public class PDFCreator {

    @Autowired
    SpringTemplateEngine templateEngine;

    //@RequestMapping("/getdetails")
    @PreAuthorize("hasRole('User')")
	@GetMapping({"/downloadPdf/{orderId}/{orderFullName}/{orderStatus}"})
    public @ResponseBody void savePDF(@PathVariable(
			"orderId") Integer orderId,@PathVariable(
					"orderFullName") String orderFullName,@PathVariable(
									"orderStatus") String orderStatus) throws IOException, DocumentException {
        Context context = new Context();

        context.setVariable("orderid",orderId);
        context.setVariable("orderFullName",orderFullName);
        context.setVariable("orderStatus",orderStatus);


        String htmlContentToRender = templateEngine.process("pdf-template", context);
        String xHtml = xhtmlConvert(htmlContentToRender);

        ITextRenderer renderer = new ITextRenderer();

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources","templates")
                .toUri()
                .toURL()
                .toString();
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        //OutputStream outputStream = new FileOutputStream("src//test.pdf");
        OutputStream outputStream = new FileOutputStream(System.getProperty("user.home")+ "//Downloads//" +orderId+".pdf");
        renderer.createPDF(outputStream);
        outputStream.close();

        

    }

    private String xhtmlConvert(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");
    }
}
