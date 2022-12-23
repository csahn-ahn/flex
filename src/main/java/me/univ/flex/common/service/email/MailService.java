package me.univ.flex.common.service.email;

import com.andwise.tm6.api.jars.automail.AutomailAPI;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.service.email.thunder.ThunderMailAPI;
import me.univ.flex.common.service.email.thunder.ThunderMailRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

	private final FlexProperties flexProperties;
	private final TemplateEngine htmlTemplateEngine;
	private final ThunderMailAPI thunderMailAPI;

	@Async
	public void send(String recipient,
		EmailTemplateEnum emailTemplateEnum,
		Map<String, Object> variables,
		String receiverName) {

		Context ctx = new Context(Locale.KOREA);
		ctx.setVariables(variables);
		String htmlTemplate = htmlTemplateEngine.process(emailTemplateEnum.getTemplateHtml(), ctx);
		htmlTemplate = htmlTemplate.replaceAll("(\\[LINE_BREAK\\])", "<br/>");

		AutomailAPI automailAPI = new AutomailAPI();
		//썬더메일UI URL
		automailAPI.setApiURL(flexProperties.getEmailProps().getApiUrl());
		automailAPI.setAutomailIDEncrypt(flexProperties.getEmailProps().getApiKey());
		//받는 사람 이메일
		automailAPI.setReceiverName(receiverName);
		automailAPI.setReceiverEmail(recipient);
		//메일 제목
		automailAPI.setMailTitle(emailTemplateEnum.getSubject());
		//메일 내용
		automailAPI.setMailContent(htmlTemplate);
		//보내는 사람 이름
		automailAPI.setSenderName(flexProperties.getEmailProps().getUserSenderName());
		//보내는 사람 이메일
		automailAPI.setSenderEmail(flexProperties.getEmailProps().getUserSenderEmail());
		//반송 이메일
		automailAPI.setReturnMail(flexProperties.getEmailProps().getUserSenderEmail());
		//automailAPI.setReturnMail("louis@univ.me");

		automailAPI.sendEmail();

		// System.out.println("auto code = " + automailAPI.getCode());
		// System.out.println("auto msg = " + automailAPI.getMsg());
	}


	@Async
	public void sendNoThunderMailAPI(String recipient,
		EmailTemplateEnum emailTemplateEnum,
		Map<String, Object> variables) {

		Context ctx = new Context(Locale.KOREA);
		ctx.setVariables(variables);
		String htmlTemplate = htmlTemplateEngine.process(emailTemplateEnum.getTemplateHtml(), ctx);

		thunderMailAPI.sendEmail(
			ThunderMailRequest.builder()
				.automailIDEncrypt(flexProperties.getEmailProps().getApiKey())
				.mailTitle(emailTemplateEnum.getSubject())
				.mailContent(htmlTemplate)
				.senderName(flexProperties.getEmailProps().getUserSenderName())
				.senderEmail(flexProperties.getEmailProps().getUserSenderEmail())
				.receiverEmail(recipient)
				.returnEmail(flexProperties.getEmailProps().getUserSenderEmail())
				.build()
		);
	}
}
