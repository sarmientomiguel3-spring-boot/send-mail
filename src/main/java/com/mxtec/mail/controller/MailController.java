package com.mxtec.mail.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mxtec.mail.dto.EmailDTO;
import com.mxtec.mail.dto.EmailFileDTO;
import com.mxtec.mail.service.IEmailService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v1")
public class MailController {

  @Autowired
  private IEmailService emailService;

  @PostMapping("/sendMessage")
  public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO) {
    System.out.println("Mensaje REcibido" + emailDTO);
    emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

    Map<String, String> response = new HashMap<>();
    response.put("estado", "enviado");

    return (ResponseEntity<?>) ResponseEntity.ok(response);
  }

  @PostMapping("/sendMessageFile")
  public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO) {
    try {
      String fileName = emailFileDTO.getFile().getName();
      Path path = Paths.get("src/main/resources/files/" + fileName);
      Files.createDirectories(path.getParent());
      Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

      File file = path.toFile();

      emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);

      Map<String, String> response = new HashMap<>();
      response.put("estado", "enviado");
      response.put("archivo", "fileName");

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el Email con el archivo" + e.getMessage());
    }

  }
}
