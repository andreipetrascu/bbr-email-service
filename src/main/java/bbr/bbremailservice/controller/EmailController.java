package bbr.bbremailservice.controller;

import bbr.bbremailservice.model.EmailDTO;
import bbr.bbremailservice.service.EmailService;
import bbr.bbremailservice.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

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
        logger.info("[EmailController]: /send-email");
        return ok(emailService.sendEmail(emailDTO));
    }

    @PostMapping(path = "/send-security-code")
    public HttpEntity sendSecurityCode(@RequestBody EmailDTO emailDTO) {
        logger.info("[EmailController]: /send-security-code");
        return ok(emailService.sendSecurityCode(emailDTO));
    }

    @PostMapping(path = "/verify-security-code")
    public HttpEntity verifySecurityCode(@RequestBody EmailDTO emailDTO) {
        logger.info("[EmailController]: /verify-security-code");
        return ok(emailService.verifySecurityCode(emailDTO));
    }

}
