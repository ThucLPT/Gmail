package pkg;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GmailController {
	@Autowired
	private GmailService gmailService;

	@GetMapping("")
	public String index() {
		return "index";
	}

	@PostMapping("send")
	@ResponseBody
	public String send(HttpServletRequest request, @RequestParam("attach") MultipartFile[] files) {
		gmailService.sendTextMailWithAttachments(request, files);
		return "Sent";
	}
}
