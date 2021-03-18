package app.controller;

import app.service.MailService_gRPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class MailRestController {

    private MailService_gRPC mailServiceGRPC;

    @Autowired
    public MailRestController(MailService_gRPC mailServiceGRPC) {
        this.mailServiceGRPC = mailServiceGRPC;
    }

    @PostMapping("/sendMail/{mail}")
    public ResponseEntity<String> sendEmailToUser(@PathVariable String mail, @RequestBody String msg) {
        return ResponseEntity.ok(mailServiceGRPC.sendEmail(mail, msg));
    }
}
