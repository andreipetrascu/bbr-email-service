package bbr.bbremailservice.controller;

import bbr.bbremailservice.model.EmailDTO;
import bbr.bbremailservice.service.EmailService;
import bbr.bbremailservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/email-service")
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping(path = "/welcome")
    public String welcome() {
        return "Congrats! you are on the right way.";
    }

    @GetMapping(path = "/hello")
    public Response hello() {
        return new Response(1, "hello from email-controller", null);
    }

    @PostMapping(path = "/send-email")
    public HttpEntity sendEmail(@RequestBody EmailDTO emailDTO) {
        return ok(emailService.sendEmail(emailDTO));
    }

}
