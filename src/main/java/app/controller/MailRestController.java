package app.controller;

import app.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class MailRestController {

    private MailService mailService;

    @Autowired
    public MailRestController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendMail/{mail}")
    public ResponseEntity<String> sendEmailToUser(@PathVariable String mail, @RequestBody String msg) {
        return ResponseEntity.ok(mailService.sendEmail(mail, msg));
    }

    @PostMapping("/greetingMail")
    public ResponseEntity<String> sendGreetingEmail(@RequestBody String mail) {
        return ResponseEntity.ok(mailService.sendGreetingEmail(mail));
    }

    @PostMapping("/deletionMail")
    public ResponseEntity<String> sendEmailOnDeletion(@RequestBody String mail) {
        return ResponseEntity.ok(mailService.sendEmailOnDeletion(mail));
    }

    @PostMapping("/updatingMail")
    public ResponseEntity<String> sendEmailOnUpdating(@RequestBody String mail) {
        return ResponseEntity.ok(mailService.sendEmailOnUpdating(mail));
    }
}
