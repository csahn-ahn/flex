package me.univ.flex.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.error.ErrorService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionController implements ErrorController {

	private final ErrorService errorService;
	private final String ERROR_404_PAGE_PATH = "error/404";
	private final String ERROR_500_PAGE_PATH = "error/500";
	private final String ERROR_ETC_PAGE_PATH = "error/error";

	@ExceptionHandler({Exception.class})
	@RequestMapping(name = "에러", value = "/error")
	public String handlerError(HttpServletRequest request, Model model, Exception e) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		int statusCode = status != null ? Integer.valueOf(status.toString()) : 0;
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		String referer = request.getHeader("Referer");
		String contentType = request.getContentType();
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));

		errorService.save(
			statusCode,
			false,
			httpStatus.getReasonPhrase(),
			referer,
			contentType,
			null,
			null,
			null,
			//trace.toString()
			e.getMessage()
		);

		if (status != null) {
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return ERROR_404_PAGE_PATH;
			}

			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return ERROR_500_PAGE_PATH;
			}
		}

		e.printStackTrace();

		return ERROR_ETC_PAGE_PATH;
	}
}
